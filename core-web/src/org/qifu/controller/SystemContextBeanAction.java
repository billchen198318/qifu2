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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.qifu.model.CtxBeanTypes;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysCtxBean;
import org.qifu.service.ISysCtxBeanService;
import org.qifu.service.ISysService;
import org.qifu.service.logic.ISystemContextBeanLogicService;
import org.qifu.vo.SysCtxBeanVO;
import org.qifu.vo.SysVO;
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
public class SystemContextBeanAction extends BaseController {
	
	private ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISystemContextBeanLogicService systemContextBeanLogicService;

	public ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> getSysCtxBeanService() {
		return sysCtxBeanService;
	}

	@Autowired
	@Resource(name="core.service.SysCtxBeanService")
	@Required	
	public void setSysCtxBeanService(ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService) {
		this.sysCtxBeanService = sysCtxBeanService;
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

	public ISystemContextBeanLogicService getSystemContextBeanLogicService() {
		return systemContextBeanLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemContextBeanLogicService")
	@Required
	public void setSystemContextBeanLogicService(ISystemContextBeanLogicService systemContextBeanLogicService) {
		this.systemContextBeanLogicService = systemContextBeanLogicService;
	}
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		mv.addObject("sysMap", this.sysService.findSysMap(this.getBasePath(request), true));
		if ("createPage".equals(type) || "editPage".equals(type)) {
			mv.addObject("ctxBeanTypesMap", CtxBeanTypes.getTypes(true));
		}
	}
	
	private void fetchData(SysCtxBeanVO sysCtxBean, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysCtxBeanVO> result = this.sysCtxBeanService.findObjectByOid(sysCtxBean);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysCtxBean = result.getValue();
		mv.addObject("sysCtxBean", sysCtxBean);
		
		TbSys sys = new TbSys();
		sys.setSysId(sysCtxBean.getSystem());
		DefaultResult<TbSys> sResult = this.sysService.findEntityByUK(sys);
		if ( sResult.getValue() == null ) {
			throw new ControllerException( sResult.getSystemMessage().getValue() );
		}
		sys = sResult.getValue();
		mv.addObject("systemOid", sys.getOid());
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006Q")
	@RequestMapping(value = "/core.sysCtxbeanManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0006Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-ctxbean/sys-ctxbean-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006Q")
	@RequestMapping(value = "/core.sysCtxbeanQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysCtxBeanVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysCtxBeanVO> > result = this.getQueryJsonResult("CORE_PROG001D0006Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysCtxBeanVO> > queryResult = this.sysCtxBeanService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006A")
	@RequestMapping(value = "/core.sysCtxbeanCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0006A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-ctxbean/sys-ctxbean-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006E")
	@RequestMapping(value = "/core.sysCtxbeanEdit.do")
	public ModelAndView editPage(HttpServletRequest request, SysCtxBeanVO sysCtxbean) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0006E");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysCtxbean, mv);
			viewName = "sys-ctxbean/sys-ctxbean-edit";
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
	
	private void checkFields(DefaultControllerJsonResultObj<SysCtxBeanVO> result, SysCtxBeanVO sysCtxbean, String systemOid) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("systemOid", ( this.noSelect(systemOid) ), "Please select system!")
		.testField("className", sysCtxbean, "@org.apache.commons.lang3.StringUtils@isBlank(className)", "Class name is blank!")
		.testField("className", sysCtxbean, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( className.replaceAll(\"[.]\", \"\") )", "Class name not accept!")
		.testField("type", ( !CtxBeanTypes.isType(sysCtxbean.getType()) ), "Please select type!")
		.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<SysCtxBeanVO> result, SysCtxBeanVO sysCtxbean, String systemOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysCtxbean, systemOid);
		DefaultResult<SysCtxBeanVO> cResult = this.systemContextBeanLogicService.create(sysCtxbean, systemOid);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );
	}
	
	private void update(DefaultControllerJsonResultObj<SysCtxBeanVO> result, SysCtxBeanVO sysCtxbean, String systemOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysCtxbean, systemOid);
		DefaultResult<SysCtxBeanVO> uResult = this.systemContextBeanLogicService.update(sysCtxbean, systemOid);
		if ( uResult.getValue() != null ) {
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );
	}	
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysCtxBeanVO sysCtxbean) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemContextBeanLogicService.delete(sysCtxbean);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );		
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006A")
	@RequestMapping(value = "/core.sysCtxbeanSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysCtxBeanVO> doSave(SysCtxBeanVO sysCtxbean, @RequestParam("systemOid") String systemOid) {
		DefaultControllerJsonResultObj<SysCtxBeanVO> result = this.getDefaultJsonResult("CORE_PROG001D0006A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysCtxbean, systemOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006E")
	@RequestMapping(value = "/core.sysCtxbeanUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysCtxBeanVO> doUpdate(SysCtxBeanVO sysCtxbean, @RequestParam("systemOid") String systemOid) {
		DefaultControllerJsonResultObj<SysCtxBeanVO> result = this.getDefaultJsonResult("CORE_PROG001D0006E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sysCtxbean, systemOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0006D")
	@RequestMapping(value = "/core.sysCtxbeanDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysCtxBeanVO sysCtxbean) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG001D0006D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysCtxbean);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
