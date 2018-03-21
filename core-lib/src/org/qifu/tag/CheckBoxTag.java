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
import org.qifu.ui.impl.CheckBox;

public class CheckBoxTag implements Tag {
	private PageContext pageContext=null;
	private Tag parent=null;	
	
	private String id = "";
	private String name = "";
	private String nbspFirst = YesNo.YES;
	private String cssClass = "custom-control-input";
	private String checked = YesNo.NO;
	private String checkedTest = "";
	private String disabled = YesNo.NO;
	private String label = "";
	private String onchange = "";
	private String onclick = "";	

	private CheckBox handler() {
		CheckBox checkBox = new CheckBox();
		checkBox.setPageContext(this.pageContext);
		checkBox.setId(this.id);
		checkBox.setName(this.name);
		checkBox.setNbspFirst(this.nbspFirst);
		checkBox.setCssClass(this.cssClass);
		checkBox.setChecked(this.checked);
		checkBox.setCheckedTest(this.checkedTest);
		checkBox.setDisabled(this.disabled);
		checkBox.setLabel(this.label);
		checkBox.setLabel(this.label);
		checkBox.setOnchange(this.onchange);
		checkBox.setOnclick(this.onclick);
		return checkBox;
	}

	@Override
	public int doEndTag() throws JspException {
		CheckBox checkBox = this.handler();
		try {
			this.pageContext.getOut().write( checkBox.getHtml() );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		checkBox = null;
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

	public String getNbspFirst() {
		return nbspFirst;
	}

	public void setNbspFirst(String nbspFirst) {
		this.nbspFirst = nbspFirst;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getCheckedTest() {
		return checkedTest;
	}

	public void setCheckedTest(String checkedTest) {
		this.checkedTest = checkedTest;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
}
