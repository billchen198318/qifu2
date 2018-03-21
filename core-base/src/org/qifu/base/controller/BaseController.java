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
package org.qifu.base.controller;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.qifu.base.Constants;
import org.qifu.base.exception.BaseSysException;
import org.qifu.base.model.CheckControllerFieldHandler;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryControllerJsonResultObj;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.base.model.YesNo;
import org.qifu.util.MenuSupportUtils;
import org.qifu.util.SimpleUtils;
import org.qifu.util.UploadSupportUtils;
import org.springframework.web.servlet.ModelAndView;

import ognl.Ognl;
import ognl.OgnlException;

public abstract class BaseController {
	protected static final String PAGE_SYS_LOGIN = "system/login";
	protected static final String PAGE_SYS_SEARCH_NO_DATA = "system/searchNoData";
	protected static final String PAGE_SYS_LOGIN_AGAIN = "system/login_again";
	protected static final String PAGE_SYS_NO_AUTH = "system/auth1";
	protected static final String PAGE_SYS_ERROR = "system/error";
	protected static final String PAGE_SYS_WARNING = "system/warning";
	
	protected static final String REDIRECT_INDEX = "index.do";
	
	protected static final String YES = YesNo.YES;
	protected static final String NO = YesNo.NO;
	protected static final String EXCEPTION = "E";
	
	public String getPageRedirect(String url) {
		return "redirect:/" + url;
	}
	
