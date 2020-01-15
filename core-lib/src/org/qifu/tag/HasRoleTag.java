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
package org.qifu.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.qifu.ui.impl.HasRole;

public class HasRoleTag implements Tag {
	private PageContext pageContext=null;
	private Tag parent=null;
	private String roleIds = "";
	
	private HasRole handler() {
		HasRole hasRoleTest = new HasRole();
		hasRoleTest.setPageContext(this.pageContext);
		hasRoleTest.setRoleIds( this.roleIds );
		return hasRoleTest;
	}	

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		HasRole hasRole = this.handler();
		if (hasRole.getTestResult()) {
			hasRole = null;
			return EVAL_BODY_INCLUDE;
		}	
		hasRole = null;		
		return SKIP_BODY;		
	}

	@Override
	public Tag getParent() {
		return this.parent;
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag parent) {
		this.parent = parent;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

}
