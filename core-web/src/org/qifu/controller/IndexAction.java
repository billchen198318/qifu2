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
package org.qifu.controller;

import javax.servlet.http.HttpServletRequest;

import org.qifu.base.controller.BaseController;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.model.MenuResultObj;
import org.qifu.util.IconUtils;
import org.qifu.util.MenuSupportUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexAction extends BaseController {
	
	@ControllerMethodAuthority(check = false, programId = "CORE_PROGCOMM0001Q")
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			mv.addObject("firstLoadJavascript", MenuSupportUtils.getFirstLoadJavascript());
			MenuResultObj menuResult = MenuSupportUtils.getMenuData(this.getBasePath(request));
			mv.addObject("menuJavascriptData", menuResult.getJavascriptData());
			mv.addObject("dropdownHtmlData", menuResult.getDropdownHtmlData());
			mv.addObject("navItemHtmlData",  menuResult.getNavItemHtmlData());
			mv.addObject("iconJavascriptData", IconUtils.getJsData());
			mv.addObject("modalHtmlData",  menuResult.getModalHtmlData());
			viewName = "index";
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;
	}
	
}
