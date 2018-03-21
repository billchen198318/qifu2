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
package org.qifu.base.sys;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.qifu.base.Constants;
import org.qifu.base.model.AccountObj;

public class UserAccountHttpSessionSupport {
	protected static Logger logger=Logger.getLogger(UserAccountHttpSessionSupport.class);
	
	public static void create(HttpServletRequest request, AccountObj account, String language) {
		request.getSession().setAttribute(Constants.SESS_ACCOUNT, account);
		request.getSession().setAttribute(Constants.SESS_LANG, language);
	}
	
	public static void createSysCurrentId(HttpServletRequest request, String sysCurrentId) {
		request.getSession().setAttribute(Constants.SESS_SYSCURRENT_ID, sysCurrentId);
	}
	
	public static AccountObj get(HttpServletRequest request) {
		return (AccountObj)request.getSession().getAttribute(Constants.SESS_ACCOUNT);
	}
	
	public static void remove(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute(Constants.SESS_ACCOUNT);
			request.getSession().removeAttribute(Constants.SESS_LANG);
			request.getSession().removeAttribute(Constants.SESS_SYSCURRENT_ID);
		} catch (Exception e) {
			logger.warn( e.getMessage().toString() );
		}
	}
	
	public static String getLang(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(Constants.SESS_LANG);
	}
	
	public static String getSysCurrentId(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(Constants.SESS_SYSCURRENT_ID);
	}
	
}
