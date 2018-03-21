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
package org.qifu.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.qifu.base.model.YesNo;
import org.qifu.ui.impl.TextArea;

public class TextAreaTag implements Tag {
	private PageContext pageContext=null;
	private Tag parent=null;
	
	private String id = "";
	private String name = "";
	private String value = "";
	private String readonly = YesNo.NO;
	private String label = "";
	private String cssClass = "form-control mb-2 mr-sm-2 mb-sm-0";
	private String requiredFlag = YesNo.NO;
	private String rows = "3";
	private String placeholder = "";
	private String escapeHtml = YesNo.YES;
	private String escapeJavaScript = YesNo.NO;	
	
	private TextArea handler() {
		TextArea textArea = new TextArea();
		textArea.setPageContext(this.pageContext);
		textArea.setId(this.id);
		textArea.setName(this.name);
		textArea.setValue(this.value);
		textArea.setReadonly(this.readonly);
		textArea.setLabel(this.label);
		textArea.setCssClass(this.cssClass);
		textArea.setRequiredFlag(this.requiredFlag);
		textArea.setRows(this.rows);
		textArea.setPlaceholder(this.placeholder);
		textArea.setEscapeHtml(this.escapeHtml);
		textArea.setEscapeJavaScript(this.escapeJavaScript);
		return textArea;
	}	
	
	@Override
	public int doEndTag() throws JspException {
		TextArea textArea = this.handler();
		try {
			this.pageContext.getOut().write( textArea.getHtml() );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		textArea = null;
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		return 0;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getRequiredFlag() {
		return requiredFlag;
	}

	public void setRequiredFlag(String requiredFlag) {
		this.requiredFlag = requiredFlag;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getEscapeHtml() {
		return escapeHtml;
	}

	public void setEscapeHtml(String escapeHtml) {
		this.escapeHtml = escapeHtml;
	}

	public String getEscapeJavaScript() {
		return escapeJavaScript;
	}

	public void setEscapeJavaScript(String escapeJavaScript) {
		this.escapeJavaScript = escapeJavaScript;
	}
	
}
