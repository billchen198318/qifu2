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
import org.qifu.ui.impl.Button;

public class ButtonTag implements Tag {
	private PageContext pageContext=null;
	private Tag parent=null;	
	
	private String id = "";
	private String xhrUrl = "";
	private String xhrParameter = "";
	private String formId = "";
	private String onclick = "";
	private String cssClass = "btn btn-primary";
	private String label = "";
	private String errorFunction = "";
	private String loadFunction = "";	
	private String disabled = YesNo.NO;
	private String xhrSendNoPleaseWait = YesNo.NO;
	private String selfPleaseWaitShow = YesNo.NO;
	private String bootboxConfirm = YesNo.NO;
	private String bootboxConfirmTitle = "";	
	
	private Button handler() {
		Button button = new Button();
		button.setPageContext(this.pageContext);
		button.setId(this.id);
		button.setXhrUrl(this.xhrUrl);
		button.setXhrParameter(this.xhrParameter);
		button.setFormId(this.formId);
		button.setOnclick(this.onclick);
		button.setCssClass(this.cssClass);
		button.setLabel(this.label);
		button.setErrorFunction(this.errorFunction);
		button.setLoadFunction(this.loadFunction);
		button.setDisabled(this.disabled);
		button.setXhrSendNoPleaseWait(this.xhrSendNoPleaseWait);
		button.setSelfPleaseWaitShow(this.selfPleaseWaitShow);
		button.setBootboxConfirm(this.bootboxConfirm);
		button.setBootboxConfirmTitle(this.bootboxConfirmTitle);
		return button;
	}

	@Override
	public int doEndTag() throws JspException {
		Button button = this.handler();
		try {
			this.pageContext.getOut().write( button.getHtml() );
			this.pageContext.getOut().write( button.getScript() );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		button = null;
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

	public String getXhrUrl() {
		return xhrUrl;
	}

	public void setXhrUrl(String xhrUrl) {
		this.xhrUrl = xhrUrl;
	}

	public String getXhrParameter() {
		return xhrParameter;
	}

	public void setXhrParameter(String xhrParameter) {
		this.xhrParameter = xhrParameter;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getErrorFunction() {
		return errorFunction;
	}

	public void setErrorFunction(String errorFunction) {
		this.errorFunction = errorFunction;
	}

	public String getLoadFunction() {
		return loadFunction;
	}

	public void setLoadFunction(String loadFunction) {
		this.loadFunction = loadFunction;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getXhrSendNoPleaseWait() {
		return xhrSendNoPleaseWait;
	}

	public void setXhrSendNoPleaseWait(String xhrSendNoPleaseWait) {
		this.xhrSendNoPleaseWait = xhrSendNoPleaseWait;
	}

	public String getSelfPleaseWaitShow() {
		return selfPleaseWaitShow;
	}

	public void setSelfPleaseWaitShow(String selfPleaseWaitShow) {
		this.selfPleaseWaitShow = selfPleaseWaitShow;
	}

	public String getBootboxConfirm() {
		return bootboxConfirm;
	}

	public void setBootboxConfirm(String bootboxConfirm) {
		this.bootboxConfirm = bootboxConfirm;
	}

	public String getBootboxConfirmTitle() {
		return bootboxConfirmTitle;
	}

	public void setBootboxConfirmTitle(String bootboxConfirmTitle) {
		this.bootboxConfirmTitle = bootboxConfirmTitle;
	}
	
}