	public ModelAndView getDefaultModelAndView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorContact", this.getErrorContact());
		mv.addObject("verMsg", this.getVerMsg());
		mv.addObject("jsVerBuild", this.getJsVerBuild());
		mv.addObject("loginCaptchaCodeEnable", this.getLoginCaptchaCodeEnable());
		mv.addObject("googleMapEnable", this.getGoogleMapEnable());
		mv.addObject("googleMapUrl", this.getGoogleMapUrl());
		mv.addObject("googleMapKey", this.getGoogleMapKey());
		mv.addObject("googleMapDefaultLat", this.getGoogleMapDefaultLat());
		mv.addObject("googleMapDefaultLng", this.getGoogleMapDefaultLng());
		mv.addObject("googleMapLanguage", this.getGoogleMapLanguage());
		mv.addObject("googleMapClientLocationEnable", this.getGoogleMapClientLocationEnable());
		mv.addObject("twitterEnable", this.getTwitterEnable());
		mv.addObject("isSuperRole", this.isSuperRole());
		mv.addObject("jqXhrType", this.getJqXhrType());
		mv.addObject("jqXhrTimeout", this.getJqXhrTimeout());
		mv.addObject("jqXhrCache", this.getJqXhrCache());
		mv.addObject("jqXhrAsync", this.getJqXhrAsync());
		mv.addObject("maxUploadSize", this.getMaxUploadSize());
		mv.addObject("maxUploadSizeMb", this.getMaxUploadSizeMb());
		return mv;
	}
	
	public ModelAndView getDefaultModelAndView(String progId) {
		ModelAndView mv = this.getDefaultModelAndView();
		if (StringUtils.isBlank(progId)) {
			return mv;
		}
		mv.addObject("programId", progId);
		mv.addObject("programName", MenuSupportUtils.getProgramName(progId));
		return mv;
	}	
	
	public String getErrorContact() {
		return String.valueOf( Constants.getSettingsMap().get("basePage.errorContact") );
	}
	
	public String getVerMsg() {
		return String.valueOf( Constants.getSettingsMap().get("basePage.verMsg") );
	}
	
	public String getJsVerBuild() {
		return String.valueOf( Constants.getSettingsMap().get("basePage.jsVerBuild") );
	}
	
	public String getLoginCaptchaCodeEnable() {
		return Constants.getLoginCaptchaCodeEnable();
	}
	
	public String getGoogleMapEnable() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.enable") );
	}	
	
	public String getGoogleMapUrl() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.url") );
	}
	
	public String getGoogleMapKey() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.key") );
	}	
	
	public String getGoogleMapDefaultLat() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.defaultLat") );
	}	
	
	public String getGoogleMapDefaultLng() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.defaultLng") );
	}		
	
	public String getGoogleMapLanguage() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.language") );
	}	
	
	public String getGoogleMapClientLocationEnable() {
		return String.valueOf( Constants.getSettingsMap().get("googleMap.clientLocationEnable") );
	}
	
	public String getTwitterEnable() {
		return String.valueOf( Constants.getSettingsMap().get("twitter.enable") );
	}
	
	public String getJqXhrType() {
		return String.valueOf( Constants.getSettingsMap().get("basePage.jqXhrType") );
	}
	
	public String getJqXhrTimeout() {
		return String.valueOf( Constants.getSettingsMap().get("basePage.jqXhrTimeout") );
	}
	
	public boolean getJqXhrCache() {
		return (YesNo.YES.equals( Constants.getSettingsMap().get("basePage.jqXhrCache") ) ? true : false);
	}
	
	public boolean getJqXhrAsync() {
		return (YesNo.YES.equals( Constants.getSettingsMap().get("basePage.jqXhrAsync") ) ? true : false);
	}
	
	public String getMaxUploadSize() {
		return String.valueOf( UploadSupportUtils.UPLOAD_MAX_SIZE );
	}
	
	public String getMaxUploadSizeMb() {
		return String.valueOf( UploadSupportUtils.UPLOAD_MAX_SIZE / 1048576 );
	}	
	
	public boolean isSuperRole() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(Constants.SUPER_ROLE_ADMIN) || subject.hasRole(Constants.SUPER_ROLE_ALL)) {
			return true;
		}
		return false;
	}
	
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	public String getAccountId() {		
		Subject subject = SecurityUtils.getSubject();		
		return this.defaultString((String)subject.getPrincipal());		
	}	
	
	public String generateOid() {
		return SimpleUtils.getUUIDStr();
	}	
	
	public String getBasePath(HttpServletRequest request) {
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	}
	
	protected String defaultString(String str) {
		return StringUtils.defaultString(str);
	}
	
	protected void setPageMessage(HttpServletRequest request, String pageMessage) {
		if (null!=pageMessage && pageMessage.length()>=500) {
			pageMessage=pageMessage.substring(0, 500);
		}
		request.setAttribute(Constants.PAGE_MESSAGE, pageMessage);
	}	
	
	protected void setPageErrorContact(HttpServletRequest request) {
		request.setAttribute("errorContact", this.getErrorContact());
	}
	
	protected void setPageMessage(ModelAndView mv, String pageMessage) {
		if (null!=pageMessage && pageMessage.length()>=500) {
			pageMessage=pageMessage.substring(0, 500);
		}
		mv.addObject(Constants.PAGE_MESSAGE, pageMessage);
	}	
	
	protected void setPageErrorContact(ModelAndView mv) {
		mv.addObject("errorContact", this.getErrorContact());
	}	
	
	protected String getNowDate() {
		return SimpleUtils.getStrYMD("/");
	}
	
	protected String getNowDate2() {
		return SimpleUtils.getStrYMD("-");
	}		
	
	protected <T> DefaultControllerJsonResultObj<T> getDefaultJsonResult(String progId) {
		DefaultControllerJsonResultObj<T> result = DefaultControllerJsonResultObj.build();
		this.setResultDefaultValue(result, progId);
		return result;
	}
	
	protected <T> QueryControllerJsonResultObj<T> getQueryJsonResult(String progId) {
		QueryControllerJsonResultObj<T> result = QueryControllerJsonResultObj.build();
		this.setResultDefaultValue(result, progId);
		return result;
	}	
	
	private void setResultDefaultValue(DefaultControllerJsonResultObj<?> result, String progId) {
		if (!StringUtils.isBlank(this.getAccountId())) {
			result.setLogin( YES );
			Subject subject = this.getSubject();
			if (subject.hasRole(Constants.SUPER_ROLE_ALL) || subject.hasRole(Constants.SUPER_ROLE_ADMIN)) {
				result.setIsAuthorize( YES );
			}
			if (subject.isPermitted(progId)) {
				result.setIsAuthorize( YES );
			}
			if (!YES.equals(result.getIsAuthorize())) {
				result.setMessage( "no authorize!" );
			}
		} else {
			result.setMessage( "Please login!" );
		}		
	}
	
	protected boolean isAuthorizeAndLoginFromControllerJsonResult(DefaultControllerJsonResultObj<?> result) {
		if (YES.equals(result.getIsAuthorize()) && YES.equals(result.getLogin())) {
			return true;
		}
		return false;
	}
	
	protected void exceptionResult(DefaultControllerJsonResultObj<?> result, Exception e) {
		e.printStackTrace();
		result.setMessage( e.getMessage().toString() );
		result.setSuccess( EXCEPTION );
	}
	
	protected String getExceptionMessage(Exception e) {
		String str = "";
		if (e != null && e.getMessage() != null) {
			str = e.getMessage();
		}
		if (e != null && e.getMessage() == null) {
			str = e.toString();
		}
		if (e == null) {
			str = "null undefined";
		}
		return str;
	}
	
	protected String getAuthorityExceptionPage(BaseSysException e, HttpServletRequest request) {
		this.setPageMessage(request, this.getExceptionMessage(e));
		return PAGE_SYS_NO_AUTH;
	}
	
	protected String getAuthorityExceptionPage(BaseSysException e, ModelAndView mv) {
		this.setPageMessage(mv, this.getExceptionMessage(e));
		return PAGE_SYS_NO_AUTH;
	}	
	
	protected String getServiceOrControllerExceptionPage(BaseSysException e, HttpServletRequest request) {
		this.setPageMessage(request, this.getExceptionMessage(e));
		return PAGE_SYS_SEARCH_NO_DATA;
	}
		
	protected String getServiceOrControllerExceptionPage(BaseSysException e, ModelAndView mv) {
		this.setPageMessage(mv, this.getExceptionMessage(e));
		return PAGE_SYS_SEARCH_NO_DATA;
	}	
	
	protected String getExceptionPage(Exception e, HttpServletRequest request) {
		e.printStackTrace();
		this.setPageMessage(request, this.getExceptionMessage(e));
		return PAGE_SYS_ERROR;
	}
	
	protected String getExceptionPage(Exception e, ModelAndView mv) {
		e.printStackTrace();
		this.setPageMessage(mv, this.getExceptionMessage(e));
		return PAGE_SYS_ERROR;
	}
	
	protected void fillObjectFromRequest(HttpServletRequest request, Object root) {
		Enumeration<String> pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String key = pNames.nextElement();
			Object value = request.getParameter(key);
			try {
				Ognl.setValue(key, root, value);
			} catch (OgnlException e) {
				//e.printStackTrace();
			}
		}
	}
	
	protected PageOf getPageOf(HttpServletRequest request) {
		PageOf pageOf = new PageOf();
		fillObjectFromRequest(request, pageOf);
		return pageOf;
	}
	
	protected SearchValue getSearchValue(HttpServletRequest request) {
		SearchValue searchValue = new SearchValue();
		fillObjectFromRequest(request, searchValue);
		return searchValue;
	}	
	
	protected <T> void setQueryGridJsonResult(QueryControllerJsonResultObj<T> jsonResult, QueryResult<T> queryResult, PageOf pageOf) {
		if (queryResult.getValue() != null) {
			jsonResult.setValue( queryResult.getValue() );
			jsonResult.setPageOfCountSize( queryResult.getRowCount() );
			jsonResult.setPageOfSelect( NumberUtils.toInt(pageOf.getSelect(), 1) );
			jsonResult.setPageOfShowRow( NumberUtils.toInt(pageOf.getShowRow(), PageOf.Rows[0]) );
			jsonResult.setPageOfSize( NumberUtils.toInt(pageOf.getSize(), 1) );
			jsonResult.setSuccess(YesNo.YES);
		} else {
			jsonResult.setMessage( queryResult.getSystemMessage().getValue() );
		}		
	}
	
	protected <T> CheckControllerFieldHandler<T> getCheckControllerFieldHandler(DefaultControllerJsonResultObj<T> result) {
		return CheckControllerFieldHandler.build(result);
	}
	
	protected boolean noSelect(String selectValue) {
		if (StringUtils.isBlank(selectValue) || Constants.HTML_SELECT_NO_SELECT_ID.equals(selectValue)) {
			return true;
		}
		return false;
	}
	
	protected Map<String, String> getPleaseSelectMap(boolean pleaseSelect) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (pleaseSelect) {
			dataMap.put(Constants.HTML_SELECT_NO_SELECT_ID, Constants.HTML_SELECT_NO_SELECT_NAME);
		}
		return dataMap;
	}
	
	protected List<String> transformAppendKeyStringToList(String appendOid) {
		List<String> list = new LinkedList<String>();
		if (StringUtils.isBlank(appendOid)) {
			return list;
		}
		String tmp[] = appendOid.split(Constants.ID_DELIMITER);
		for (int i=0; tmp != null && i < tmp.length; i++) {
			if (StringUtils.isBlank(tmp[i])) {
				continue;
			}
			if (list.contains(tmp[i])) {
				continue;
			}
			list.add(tmp[i]);
		}
		return list;
	}
	
}
