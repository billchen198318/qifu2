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
import org.qifu.po.TbSys;
import org.qifu.po.TbSysIcon;
import org.qifu.service.ISysIconService;
import org.qifu.service.ISysService;
import org.qifu.service.logic.IApplicationSystemLogicService;
import org.qifu.util.IconUtils;
import org.qifu.vo.SysIconVO;
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
public class SystemSiteAction extends BaseController {
	
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	private IApplicationSystemLogicService applicationSystemLogicService;
	
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}

	public ISysIconService<SysIconVO, TbSysIcon, String> getSysIconService() {
		return sysIconService;
	}

	@Autowired
	@Resource(name="core.service.SysIconService")
	@Required	
	public void setSysIconService(ISysIconService<SysIconVO, TbSysIcon, String> sysIconService) {
		this.sysIconService = sysIconService;
	}

	public IApplicationSystemLogicService getApplicationSystemLogicService() {
		return applicationSystemLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.ApplicationSystemLogicService")
	@Required	
	public void setApplicationSystemLogicService(IApplicationSystemLogicService applicationSystemLogicService) {
		this.applicationSystemLogicService = applicationSystemLogicService;
	}

	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001Q")
	@RequestMapping(value = "/core.sysSiteManagement.do")
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0001Q");
		try {
			// do some...
			viewName = "sys-site/sys-site-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001Q")
	@RequestMapping(value = "/core.sysSiteQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj<List<SysVO>> queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj<List<SysVO>> result = this.getQueryJsonResult("CORE_PROG001D0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult<List<SysVO>> queryResult = this.sysService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001A")
	@RequestMapping(value = "/core.sysSiteCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0001A");
		try {
			Map<String, String> iconDataMap = IconUtils.getIconsSelectData();
			mv.addObject("iconDataMap", iconDataMap);
			String firstIconKey = "";
			for (Map.Entry<String, String> entry : iconDataMap.entrySet()) {
				if ("".equals(firstIconKey)) {
					firstIconKey = entry.getKey();
				}
			}
			mv.addObject("firstIconKey", firstIconKey);
			viewName = "sys-site/sys-site-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001E")
	@RequestMapping(value = "/core.sysSiteEdit.do")	
	public ModelAndView editPage(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0001E");
		try {
			SysVO sys = new SysVO();
			sys.setOid(oid);
			DefaultResult<SysVO> sysResult = this.sysService.findObjectByOid(sys);
			if ( sysResult.getValue() == null ) {
				throw new ControllerException( sysResult.getSystemMessage().getValue() );
			}
			sys = sysResult.getValue();
			Map<String, String> iconDataMap = IconUtils.getIconsSelectData();
			TbSysIcon sysIcon = new TbSysIcon();
			sysIcon.setIconId(sys.getIcon());
			DefaultResult<TbSysIcon> iconResult = this.sysIconService.findEntityByUK(sysIcon);
			if (iconResult.getValue() == null) {
				throw new ControllerException( iconResult.getSystemMessage().getValue() );
			}
			sysIcon = iconResult.getValue();
			mv.addObject("firstIconKey", sysIcon.getOid());
			mv.addObject("iconDataMap", iconDataMap);
			mv.addObject("sys", sys);
			viewName = "sys-site/sys-site-edit";
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
	
	private void checkFields(DefaultControllerJsonResultObj<SysVO> result, SysVO sys) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("systemId", sys, "@org.apache.commons.lang3.StringUtils@isBlank(sysId)", "Id is blank!")
		.testField("systemId", sys, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09(sysId)", "Id only normal character!")
		.testField("systemId", ( this.noSelect(sys.getSysId()) ), "Please change Id value!") // Id 不能用  "all" 這個下拉值
		.testField("systemName", sys, "@org.apache.commons.lang3.StringUtils@isBlank(name)", "Name is blank!")
		.testField("systemHost", sys, "@org.apache.commons.lang3.StringUtils@isBlank(host)", "Host is blank!")
		.testField("systemContextPath", sys, "@org.apache.commons.lang3.StringUtils@isBlank(contextPath)", "Context path is blank!")
		.throwMessage();		
	}
	
	private void save(DefaultControllerJsonResultObj<SysVO> result, SysVO sys) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sys);
		DefaultResult<SysVO> sysResult = this.applicationSystemLogicService.create(sys, sys.getIcon());
		if ( sysResult.getValue() != null ) {
			result.setValue( sysResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( sysResult.getSystemMessage().getValue() );		
	}
	
	private void update(DefaultControllerJsonResultObj<SysVO> result, SysVO sys) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sys);
		DefaultResult<SysVO> sysResult = this.applicationSystemLogicService.update(sys, sys.getIcon());
		if ( sysResult.getValue() != null ) {
			result.setValue( sysResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( sysResult.getSystemMessage().getValue() );
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysVO sys) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> sysResult = this.applicationSystemLogicService.delete(sys);
		if (sysResult.getValue() != null) {
			result.setSuccess( YES );
		}
		result.setMessage( sysResult.getSystemMessage().getValue() );		
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001A")
	@RequestMapping(value = "/core.sysSiteSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysVO> doSave(SysVO sys) {
		DefaultControllerJsonResultObj<SysVO> result = this.getDefaultJsonResult("CORE_PROG001D0001A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sys);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001E")
	@RequestMapping(value = "/core.sysSiteUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysVO> doUpdate(SysVO sys) {
		DefaultControllerJsonResultObj<SysVO> result = this.getDefaultJsonResult("CORE_PROG001D0001E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sys);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0001D")
	@RequestMapping(value = "/core.sysSiteDeleteJson.do", produces = "application/json")			
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysVO sys) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG001D0001D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sys);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
}
