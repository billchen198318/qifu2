/* 
 * Copyright 2012-2017 qifu of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package org.qifu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryControllerJsonResultObj;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.model.WSConfig;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysWsConfig;
import org.qifu.service.ISysService;
import org.qifu.service.ISysWsConfigService;
import org.qifu.service.logic.ISystemWebServiceConfigLogicService;
import org.qifu.sys.CxfServerBean;
import org.qifu.vo.SysVO;
import org.qifu.vo.SysWsConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class SystemWebServiceAction extends BaseController {
	
	private ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISystemWebServiceConfigLogicService systemWebServiceConfigLogicService;
	
	public ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> getSysWsConfigService() {
		return sysWsConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysWsConfigService")
	@Required	
	public void setSysWsConfigService(ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService) {
		this.sysWsConfigService = sysWsConfigService;
	}
	
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}	
	
	public ISystemWebServiceConfigLogicService getSystemWebServiceConfigLogicService() {
		return systemWebServiceConfigLogicService;
	}
	
	@Autowired
	@Resource(name="core.service.logic.SystemWebServiceConfigLogicService")
	@Required
	public void setSystemWebServiceConfigLogicService(ISystemWebServiceConfigLogicService systemWebServiceConfigLogicService) {
		this.systemWebServiceConfigLogicService = systemWebServiceConfigLogicService;
	}
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		mv.addObject("sysMap", this.sysService.findSysMap(this.getBasePath(request), true));
		mv.addObject("typeMap", WSConfig.getTypes(true));
	}
	
	private void fetchData(SysWsConfigVO sysWsConfig, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysWsConfigVO> result = this.sysWsConfigService.findObjectByOid(sysWsConfig);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysWsConfig = result.getValue();
		mv.addObject("sysWsConfig", sysWsConfig);
		
		TbSys sys = new TbSys();
		sys.setSysId(sysWsConfig.getSystem());
		DefaultResult<TbSys> sResult = this.sysService.findEntityByUK(sys);
		if ( sResult.getValue() == null ) {
			throw new ControllerException( sResult.getSystemMessage().getValue() );
		}
		sys = sResult.getValue();
		mv.addObject("systemOid", sys.getOid());		
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001Q")
	@RequestMapping(value = "/core.sysWebServiceManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0001Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-webserv/sys-webserv-management";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001Q")
	@RequestMapping(value = "/core.sysWebServiceQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysWsConfigVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysWsConfigVO> > result = this.getQueryJsonResult("CORE_PROG003D0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysWsConfigVO> > queryResult = this.sysWsConfigService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001A")
	@RequestMapping(value = "/core.sysWebServiceCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0001A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-webserv/sys-webserv-create";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001E")
	@RequestMapping(value = "/core.sysWebServiceEdit.do")
	public ModelAndView editPage(HttpServletRequest request, SysWsConfigVO sysWsConfig) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0001E");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysWsConfig, mv);
			viewName = "sys-webserv/sys-webserv-edit";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;
	}	
	
	private void checkFields(DefaultControllerJsonResultObj<SysWsConfigVO> result, SysWsConfigVO sysWsConfig, String systemOid) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("systemOid", ( this.noSelect(systemOid) ), "Please select system!")
		.testField("wsId", sysWsConfig, "@org.apache.commons.lang3.StringUtils@isBlank(wsId)", "Id is blank!")
		.testField("wsId", sysWsConfig, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( wsId.replaceAll(\"-\", \"\").replaceAll(\"_\", \"\") )", "Id only normal character!")
		.testField("beanId", sysWsConfig, "@org.apache.commons.lang3.StringUtils@isBlank(beanId)", "Bean id is blank!")
		.testField("beanId", sysWsConfig, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( beanId.replaceAll(\"[.]\", \"\") )", "Bean id not accept!")
		.testField("type", ( !WSConfig.isType(sysWsConfig.getType()) ), "Please select type!")
		.testField("publishAddress", ( WSConfig.TYPE_SOAP.equals(sysWsConfig.getType()) && StringUtils.isBlank(sysWsConfig.getPublishAddress()) ), "Address is required SOAP type!")
		.throwMessage();
	}	
	
	private void save(DefaultControllerJsonResultObj<SysWsConfigVO> result, SysWsConfigVO sysWsConfig, String systemOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysWsConfig, systemOid);
		DefaultResult<SysWsConfigVO> cResult = this.systemWebServiceConfigLogicService.create(sysWsConfig, systemOid);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );
	}
	
	private void update(DefaultControllerJsonResultObj<SysWsConfigVO> result, SysWsConfigVO sysWsConfig, String systemOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysWsConfig, systemOid);
		DefaultResult<SysWsConfigVO> uResult = this.systemWebServiceConfigLogicService.update(sysWsConfig, systemOid);
		if ( uResult.getValue() != null ) {
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysWsConfigVO sysWsConfig) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemWebServiceConfigLogicService.delete(sysWsConfig);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );		
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001A")
	@RequestMapping(value = "/core.sysWebServiceSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysWsConfigVO> doSave(SysWsConfigVO sysWsConfig, @RequestParam("systemOid") String systemOid) {
		DefaultControllerJsonResultObj<SysWsConfigVO> result = this.getDefaultJsonResult("CORE_PROG003D0001A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysWsConfig, systemOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001E")
	@RequestMapping(value = "/core.sysWebServiceUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysWsConfigVO> doUpdate(SysWsConfigVO sysWsConfig, @RequestParam("systemOid") String systemOid) {
		DefaultControllerJsonResultObj<SysWsConfigVO> result = this.getDefaultJsonResult("CORE_PROG003D0001E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sysWsConfig, systemOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001D")
	@RequestMapping(value = "/core.sysWebServiceDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysWsConfigVO sysWsConfig) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0001D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysWsConfig);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0001D")
	@RequestMapping(value = "/core.sysWebServiceStopOrReloadJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<String> doStopOrReload(HttpServletRequest request, @RequestParam("type") String type) {
		DefaultControllerJsonResultObj<String> result = this.getDefaultJsonResult("CORE_PROG003D0001D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			Map<String, String> resultMap = CxfServerBean.shutdownOrReloadCallAllSystem(request, type);
			result.setMessage( resultMap.get("message") );
			if (YES.equals(resultMap.get("success"))) {
				result.setSuccess( YES );
			}
			result.setValue(type);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
