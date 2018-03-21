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

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.qifu.base.Constants;
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
import org.qifu.model.UploadTypes;
import org.qifu.po.TbSysBpmnResource;
import org.qifu.service.ISysBpmnResourceService;
import org.qifu.util.BusinessProcessManagementUtils;
import org.qifu.util.UploadSupportUtils;
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
public class SystemBpmResourceAction extends BaseController {
	
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	
	public ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> getSysBpmnResourceService() {
		return sysBpmnResourceService;
	}
	
	@Autowired
	@Resource(name="core.service.SysBpmnResourceService")
	@Required	
	public void setSysBpmnResourceService(ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService) {
		this.sysBpmnResourceService = sysBpmnResourceService;
	}
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		
	}
	
	private void fetchData(SysBpmnResourceVO sysBpmnResource, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.findObjectByOid(sysBpmnResource);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysBpmnResource = result.getValue();
		sysBpmnResource.setContent( null ); // 不需要把 byte[] 內容帶回頁面
		mv.addObject("sysBpmnResource", sysBpmnResource);
	}
	
	private void queryProcess(SysBpmnResourceVO sysBpmnResource, ModelAndView mv) throws Exception {
		List<ProcessDefinition> processDefinitions = BusinessProcessManagementUtils.queryProcessDefinition(sysBpmnResource);
		List<ProcessInstance> processInstances = BusinessProcessManagementUtils.queryProcessInstance(sysBpmnResource);
		List<Task> tasks = BusinessProcessManagementUtils.queryTask(sysBpmnResource);
		mv.addObject("processDefinitions", processDefinitions);
		mv.addObject("processInstances", processInstances);
		mv.addObject("tasks", tasks);
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004Q")
	@RequestMapping(value = "/core.sysBpmResourceManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0004Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-bpm/sys-bpm-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004Q")
	@RequestMapping(value = "/core.sysBpmResourceQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysBpmnResourceVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysBpmnResourceVO> > result = this.getQueryJsonResult("CORE_PROG003D0004Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysBpmnResourceVO> > queryResult = this.sysBpmnResourceService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004A")
	@RequestMapping(value = "/core.sysBpmResourceCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0004A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-bpm/sys-bpm-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004E")
	@RequestMapping(value = "/core.sysBpmResourceEdit.do")
	public ModelAndView editPage(HttpServletRequest request, SysBpmnResourceVO sysBpmnResource) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0004E");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysBpmnResource, mv);
			viewName = "sys-bpm/sys-bpm-edit";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004S01Q")
	@RequestMapping(value = "/core.sysBpmResourceProcessObjectList.do")
	public ModelAndView processObjectList(HttpServletRequest request, SysBpmnResourceVO sysBpmnResource) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0004S01Q");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysBpmnResource, mv);
			this.queryProcess( (SysBpmnResourceVO) mv.getModel().get("sysBpmnResource"), mv);
			viewName = "sys-bpm/sys-bpm-proclist";
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
	
	private void selfTestUploadResourceData(DefaultControllerJsonResultObj<SysBpmnResourceVO> result, SysBpmnResourceVO sysBpmnResource, String uploadOid ) throws ControllerException, AuthorityException, ServiceException, Exception {
		if (StringUtils.isBlank(sysBpmnResource.getId())) {
			throw new ControllerException("Id is blank!");
		}
		String id = sysBpmnResource.getId();		
		String resourceFileProcessId = BusinessProcessManagementUtils.getResourceProcessId4Upload(uploadOid);
		if (!id.equals(resourceFileProcessId)) {
			this.getCheckControllerFieldHandler(result).throwMessage("id", "Resource file process-Id not equals Id field!");
		}
	}	
	
	private void fillContent(SysBpmnResourceVO resource, String uploadOid) throws ServiceException, Exception {
		byte[] content = UploadSupportUtils.getDataBytes( uploadOid );
		resource.setContent(content);
	}		
	
	private void checkFields(DefaultControllerJsonResultObj<SysBpmnResourceVO> result, SysBpmnResourceVO sysBpmnResource) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("id", sysBpmnResource, "@org.apache.commons.lang3.StringUtils@isBlank( id )", "Id is required!")
		.testField("id", sysBpmnResource, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( id )", "Id only normal character!")
		.testField("name", sysBpmnResource, "@org.apache.commons.lang3.StringUtils@isBlank( name )", "Name is required!")
		.throwMessage();
	}
	
	private void deployment(DefaultControllerJsonResultObj<SysBpmnResourceVO> result, boolean overDeployment, String sysBpmnResourceOid) throws ControllerException, AuthorityException, ServiceException, Exception {
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		resource.setOid( sysBpmnResourceOid );
		DefaultResult<SysBpmnResourceVO> rResult = this.sysBpmnResourceService.findObjectByOid(resource);
		if (rResult.getValue()==null) {
			throw new ServiceException( rResult.getSystemMessage().getValue() );
		}
		resource = rResult.getValue();
		sysBpmnResourceService.hibernateSessionClear();
		if (!overDeployment && !StringUtils.isBlank(resource.getDeploymentId())) {
			result.setMessage( "Already deployment!" );
			return;
		}
		String id = BusinessProcessManagementUtils.deployment(resource);		
		if (!StringUtils.isBlank(id)) {
			result.setMessage( "Already deployment!" );
			result.setSuccess( YES );
		} else {
			result.setMessage( "Deployment fail!" );
		}
	}	
	
	private void exportDiagram(DefaultControllerJsonResultObj<String> result, String type, String objectId, String resourceId) throws ControllerException, AuthorityException, ServiceException, Exception {
		String uploadOid = "";
		if ("processDefinition".equals(type)) {
			uploadOid = BusinessProcessManagementUtils.getProcessDefinitionDiagramById2Upload(objectId);
		} else if ("processInstance".equals(type)) {
			uploadOid = BusinessProcessManagementUtils.getProcessInstanceDiagramById2Upload(objectId);
		} else { // task
			uploadOid = BusinessProcessManagementUtils.getTaskDiagramById2Upload(resourceId, objectId);
		}		
		if (!StringUtils.isBlank(uploadOid)) {
			result.setValue( uploadOid );
			result.setSuccess( YES );
			result.setMessage( SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS) );
		} else {
			result.setMessage( SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST) );
		}
	}
	
	private void export(DefaultControllerJsonResultObj<String> result, SysBpmnResourceVO sysBpmnResource) throws ControllerException, AuthorityException, ServiceException, Exception {
		DefaultResult<SysBpmnResourceVO> rResult = this.sysBpmnResourceService.findObjectByOid(sysBpmnResource);
		if (rResult.getValue()==null) {
			throw new ServiceException( rResult.getSystemMessage().getValue() );
		}
		sysBpmnResource = rResult.getValue();		
		sysBpmnResourceService.hibernateSessionClear();
		result.setValue( UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, false, sysBpmnResource.getContent(), sysBpmnResource.getId()+".zip") );
		result.setMessage( SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS) );
		result.setSuccess( YES );
	}
	
	private void save(DefaultControllerJsonResultObj<SysBpmnResourceVO> result, SysBpmnResourceVO sysBpmnResource, String uploadOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		if (StringUtils.isBlank(uploadOid)) {
			throw new ControllerException("Please upload BPM file(zip)!");
		}		
		this.checkFields(result, sysBpmnResource);
		this.selfTestUploadResourceData(result, sysBpmnResource, uploadOid);
		this.fillContent(sysBpmnResource, uploadOid);
		if (StringUtils.defaultString(sysBpmnResource.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			sysBpmnResource.setDescription( sysBpmnResource.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		DefaultResult<SysBpmnResourceVO> cResult = this.sysBpmnResourceService.saveObject(sysBpmnResource);
		if ( cResult.getValue() != null ) {
			cResult.getValue().setContent( null ); // 不要回填 content
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );
	}		
	
	private void update(DefaultControllerJsonResultObj<SysBpmnResourceVO> result, SysBpmnResourceVO sysBpmnResource, String uploadOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysBpmnResource);
		if (!StringUtils.isBlank(uploadOid) ) { // 有更新上傳檔案
			this.selfTestUploadResourceData(result, sysBpmnResource, uploadOid);
		}		
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		DefaultResult<SysBpmnResourceVO> oldResult = this.sysBpmnResourceService.findObjectByOid( sysBpmnResource );
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		resource = oldResult.getValue();
		String beforeId = resource.getId();
		byte[] beforeContent = resource.getContent();
		resource.setContent( null );
		sysBpmnResourceService.updateObject(resource); // 先清除原本的byte資料, 要不然每次update 資料越來越大
		if (!StringUtils.isBlank(uploadOid) ) {
			this.fillContent(sysBpmnResource, uploadOid);
		} else {
			sysBpmnResource.setContent(beforeContent);
		}
		if (StringUtils.defaultString(sysBpmnResource.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			sysBpmnResource.setDescription( sysBpmnResource.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		sysBpmnResource.setId( beforeId );
		DefaultResult<SysBpmnResourceVO> uResult = this.sysBpmnResourceService.updateObject( sysBpmnResource );
		if ( uResult.getValue() != null ) {
			String oldDeploymentIdId = uResult.getValue().getDeploymentId();
			if (!StringUtils.isBlank(oldDeploymentIdId) && !StringUtils.isBlank(uploadOid)) { // 更新上傳部屬
				BusinessProcessManagementUtils.deleteDeployment(uResult.getValue(), false);				
				this.deployment(result, true, uResult.getValue().getOid());
			}
			uResult.getValue().setContent( null ); // 不要回填 content
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}		
		result.setMessage( uResult.getSystemMessage().getValue() );
	}	
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysBpmnResourceVO sysBpmnResource) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<SysBpmnResourceVO> rResult = this.sysBpmnResourceService.findObjectByOid(sysBpmnResource);
		if ( rResult.getValue() == null ) {
			throw new ServiceException( rResult.getSystemMessage().getValue() );
		}
		sysBpmnResource = rResult.getValue();
		sysBpmnResourceService.hibernateSessionClear();
		if (!StringUtils.isBlank(sysBpmnResource.getDeploymentId())) {
			BusinessProcessManagementUtils.deleteDeployment(sysBpmnResource, false);
			rResult = this.sysBpmnResourceService.findObjectByOid( sysBpmnResource );
			if (rResult.getValue()==null) {
				throw new ServiceException( rResult.getSystemMessage().getValue() );
			}
			sysBpmnResource = rResult.getValue();
			sysBpmnResourceService.hibernateSessionClear();
			if (!StringUtils.isBlank(sysBpmnResource.getDeploymentId())) {
				result.setMessage("Cannot delete deployment!");
				return;
			}			
		}
		DefaultResult<Boolean> dResult = this.sysBpmnResourceService.deleteObject(sysBpmnResource);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );	
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004A")
	@RequestMapping(value = "/core.sysBpmResourceSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBpmnResourceVO> doSave(SysBpmnResourceVO sysBpmnResource, @RequestParam("uploadOid") String uploadOid) {
		DefaultControllerJsonResultObj<SysBpmnResourceVO> result = this.getDefaultJsonResult("CORE_PROG003D0004A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysBpmnResource, uploadOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004E")
	@RequestMapping(value = "/core.sysBpmResourceUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBpmnResourceVO> doUpdate(SysBpmnResourceVO sysBpmnResource, @RequestParam("uploadOid") String uploadOid) {
		DefaultControllerJsonResultObj<SysBpmnResourceVO> result = this.getDefaultJsonResult("CORE_PROG003D0004E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sysBpmnResource, uploadOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004D")
	@RequestMapping(value = "/core.sysBpmResourceDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysBpmnResourceVO sysBpmnResource) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0004D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysBpmnResource);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004E")
	@RequestMapping(value = "/core.sysBpmResourceDeploymentJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBpmnResourceVO> doDeployment(SysBpmnResourceVO sysBpmnResource) {
		DefaultControllerJsonResultObj<SysBpmnResourceVO> result = this.getDefaultJsonResult("CORE_PROG003D0004E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.deployment(result, false, sysBpmnResource.getOid());
			if ( YES.equals(result.getSuccess()) ) {
				result.setValue(sysBpmnResource);
			}
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004S01Q")
	@RequestMapping(value = "/core.sysBpmResourceExportDiagramJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<String> doExportDiagram(@RequestParam("type") String type, @RequestParam("objectId") String objectId, @RequestParam("resourceId") String resourceId) {
		DefaultControllerJsonResultObj<String> result = this.getDefaultJsonResult("CORE_PROG003D0004E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.exportDiagram(result, type, objectId, resourceId);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0004Q")
	@RequestMapping(value = "/core.sysBpmResourceDownloadJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<String> doDownload(SysBpmnResourceVO sysBpmnResource) {
		DefaultControllerJsonResultObj<String> result = this.getDefaultJsonResult("CORE_PROG003D0004E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.export(result, sysBpmnResource);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
}
