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
import org.qifu.ui.impl.Grid;

public class GridTag implements Tag {
	private PageContext pageContext=null;
	private Tag parent=null;	
	
	private String id = "";
	private String xhrUrl = "";
	private String xhrParameter = "";
	private String gridFieldStructure = "";
	private String queryFunction = "";
	private String clearFunction = "";
	private String selfPleaseWaitShow = YesNo.NO;
	
	private Grid handler() {
		Grid grid = new Grid();
		grid.setPageContext(this.pageContext);
		grid.setId(this.id);
		grid.setXhrUrl(this.xhrUrl);
		grid.setXhrParameter(this.xhrParameter);
		grid.setGridFieldStructure(this.gridFieldStructure);
		grid.setQueryFunction(this.queryFunction.replaceAll("[(]", "").replaceAll("[)]", "").replaceAll(";", ""));
		grid.setClearFunction(this.clearFunction.replaceAll("[(]", "").replaceAll("[)]", "").replaceAll(";", ""));
		grid.setSelfPleaseWaitShow(this.selfPleaseWaitShow);
		return grid;
	}

	@Override
	public int doEndTag() throws JspException {
		Grid grid = this.handler();
		try {
			this.pageContext.getOut().write( grid.getHtml() );
			this.pageContext.getOut().write( grid.getScript() );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		grid = null;
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

	public String getGridFieldStructure() {
		return gridFieldStructure;
	}

	public void setGridFieldStructure(String gridFieldStructure) {
		this.gridFieldStructure = gridFieldStructure;
	}

	public String getQueryFunction() {
		return queryFunction;
	}

	public void setQueryFunction(String queryFunction) {
		this.queryFunction = queryFunction;
	}

	public String getClearFunction() {
		return clearFunction;
	}

	public void setClearFunction(String clearFunction) {
		this.clearFunction = clearFunction;
	}

	public String getSelfPleaseWaitShow() {
		return selfPleaseWaitShow;
	}

	public void setSelfPleaseWaitShow(String selfPleaseWaitShow) {
		this.selfPleaseWaitShow = selfPleaseWaitShow;
	}

}
