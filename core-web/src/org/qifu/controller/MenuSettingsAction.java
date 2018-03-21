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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.TbSys;
import org.qifu.service.ISysService;
import org.qifu.service.logic.ISystemMenuLogicService;
import org.qifu.vo.SysProgVO;
import org.qifu.vo.SysVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class MenuSettingsAction extends BaseController {
	
	private ISysService<SysVO, TbSys, String> sysService;
	private ISystemMenuLogicService systemMenuLogicService;
	
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}	
	
	public ISystemMenuLogicService getSystemMenuLogicService() {
		return systemMenuLogicService;
	}
	
	@Autowired
	@Resource(name="core.service.logic.SystemMenuLogicService")
	@Required	
	public void setSystemMenuLogicService(ISystemMenuLogicService systemMenuLogicService) {
		this.systemMenuLogicService = systemMenuLogicService;
	}

	private void init(HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		mv.addObject("sysMap", this.sysService.findSysMap(this.getBasePath(request), true));
		mv.addObject("folderProgMap", this.getPleaseSelectMap(true));
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0003Q")
	@RequestMapping(value = "/core.menuSettingsManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0003Q");
		try {
			this.init(request, mv);
			viewName = "menu-settings/menu-settings-management";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;		
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0003Q")
	@RequestMapping(value = "/core.menuSettingsQueryProgramListByFolderOidJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj< Map<String, List<SysProgVO>> > queryProgramListByFolderOid(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		DefaultControllerJsonResultObj< Map<String, List<SysProgVO>> > result = this.getDefaultJsonResult("CORE_PROG001D0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}		
		try {
			Map<String, List<SysProgVO>> searchDataMap = this.systemMenuLogicService.findForMenuSettingsEnableAndAll(oid);
			result.setValue( searchDataMap );
			result.setSuccess( YES );
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}		
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0003Q")
	@RequestMapping(value = "/core.menuSettingsUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> updateMenu(HttpServletRequest request, @RequestParam(name="folderProgramOid") String folderProgramOid, @RequestParam(name="appendOid") String appendOid) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG001D0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			DefaultResult<Boolean> updateResult = this.systemMenuLogicService.createOrUpdate(folderProgramOid, this.transformAppendKeyStringToList(appendOid));
			if (updateResult.getValue() != null && updateResult.getValue()) {
				result.setSuccess(YES);
			}
			result.setMessage( updateResult.getSystemMessage().getValue() );
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
}
