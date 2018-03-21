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

import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
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
import org.qifu.po.TbSysBpmnResource;
import org.qifu.po.TbSysBpmnResourceRole;
import org.qifu.service.IRoleService;
import org.qifu.service.ISysBpmnResourceRoleService;
import org.qifu.service.ISysBpmnResourceService;
import org.qifu.vo.RoleVO;
import org.qifu.vo.SysBpmnResourceRoleVO;
import org.qifu.vo.SysBpmnResourceVO;
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
public class SystemBpmResourceRoleAction extends BaseController {

	private ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	
	public ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> getSysBpmnResourceService() {
		return sysBpmnResourceService;
	}
	
	@Autowired
	@Resource(name="core.service.SysBpmnResourceService")
	@Required	
	public void setSysBpmnResourceService(ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService) {
		this.sysBpmnResourceService = sysBpmnResourceService;
	}
	
	public ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> getSysBpmnResourceRoleService() {
		return sysBpmnResourceRoleService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceRoleService")
	@Required
	public void setSysBpmnResourceRoleService(ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService) {
		this.sysBpmnResourceRoleService = sysBpmnResourceRoleService;
	}

	public IRoleService<RoleVO, TbRole, String> getRoleService() {
		return roleService;
	}
	
	@Autowired
	@Resource(name="core.service.RoleService")
	@Required	
	public void setRoleService(IRoleService<RoleVO, TbRole, String> roleService) {
		this.roleService = roleService;
	}	
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		if ("queryPage".equals(type) || "createPage".equals(type)) {
			mv.addObject("resourceMap", this.sysBpmnResourceService.findForMap(true));
		}
		if ("createPage".equals(type)) {
			mv.addObject("roleMap", this.roleService.findForMap(true, false));
		}
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0005Q")
	@RequestMapping(value = "/core.sysBpmResourceRoleManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0005Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-bpm-role/sys-bpm-role-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0005Q")
	@RequestMapping(value = "/core.sysBpmResourceRoleQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysBpmnResourceRoleVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysBpmnResourceRoleVO> > result = this.getQueryJsonResult("CORE_PROG003D0005Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysBpmnResourceRoleVO> > queryResult = this.sysBpmnResourceRoleService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0005A")
	@RequestMapping(value = "/core.sysBpmResourceRoleCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0005A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-bpm-role/sys-bpm-role-create";
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
	
	private void checkFields(DefaultControllerJsonResultObj<SysBpmnResourceRoleVO> result, SysBpmnResourceRoleVO sysBpmnResourceRole, String resourceOid, String roleOid) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("resourceOid", this.noSelect(resourceOid), "Please select resource!")
		.testField("roleOid", this.noSelect(roleOid), "Please select role!")
		.testField("taskName", sysBpmnResourceRole, "@org.apache.commons.lang3.StringUtils@isBlank( taskName )", "Task name is required!")
		.throwMessage();
	}	
	
	private void save(DefaultControllerJsonResultObj<SysBpmnResourceRoleVO> result, SysBpmnResourceRoleVO sysBpmnResourceRole, String resourceOid, String roleOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysBpmnResourceRole, resourceOid, roleOid);
		TbSysBpmnResource resourceObj = this.sysBpmnResourceService.findByPKng(resourceOid);
		if (null == resourceObj) {
			throw new ControllerException( SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST) );
		}
		TbRole roleObj = this.roleService.findByPKng(roleOid);
		if (null == roleObj) {
			throw new ControllerException( SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST) );
		}		
		sysBpmnResourceRole.setId(resourceObj.getId());
		sysBpmnResourceRole.setRole(roleObj.getRole());
		DefaultResult<SysBpmnResourceRoleVO> cResult = this.sysBpmnResourceRoleService.saveObject(sysBpmnResourceRole);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );
	}		
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysBpmnResourceRoleVO sysBpmnResourceRole) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.sysBpmnResourceRoleService.deleteObject(sysBpmnResourceRole);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0005A")
	@RequestMapping(value = "/core.sysBpmResourceRoleSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBpmnResourceRoleVO> doSave(SysBpmnResourceRoleVO sysBpmnResourceRole, @RequestParam("resourceOid") String resourceOid, @RequestParam("roleOid") String roleOid) {
		DefaultControllerJsonResultObj<SysBpmnResourceRoleVO> result = this.getDefaultJsonResult("CORE_PROG003D0005A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysBpmnResourceRole, resourceOid, roleOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0005D")
	@RequestMapping(value = "/core.sysBpmResourceRoleDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysBpmnResourceRoleVO sysBpmnResourceRole) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0005D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysBpmnResourceRole);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
