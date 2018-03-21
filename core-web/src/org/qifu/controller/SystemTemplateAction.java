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
import org.qifu.po.TbSysTemplate;
import org.qifu.po.TbSysTemplateParam;
import org.qifu.service.ISysTemplateParamService;
import org.qifu.service.ISysTemplateService;
import org.qifu.service.logic.ISystemTemplateLogicService;
import org.qifu.util.SimpleUtils;
import org.qifu.vo.SysTemplateParamVO;
import org.qifu.vo.SysTemplateVO;
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
public class SystemTemplateAction extends BaseController {
	
	private ISysTemplateService<SysTemplateVO, TbSysTemplate, String> sysTemplateService;
	private ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> sysTemplateParamService;
	private ISystemTemplateLogicService systemTemplateLogicService;
	
	public ISysTemplateService<SysTemplateVO, TbSysTemplate, String> getSysTemplateService() {
		return sysTemplateService;
	}
	
	@Autowired
	@Resource(name="core.service.SysTemplateService")
	@Required
	public void setSysTemplateService(ISysTemplateService<SysTemplateVO, TbSysTemplate, String> sysTemplateService) {
		this.sysTemplateService = sysTemplateService;
	}
	
	public ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> getSysTemplateParamService() {
		return sysTemplateParamService;
	}

	@Autowired
	@Resource(name="core.service.SysTemplateParamService")
	@Required
	public void setSysTemplateParamService(
			ISysTemplateParamService<SysTemplateParamVO, TbSysTemplateParam, String> sysTemplateParamService) {
		this.sysTemplateParamService = sysTemplateParamService;
	}

	public ISystemTemplateLogicService getSystemTemplateLogicService() {
		return systemTemplateLogicService;
	}

