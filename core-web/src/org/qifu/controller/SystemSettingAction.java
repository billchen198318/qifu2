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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.util.SystemSettingConfigureUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class SystemSettingAction extends BaseController {
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		request.setAttribute("mailFrom", SystemSettingConfigureUtils.getMailDefaultFromValue().trim());
		request.setAttribute("mailEnable", SystemSettingConfigureUtils.getMailEnableValue().trim());
		request.setAttribute("leftMenu", SystemSettingConfigureUtils.getLeftAccordionContainerEnableValue().trim());
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0007Q")
	@RequestMapping(value = "/core.sysSettingManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0007Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-setting/sys-setting-management";
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
	
	private void update(DefaultControllerJsonResultObj<Boolean> result, HttpServletRequest request) throws AuthorityException, ControllerException, ServiceException, Exception {
		String mailFrom = request.getParameter("mailFrom");
		String mailEnable = request.getParameter("mailEnable");
		String leftMenu = request.getParameter("leftMenu");
		if (StringUtils.isBlank(mailFrom)) {
			mailFrom = SystemSettingConfigureUtils.getMailDefaultFromValue().trim();
		}
		SystemSettingConfigureUtils.updateMailDefaultFromValue(mailFrom);
		SystemSettingConfigureUtils.updateMailEnableValue( (YES.equals(mailEnable) ? YES : NO) );
		SystemSettingConfigureUtils.updateLeftAccordionContainerEnableValue( (YES.equals(leftMenu) ? YES : NO) );
		result.setSuccess( YES );
		result.setValue( true );
		result.setMessage( SysMessageUtil.get(SysMsgConstants.UPDATE_SUCCESS) );				
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0007E")
	@RequestMapping(value = "/core.sysSettingUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doUpdate(HttpServletRequest request) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG001D0007E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, request);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
