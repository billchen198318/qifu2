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
package org.qifu.model;

public class MenuResultObj implements java.io.Serializable {
	private static final long serialVersionUID = -8662108979333485489L;

	/**
	 * 下拉選單的 html
	 */
	private String dropdownHtmlData = "";
	
	/**
	 * 左邊選單的 html
	 */
	private String navItemHtmlData = "";
	
	/**
	 * 選單的 javascript
	 */
	private String javascriptData = "";
	
	/**
	 * modal 的 html
	 */
	private String modalHtmlData = "";

	public String getDropdownHtmlData() {
		return dropdownHtmlData;
	}

	public void setDropdownHtmlData(String dropdownHtmlData) {
		this.dropdownHtmlData = dropdownHtmlData;
	}

	public String getNavItemHtmlData() {
		return navItemHtmlData;
	}

	public void setNavItemHtmlData(String navItemHtmlData) {
		this.navItemHtmlData = navItemHtmlData;
	}

	public String getJavascriptData() {
		return javascriptData;
	}

	public void setJavascriptData(String javascriptData) {
		this.javascriptData = javascriptData;
	}

	public String getModalHtmlData() {
		return modalHtmlData;
	}

	public void setModalHtmlData(String modalHtmlData) {
		this.modalHtmlData = modalHtmlData;
	}	
	
}