	@Autowired
	@Resource(name="core.service.logic.SystemTemplateLogicService")
	@Required	
	public void setSystemTemplateLogicService(ISystemTemplateLogicService systemTemplateLogicService) {
		this.systemTemplateLogicService = systemTemplateLogicService;
	}
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		
	}
	
	private void fetchData(SysTemplateVO template, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysTemplateVO> result = this.sysTemplateService.findObjectByOid(template);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		template = result.getValue();
		mv.addObject("template", template);
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004Q")
	@RequestMapping(value = "/core.templateManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0004Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-template/sys-template-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004Q")
	@RequestMapping(value = "/core.templateQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysTemplateVO>>  queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysTemplateVO> > result = this.getQueryJsonResult("CORE_PROG001D0004Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysTemplateVO> > queryResult = this.sysTemplateService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004A")
	@RequestMapping(value = "/core.templateCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0004A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-template/sys-template-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004E")
	@RequestMapping(value = "/core.templateEdit.do")
	public ModelAndView editPage(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0004E");
		try {
			SysTemplateVO template = new SysTemplateVO();
			template.setOid(oid);
			this.init("editPage", request, mv);
			this.fetchData(template, mv);
			viewName = "sys-template/sys-template-edit";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004S01Q")
	@RequestMapping(value = "/core.templateParam.do")
	public ModelAndView paramPage(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG001D0004S01Q");
		try {
			SysTemplateVO template = new SysTemplateVO();
			template.setOid(oid);
			this.init("editParamPage", request, mv);
			this.fetchData(template, mv);
			viewName = "sys-template/sys-template-param";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004S01Q")
	@RequestMapping(value = "/core.templateParamQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysTemplateParamVO>>  paramQueryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysTemplateParamVO> > result = this.getQueryJsonResult("CORE_PROG001D0004S01Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysTemplateParamVO> > queryResult = this.sysTemplateParamService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	private void checkFields(DefaultControllerJsonResultObj<SysTemplateVO> result, SysTemplateVO template) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("templateId", template, "@org.apache.commons.lang3.StringUtils@isBlank(templateId)", "Id is blank!")
		.testField("templateId", ( !SimpleUtils.checkBeTrueOf_azAZ09(super.defaultString(template.getTemplateId()).replaceAll("-", "").replaceAll("_", "")) ), "Id only normal character!")
		.testField("templateId", ( this.noSelect(template.getTemplateId()) ), "Please change Id value!") // Role 不能用  "all" 這個下拉值
		.testField("title", template, "@org.apache.commons.lang3.StringUtils@isBlank(title)", "Title is blank!")
		.testField("message", template, "@org.apache.commons.lang3.StringUtils@isBlank(message)", "Message is blank!")
		.throwMessage();		
	}
	
	private void checkFieldsForParam(DefaultControllerJsonResultObj<SysTemplateParamVO> result, SysTemplateParamVO templateParam) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("templateVar", templateParam, "@org.apache.commons.lang3.StringUtils@isBlank(templateVar)", "Template variable is blank!")
		.testField("objectVar", templateParam, "@org.apache.commons.lang3.StringUtils@isBlank(objectVar)", "Object variable is blank!")
		.testField("templateVar", templateParam, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09(templateVar)", "Template variable only normal character!")
		.testField("objectVar", templateParam, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09(objectVar)", "Object variable only normal character!")
		.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<SysTemplateVO> result, SysTemplateVO template) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, template);
		DefaultResult<SysTemplateVO> tResult = this.systemTemplateLogicService.create(template);
		if ( tResult.getValue() != null ) {
			result.setValue( tResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( tResult.getSystemMessage().getValue() );
	}
	
	private void update(DefaultControllerJsonResultObj<SysTemplateVO> result, SysTemplateVO template) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, template);
		DefaultResult<SysTemplateVO> tResult = this.systemTemplateLogicService.update(template);
		if ( tResult.getValue() != null ) {
			result.setValue( tResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( tResult.getSystemMessage().getValue() );		
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysTemplateVO template) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> tResult = this.systemTemplateLogicService.delete(template);
		if ( tResult.getValue() != null && tResult.getValue() ) {
			result.setValue( Boolean.TRUE );
			result.setSuccess( YES );
		}
		result.setMessage( tResult.getSystemMessage().getValue() );
	}
	
	private void saveParam(DefaultControllerJsonResultObj<SysTemplateParamVO> result, String templateOid, SysTemplateParamVO templateParam) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFieldsForParam(result, templateParam);
		DefaultResult<SysTemplateParamVO> tResult = this.systemTemplateLogicService.createParam(templateParam, templateOid);
		if ( tResult.getValue() != null ) {
			result.setValue( tResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( tResult.getSystemMessage().getValue() );
	}	
	
	private void deleteParam(DefaultControllerJsonResultObj<Boolean> result, SysTemplateParamVO templateParam) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> tResult = this.systemTemplateLogicService.deleteParam(templateParam);
		if ( tResult.getValue() != null && tResult.getValue() ) {
			result.setValue( Boolean.TRUE );
			result.setSuccess( YES );
		}
		result.setMessage( tResult.getSystemMessage().getValue() );
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004A")
	@RequestMapping(value = "/core.templateSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysTemplateVO> doSave(SysTemplateVO template) {
		DefaultControllerJsonResultObj<SysTemplateVO> result = this.getDefaultJsonResult("CORE_PROG001D0004A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, template);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004E")
	@RequestMapping(value = "/core.templateUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysTemplateVO> doUpdate(SysTemplateVO template) {
		DefaultControllerJsonResultObj<SysTemplateVO> result = this.getDefaultJsonResult("CORE_PROG001D0004E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, template);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004D")
	@RequestMapping(value = "/core.templateDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysTemplateVO template) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG001D0004D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, template);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004S01A")
	@RequestMapping(value = "/core.templateParamSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysTemplateParamVO> doParamSave(@RequestParam("templateOid") String templateOid, SysTemplateParamVO templateParam) {
		DefaultControllerJsonResultObj<SysTemplateParamVO> result = this.getDefaultJsonResult("CORE_PROG001D0004S01A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.saveParam(result, templateOid, templateParam);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG001D0004S01D")
	@RequestMapping(value = "/core.templateParamDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDeleteParam(SysTemplateParamVO templateParam) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG001D0004S01D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.deleteParam(result, templateParam);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
