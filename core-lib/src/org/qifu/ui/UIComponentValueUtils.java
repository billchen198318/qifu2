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
package org.qifu.ui;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import ognl.Ognl;
import ognl.OgnlException;

public class UIComponentValueUtils {
	
	public static boolean foundIfResult(PageContext pageContext) {
		if ( pageContext.getAttribute( UIComponent.IfResultVariableName) != null ) {
			return true;
		}
		return false;
	}
	
	public static void removeIfResult(PageContext pageContext) {
		pageContext.removeAttribute(UIComponent.IfResultVariableName);
	}
	
	public static void putIfResult(PageContext pageContext, boolean result) {
		pageContext.setAttribute(UIComponent.IfResultVariableName, result);
	}
	
	public static boolean getIfResult(PageContext pageContext) {
		if ( pageContext.getAttribute( UIComponent.IfResultVariableName) != null ) {
			return (Boolean) pageContext.getAttribute( UIComponent.IfResultVariableName );
		}
		return false;
	}
	
	public static Object getObjectFromPageContextOrRequest(PageContext pageContext, String paramName) {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		/**
		 * 優先順序: pageContext.getAttribute > request.getParameter > request.getAttribute
		 */
		if ( pageContext.getAttribute(paramName) != null ) {
			return pageContext.getAttribute(paramName);
		}
		return ( request.getParameter(paramName) != null ? request.getParameter(paramName) : request.getAttribute(paramName) );
	}
	
	public static Object getObjectFromSession(PageContext pageContext, String paramName) {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		return request.getSession().getAttribute(paramName);
	}
	
	public static Object getOgnlProcessObjectFromPageContextOrRequest(PageContext pageContext, String expression) {
		Map<String, Object> ognlRoot = new HashMap<String, Object>();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Enumeration<String> pcNames = pageContext.getAttributeNamesInScope( PageContext.PAGE_SCOPE );
		Enumeration<String> pNames = request.getParameterNames();
		Enumeration<String> aNames = request.getAttributeNames();
		/**
		 * ognlRoot 放入變數, 優先順序: pageContext.getAttribute > request.getParameter > request.getAttribute
		 */
		while (pcNames.hasMoreElements()) {
			String key = pcNames.nextElement();
			ognlRoot.put(key, pageContext.getAttribute(key));
		}
		while (pNames.hasMoreElements()) {
			String key = pNames.nextElement();
			if (ognlRoot.get(key) == null) {
				ognlRoot.put(key, request.getParameter(key));
			}
		}
		while (aNames.hasMoreElements()) {
			String key = aNames.nextElement();
			if (ognlRoot.get(key) == null) {
				ognlRoot.put(key, request.getAttribute(key));
			}
		}
		if (ognlRoot.size() == 0) {
			ognlRoot = null;
			return null;
		}
		Object val = null;
		try {
			val = Ognl.getValue(expression, ognlRoot);
		} catch (OgnlException e) {
			//e.printStackTrace();
		}
		ognlRoot.clear();
		ognlRoot = null;
		return val;
	}
	
	public static Object getOgnlProcessObjectFromHttpSession(PageContext pageContext, String expression) {
		Map<String, Object> ognlRoot = new HashMap<String, Object>();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Enumeration<String> aNames = request.getSession().getAttributeNames();
		while (aNames.hasMoreElements()) {
			String key = aNames.nextElement();
			ognlRoot.put(key, request.getAttribute(key));
		}
		if (ognlRoot.size() == 0) {
			ognlRoot = null;
			return null;
		}
		Object val = null;
		try {
			val = Ognl.getValue(expression, ognlRoot);
		} catch (OgnlException e) {
			//e.printStackTrace();
		}
		ognlRoot.clear();
		ognlRoot = null;		
		return val;		
	}
	
	public static void setValue(PageContext pageContext, Map<String, Object> paramMap, String paramMapKey, String value, boolean escapeHtml, boolean ecmaScript, String scope) {
		if (!StringUtils.isBlank(value)) {
			if ( UIComponent.SCOPE_SESSION.equals(scope) ) {
				Object valObj = null;
				if ( (valObj = getObjectFromSession(pageContext, value)) != null ) {
					putValue(paramMap, paramMapKey, valObj, escapeHtml, ecmaScript);
					return;
				}
			} else {
				Object valObj = null;
				if ( (valObj = getObjectFromPageContextOrRequest(pageContext, value)) != null ) {
					putValue(paramMap, paramMapKey, valObj, escapeHtml, ecmaScript);
					return;
				}
			}
		}		
		/**
		 * (暫時不需要)不要處理 "@" 與 "new" 的 Ognl expression 如: @java.lang.Runtime@getRuntime().exec("exec /usr/local/bin/firefox"), new java.lang...
		 * 處理 如: policy.no , policy.amount
		 */
		//if (!StringUtils.isBlank(value) && value.indexOf("@") == -1 && value.indexOf("new ") == -1 && value.indexOf(".") >= 1) { // 暫時不需要
		if (!StringUtils.isBlank(value)) { // TextBox, TextArea, Select, Out tag 會用到
			Object val = null;
			if ( UIComponent.SCOPE_SESSION.equals(scope) ) {
				val = getOgnlProcessObjectFromHttpSession(pageContext, value);
			} else {
				val = getOgnlProcessObjectFromPageContextOrRequest(pageContext, value);
			}
			if (null != val) {
				putValue(paramMap, paramMapKey, val, escapeHtml, ecmaScript);
				return;
			}
		}
		if (paramMap.get(paramMapKey) == null) {
			paramMap.put(paramMapKey, StringUtils.defaultString(value));
		}
	}
	
	private static void putValue(Map<String, Object> params, String paramMapKey, Object val, boolean escapeHtml, boolean ecmaScript) {
		if (val == null) {
			return;
		}
		if (val instanceof java.lang.String) {
			params.put(paramMapKey, String.valueOf(val) );
			if (ecmaScript) {
				params.put(paramMapKey, StringEscapeUtils.escapeEcmaScript( (String)val ) );
			}
			if (escapeHtml) {
				params.put(paramMapKey, StringEscapeUtils.escapeHtml4( (String)val ) );
			}
			if (ecmaScript && escapeHtml) {
				params.put(paramMapKey, StringEscapeUtils.escapeHtml4( StringEscapeUtils.escapeEcmaScript( (String)val ) ) );
			}
			return;
		}		
		if (val instanceof java.lang.Integer) {
			params.put(paramMapKey, String.valueOf( (Integer)val ) );
			return;			
		}
		if (val instanceof java.lang.Long) {
			params.put(paramMapKey, String.valueOf( (Long)val ) );
			return;						
		}
		if (val instanceof java.math.BigDecimal) {
			params.put(paramMapKey, ((java.math.BigDecimal)val).toString() );
			return;					
		}
		if (val instanceof java.math.BigInteger) {
			params.put(paramMapKey, ((java.math.BigInteger)val).toString() );
			return;							
		}
		if (val instanceof java.lang.Float) {
			params.put(paramMapKey, String.valueOf( (Float)val ) );
			return;						
		}
		if (val instanceof java.lang.Double) {
			params.put(paramMapKey, String.valueOf( (Double)val ) );
			return;						
		}	
		params.put(paramMapKey, String.valueOf(val));
	}	
	
}
