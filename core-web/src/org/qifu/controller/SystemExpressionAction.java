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
import org.qifu.base.model.ScriptTypeCode;
import org.qifu.base.model.SearchValue;
import org.qifu.po.TbSysExpression;
import org.qifu.service.ISysExpressionService;
import org.qifu.service.logic.ISystemExpressionLogicService;
import org.qifu.vo.SysExpressionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class SystemExpressionAction extends BaseController {
	
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private ISystemExpressionLogicService systemExpressionLogicService;
	
	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}
	
	@Autowired
	@Resource(name="core.service.SysExpressionService")
	@Required
	public void setSysExpressionService(ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}
	
	public ISystemExpressionLogicService getSystemExpressionLogicService() {
		return systemExpressionLogicService;
	}
	
	@Autowired
	@Resource(name="core.service.logic.SystemExpressionLogicService")
	@Required
	public void setSystemExpressionLogicService(ISystemExpressionLogicService systemExpressionLogicService) {
		this.systemExpressionLogicService = systemExpressionLogicService;
	}
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		mv.addObject("typeMap", ScriptTypeCode.getTypeMap(true));
	}
	
	private void fetchData(SysExpressionVO sysExpression, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysExpressionVO> result = this.sysExpressionService.findObjectByOid(sysExpression);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysExpression = result.getValue();
		mv.addObject("sysExpression", sysExpression);
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002Q")
	@RequestMapping(value = "/core.sysExpressionManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0002Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-expression/sys-expression-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002Q")
	@RequestMapping(value = "/core.sysExpressionQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysExpressionVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysExpressionVO> > result = this.getQueryJsonResult("CORE_PROG003D0002Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysExpressionVO> > queryResult = this.sysExpressionService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002A")
	@RequestMapping(value = "/core.sysExpressionCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0002A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-expression/sys-expression-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002E")
	@RequestMapping(value = "/core.sysExpressionEdit.do")
	public ModelAndView editPage(HttpServletRequest request, SysExpressionVO sysExpression) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0002E");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysExpression, mv);
			viewName = "sys-expression/sys-expression-edit";
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
	
	private void checkFields(DefaultControllerJsonResultObj<SysExpressionVO> result, SysExpressionVO sysExpression) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("exprId", sysExpression, "@org.apache.commons.lang3.StringUtils@isBlank( exprId )", "Id is required!")
		.testField("exprId", sysExpression, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( exprId.replaceAll(\"-\", \"\").replaceAll(\"_\", \"\") )", "Id only normal character!")
		.testField("exprId", this.noSelect(sysExpression.getExprId()), "Please change Id value!") // Id 不能使用 all
		.testField("name", sysExpression, "@org.apache.commons.lang3.StringUtils@isBlank( name )", "Name is required!")
		.testField("type", ( !ScriptTypeCode.isTypeCode(sysExpression.getType()) ), "Please select type!")
		.testField("content", sysExpression, "@org.apache.commons.lang3.StringUtils@isBlank( content )", "Expression content is required!")
		.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<SysExpressionVO> result, SysExpressionVO sysExpression) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysExpression);
		DefaultResult<SysExpressionVO> cResult = this.systemExpressionLogicService.create(sysExpression);
		if ( cResult.getValue() != null ) {
			cResult.getValue().setContent( "" ); // 不要回填 content
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );		
	}
	
	private void update(DefaultControllerJsonResultObj<SysExpressionVO> result, SysExpressionVO sysExpression) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysExpression);
		DefaultResult<SysExpressionVO> uResult = this.systemExpressionLogicService.update(sysExpression);
		if ( uResult.getValue() != null ) {
			uResult.getValue().setContent( "" ); // 不要回填 content
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );		
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysExpressionVO sysExpression) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemExpressionLogicService.delete(sysExpression);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002A")
	@RequestMapping(value = "/core.sysExpressionSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysExpressionVO> doSave(SysExpressionVO sysExpression) {
		DefaultControllerJsonResultObj<SysExpressionVO> result = this.getDefaultJsonResult("CORE_PROG003D0002A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysExpression);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002E")
	@RequestMapping(value = "/core.sysExpressionUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysExpressionVO> doUpdate(SysExpressionVO sysExpression) {
		DefaultControllerJsonResultObj<SysExpressionVO> result = this.getDefaultJsonResult("CORE_PROG003D0002E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sysExpression);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0002D")
	@RequestMapping(value = "/core.sysExpressionDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysExpressionVO sysExpression) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0002D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysExpression);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
}
