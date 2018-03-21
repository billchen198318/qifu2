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
package org.qifu.base.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.qifu.base.Constants;
import org.qifu.base.model.AccountObj;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.YesNo;
import org.qifu.base.sys.IUSessLogHelper;
import org.qifu.base.sys.USessLogHelperImpl;
import org.qifu.base.sys.UserAccountHttpSessionSupport;
import org.qifu.base.sys.UserCurrentCookie;
import org.qifu.util.SimpleUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginHandlerInterceptor implements HandlerInterceptor {
	protected static Logger logger = Logger.getLogger(LoginHandlerInterceptor.class);
	private IUSessLogHelper uSessLogHelper;
	
	public LoginHandlerInterceptor() {
		super();
		uSessLogHelper=new USessLogHelperImpl();
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelView) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, String> currentData = UserCurrentCookie.getCurrentData(request);
		AccountObj accountObj = UserAccountHttpSessionSupport.get(request);
		String accountId = null;
		String currentId = null;
		if ( null != accountObj ) {
			accountId = accountObj.getAccount();
		}
		if ( null != currentData ) {
			currentId = currentData.get("currentId");
		}
		if (accountObj!=null && !StringUtils.isBlank(accountObj.getAccount()) ) {
			if ( StringUtils.isBlank(currentId) ) {
				currentId = "NULL";
			}
			String sessSysCurrentId = UserAccountHttpSessionSupport.getSysCurrentId(request);
			if ( !currentId.equals(sessSysCurrentId) ) {
				logger.warn( "currentId: " + currentId + " not equals session variable currentId: " + sessSysCurrentId );
				return this.redirectLogin(request, response, ((HandlerMethod) handler).getMethod(), sessSysCurrentId, accountId);
			}
			if (uSessLogHelper.countByCurrent(accountObj.getAccount(), currentId)<1) {
				return this.redirectLogin(request, response, ((HandlerMethod) handler).getMethod(), sessSysCurrentId, accountId);
			}
			return true;
		} 
		return this.redirectLogin(request, response, ((HandlerMethod) handler).getMethod(), currentId, accountId);
	}
	
	private boolean redirectLogin(HttpServletRequest request, HttpServletResponse response, Method method, String currentId, String accountId) throws Exception {
		if (request.getSession() != null && request.getSession().getAttribute(Constants.SESS_ACCOUNT) != null) {
			UserAccountHttpSessionSupport.remove(request);
			if ( !Constants.getSystem().equals(Constants.getMainSystem()) ) { // for gsbsc-web, qcharts-web
				if ( SecurityUtils.getSubject().isAuthenticated() ) {
					SecurityUtils.getSubject().logout();
				}				
			}			
		}		
		String header = request.getHeader("X-Requested-With");
		String isQifuPageChange = request.getParameter(Constants.QIFU_PAGE_IN_TAB_IFRAME);	
		if ("XMLHttpRequest".equalsIgnoreCase(header) && !YesNo.YES.equals(isQifuPageChange) ) {
			response.getWriter().print(Constants.NO_LOGIN_JSON_DATA);
			response.getWriter().flush();
			response.getWriter().close();
			return false;
		}		
		if ( !Constants.getSystem().equals(Constants.getMainSystem()) ) { // for gsbsc-web, qcharts-web
			if ( !StringUtils.isBlank(accountId) && !StringUtils.isBlank(currentId) && this.uSessLogHelper.countByCurrent(accountId, currentId) > 0 ) { 
				/**
				 * 
				 * 有在 CORE-WEB 登入, 但是"這次的登入" 與 "上一次登入" 不同
				 * 必須讓 shiroFilter 重新刷新過 , 所以樣頁面處理 refreshDojoContentPane
				 * 
				 * 如:
				 * 1. 先用 admin 登入  
				 * 2. 登出 此時 core-web 的 session 失效了, 但 gsbsc-web 還存在
				 * 3. 用 tester 登入 , 此時使用 gsbsc-web 或 qcharts-web 時讓 shiroFilter 重新刷新一次
				 * 
				 */
				Annotation[] actionMethodAnnotations = method.getAnnotations();						
				String progId = this.getProgramId( request, actionMethodAnnotations );				
				if ( !StringUtils.isBlank(progId) ) {
					//request.setAttribute("progId", progId);
					logger.warn("do page call refresTab event = " + progId);					
					//return "refreshDojoContentPane"; // 重新調用 url , 讓 shiroFilter 重導
					response.sendRedirect("./pages/system/refresPage.jsp?progId=" + progId + "&n=" + SimpleUtils.getUUIDStr());
					return false;
				} else {
					String url = SimpleUtils.getHttpRequestUrl( request );
					logger.warn("redirect URL = " + url );			
					response.sendRedirect( url );
					return false;
				}
				
			}
		}		
		if (YesNo.YES.equals(isQifuPageChange)) {						
			response.sendRedirect("./pages/system/login_again.jsp");
			return false;
		}
		response.sendRedirect("logout.do");; // 導向logout , 讓 logout action 執行 SecurityUtils.getSubject().logout()
		return false;
	}	
	
	private String getProgramId(HttpServletRequest request, Annotation[] annotations) {
		String progId = "";
		for (Annotation annotation : annotations) {
			if (annotation instanceof ControllerMethodAuthority) {
				progId = StringUtils.defaultString( ((ControllerMethodAuthority)annotation).programId() );
			}
		}
		if ( StringUtils.isBlank(progId) ) { // 沒有ControllerMethodAuthority , 就找 url 的 prog_id 參數 , 主要是 COMMON FORM 會用到
			progId = StringUtils.defaultString( request.getParameter("progId") );			
		}
		return progId;		
	}	
	
}
