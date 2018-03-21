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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.qifu.base.Constants;
import org.qifu.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginAction extends BaseController {
	
	private void fillErrorMessage(HttpServletRequest request, HttpServletResponse response) {
		Object errObj = request.getAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if ("org.qifu.sys.InvalidAccountException".equals(errObj)) {
			request.setAttribute(Constants.PAGE_MESSAGE, "Invalid account.");
		}		
		if ("org.qifu.sys.IncorrectCaptchaException".equals(errObj)) {
			request.setAttribute(Constants.PAGE_MESSAGE, "Captcha code incorrect.");
		}		
		if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(errObj)
				|| "org.apache.shiro.authc.UnknownAccountException".equals(errObj)
				|| "org.apache.shiro.authc.AuthenticationException".equals(errObj)) {
			this.setPageMessage(request, "Login fail.");
		}		
	}
	
	@RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST} )
	public String login(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("loginCaptchaCodeEnable", this.getLoginCaptchaCodeEnable());
		try {
			if (SecurityUtils.getSubject().isAuthenticated()) {
				return this.getPageRedirect( REDIRECT_INDEX );
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.fillErrorMessage(request, response);
		}
		return PAGE_SYS_LOGIN;
	}
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET )
	public String logout(HttpServletRequest request, HttpSession session) {
		request.setAttribute("loginCaptchaCodeEnable", this.getLoginCaptchaCodeEnable());
		SecurityUtils.getSubject().logout();
		return PAGE_SYS_LOGIN;
	}
	
}
