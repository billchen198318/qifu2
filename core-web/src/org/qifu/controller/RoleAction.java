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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.qifu.base.Constants;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryControllerJsonResultObj;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.po.TbRole;
import org.qifu.service.IRoleService;
import org.qifu.service.logic.IRoleLogicService;
import org.qifu.util.SimpleUtils;
import org.qifu.vo.RoleVO;
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
public class RoleAction extends BaseController {
	
	private IRoleService<RoleVO, TbRole, String> roleService;
	private IRoleLogicService roleLogicService; 
	
	public IRoleService<RoleVO, TbRole, String> getRoleService() {
		return roleService;
	}

	@Autowired
	@Resource(name="core.service.RoleService")
	@Required	
	public void setRoleService(IRoleService<RoleVO, TbRole, String> roleService) {
		this.roleService = roleService;
	}
	
	public IRoleLogicService getRoleLogicService() {
		return roleLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.RoleLogicService")
	@Required		
	public void setRoleLogicService(IRoleLogicService roleLogicService) {
		this.roleLogicService = roleLogicService;
	}

	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		
	}
	
	private void fetchData(RoleVO role, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<RoleVO> roleResult = this.roleService.findObjectByOid(role);
		if ( roleResult.getValue() == null ) {
			throw new ControllerException( roleResult.getSystemMessage().getValue() );
		}
		role = roleResult.getValue();
		mv.addObject("role", role);
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001Q")
	@RequestMapping(value = "/core.roleManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG002D0001Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "role/role-management";
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
	
	private void checkFields(DefaultControllerJsonResultObj<RoleVO> result, RoleVO role) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("role", role, "@org.apache.commons.lang3.StringUtils@isBlank(role)", "Role is blank!")
		.testField("role", ( !SimpleUtils.checkBeTrueOf_azAZ09(super.defaultString(role.getRole()).replaceAll("-", "").replaceAll("_", "")) ), "Role only normal character!")
		.testField("role", ( this.noSelect(role.getRole()) ), "Please change Role value!") // Role 不能用  "all" 這個下拉值
		.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<RoleVO> result, RoleVO role) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, role);
		DefaultResult<RoleVO> roleResult = this.roleLogicService.create(role);
		if ( roleResult.getValue() != null ) {
			result.setValue( roleResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( roleResult.getSystemMessage().getValue() );
	}
	
	private void update(DefaultControllerJsonResultObj<RoleVO> result, RoleVO role) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, role);
		DefaultResult<RoleVO> roleResult = this.roleLogicService.update(role);
		if ( roleResult.getValue() != null ) {
			result.setValue( roleResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( roleResult.getSystemMessage().getValue() );
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, RoleVO role) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> roleResult = this.roleLogicService.delete(role);
		if ( roleResult.getValue() != null && roleResult.getValue() ) {
			result.setValue( Boolean.TRUE );
			result.setSuccess( YES );
		}
		result.setMessage( roleResult.getSystemMessage().getValue() );
	}
	
	private void saveAsNew(DefaultControllerJsonResultObj<RoleVO> result, String fromRoleOid, RoleVO role) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, role);
		DefaultResult<RoleVO> roleResult = this.roleLogicService.copyAsNew(fromRoleOid, role);
		if ( roleResult.getValue() != null ) {
			result.setValue( roleResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( roleResult.getSystemMessage().getValue() );
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001Q")
	@RequestMapping(value = "/core.roleQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj<List<RoleVO>> queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj<List<RoleVO>> result = this.getQueryJsonResult("CORE_PROG002D0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult<List<RoleVO>> queryResult = this.roleService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001A")
	@RequestMapping(value = "/core.roleCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG002D0001A");
		try {
			this.init("createPage", request, mv);
			viewName = "role/role-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001E")
	@RequestMapping(value = "/core.roleEdit.do")
	public ModelAndView editPage(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG002D0001E");
		try {
			RoleVO role = new RoleVO();
			role.setOid(oid);
			this.init("editPage", request, mv);
			this.fetchData(role, mv);
			viewName = "role/role-edit";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001S02Q")
	@RequestMapping(value = "/core.roleCopyManagement.do")
	public ModelAndView copyAsNewPage(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG002D0001S02Q");
		try {
			RoleVO role = new RoleVO();
			role.setOid(oid);
			this.init("copyAsNewPage", request, mv);
			this.fetchData(role, mv);
			viewName = "role/role-copy";
			role = (RoleVO) mv.getModel().get("role");
			if (Constants.SUPER_ROLE_ADMIN.equals(role.getRole()) || Constants.SUPER_ROLE_ALL.equals(role.getRole())) {
				viewName = PAGE_SYS_WARNING;
				this.setPageMessage(mv, "Super/Admin cannot copy as new!");
			}			
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001A")
	@RequestMapping(value = "/core.roleSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<RoleVO> doSave(RoleVO role) {
		DefaultControllerJsonResultObj<RoleVO> result = this.getDefaultJsonResult("CORE_PROG002D0001A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, role);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001E")
	@RequestMapping(value = "/core.roleUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<RoleVO> doUpdate(RoleVO role) {
		DefaultControllerJsonResultObj<RoleVO> result = this.getDefaultJsonResult("CORE_PROG002D0001E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, role);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001D")
	@RequestMapping(value = "/core.roleDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(RoleVO role) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG002D0001D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, role);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001S02A")
	@RequestMapping(value = "/core.roleCopySaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<RoleVO> doSaveCopyAsNew(@RequestParam(name="fromRoleOid") String fromRoleOid, RoleVO role) {
		DefaultControllerJsonResultObj<RoleVO> result = this.getDefaultJsonResult("CORE_PROG002D0001S02A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.saveAsNew(result, fromRoleOid, role);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
}
