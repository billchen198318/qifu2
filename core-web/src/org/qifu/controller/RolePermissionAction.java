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
import org.qifu.po.TbRolePermission;
import org.qifu.service.IRolePermissionService;
import org.qifu.service.IRoleService;
import org.qifu.service.logic.IRoleLogicService;
import org.qifu.vo.RolePermissionVO;
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
public class RolePermissionAction extends BaseController {
	
	private IRoleService<RoleVO, TbRole, String> roleService;
	private IRolePermissionService<RolePermissionVO, TbRolePermission, String> rolePermissionService;
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
	
	public IRolePermissionService<RolePermissionVO, TbRolePermission, String> getRolePermissionService() {
		return rolePermissionService;
	}

	@Autowired
	@Resource(name="core.service.RolePermissionService")
	@Required		
	public void setRolePermissionService(IRolePermissionService<RolePermissionVO, TbRolePermission, String> rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
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
		Map<String, String> permTypeMap = this.getPleaseSelectMap(true);
		permTypeMap.put("CONTROLLER", "Controller");
		permTypeMap.put("COMPOMENT", "Compoment/Service");
		mv.addObject("permTypeMap", permTypeMap);
		
		RoleVO role = new RoleVO();
		this.fillObjectFromRequest(request, role);
		DefaultResult<RoleVO> roleResult = this.roleService.findObjectByOid(role);
		if ( roleResult.getValue() == null ) {
			throw new ControllerException( roleResult.getSystemMessage().getValue() );
		}
		role = roleResult.getValue();
		mv.addObject("role", role);
		
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001S01Q")
	@RequestMapping(value = "/core.rolePermissionManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG002D0001S01Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "role/permission-management";
			RoleVO role = (RoleVO) mv.getModel().get("role");
			if (Constants.SUPER_ROLE_ADMIN.equals(role.getRole()) || Constants.SUPER_ROLE_ALL.equals(role.getRole())) {
				viewName = PAGE_SYS_WARNING;
				this.setPageMessage(mv, "Super/Admin role no need set permission!");
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001S01Q")
	@RequestMapping(value = "/core.rolePermissionQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj<List<RolePermissionVO>> queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj<List<RolePermissionVO>> result = this.getQueryJsonResult("CORE_PROG002D0001S01Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult<List<RolePermissionVO>> queryResult = this.rolePermissionService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	private void checkFields(DefaultControllerJsonResultObj<RolePermissionVO> result, RolePermissionVO rolePermission) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("permission", rolePermission, "@org.apache.commons.lang3.StringUtils@isBlank(permission)", "Permission is blank!")
		.testField("permissionType", ( this.noSelect(rolePermission.getPermType()) ), "Please select type!") 
		.throwMessage();		
	}
	
	private void save(DefaultControllerJsonResultObj<RolePermissionVO> result, RolePermissionVO rolePermission, String roleOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, rolePermission);
		DefaultResult<RolePermissionVO> permResult = this.roleLogicService.createPermission(rolePermission, roleOid);
		if ( permResult.getValue() != null ) {
			result.setValue( permResult.getValue() );
			result.setSuccess( YES );			
		}
		result.setMessage( permResult.getSystemMessage().getValue() );
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, RolePermissionVO rolePermission) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> permResult = this.roleLogicService.deletePermission(rolePermission);
		if ( permResult.getValue() != null && permResult.getValue() ) {
			result.setValue( Boolean.TRUE );
			result.setSuccess( YES );
		}
		result.setMessage( permResult.getSystemMessage().getValue() );
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001S01A")
	@RequestMapping(value = "/core.rolePermissionSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<RolePermissionVO> doSave(RolePermissionVO rolePermission, @RequestParam(name="roleOid") String roleOid) {
		DefaultControllerJsonResultObj<RolePermissionVO> result = this.getDefaultJsonResult("CORE_PROG002D0001S01A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, rolePermission, roleOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG002D0001S01D")
	@RequestMapping(value = "/core.rolePermissionDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(RolePermissionVO rolePermission) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG002D0001S01D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, rolePermission);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
}
