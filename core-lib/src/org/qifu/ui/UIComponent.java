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

import javax.servlet.jsp.PageContext;

import org.qifu.util.SimpleUtils;

public interface UIComponent {
	public static final String IS_SCRIPT = "script";
	public static final String IS_HTML = "html";		
	public static final String SCOPE_PAGE = "page";
	public static final String SCOPE_SESSION = "session";
	public static final String IfResultVariableName = "_qifu_UIComponent_IfResult_" + SimpleUtils.createRandomString(10);
	public static final long TIMEOUT = 300 * 1000; // 300 sec
	public void setId(String id);
	public String getId();	
	public void setName(String name);
	public String getName();	
	public String getScript() throws Exception;
	public String getHtml() throws Exception;
	public void setPageContext(PageContext pageContext);
}
