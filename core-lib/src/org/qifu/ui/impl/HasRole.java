/* 
 * Copyright 2012-2020 qifu of copyright Chen Xin Nien
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
package org.qifu.ui.impl;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.qifu.base.Constants;
import org.qifu.ui.UIComponent;
import org.qifu.ui.UIComponentValueUtils;

public class HasRole implements UIComponent {
	private PageContext pageContext = null;	
	private String roleIds = "";

	@Override
	public void setId(String id) {
		
	}
	
	@Override
	public String getId() {
		return "";
	}
	
	@Override
	public void setName(String name) {
		
	}
	
	@Override
	public String getName() {
		return "";
	}
	
	@Override
	public String getScript() throws Exception {
		return "";
	}

	@Override
	public String getHtml() throws Exception {
		return "";
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	public Boolean getTestResult() {
		String roleIdArr[] = StringUtils.defaultString( this.roleIds ).split(",|" + Constants.ID_DELIMITER);
		boolean flag = false;
		Subject subject = SecurityUtils.getSubject();
		for (int i = 0; roleIdArr != null && i < roleIdArr.length && !flag && subject != null; i++) {
			flag = subject.hasRole( roleIdArr[i].replaceAll(" ", "") );
		}
		UIComponentValueUtils.putHasRoleResult(pageContext, flag);
		return flag;
	}	
	
}
