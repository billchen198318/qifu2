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
import org.qifu.base.model.CheckControllerFieldHandler;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryControllerJsonResultObj;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.model.ScriptExpressionRunType;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysBeanHelp;
import org.qifu.po.TbSysBeanHelpExpr;
import org.qifu.po.TbSysBeanHelpExprMap;
import org.qifu.po.TbSysExpression;
import org.qifu.service.ISysBeanHelpExprMapService;
import org.qifu.service.ISysBeanHelpExprService;
import org.qifu.service.ISysBeanHelpService;
import org.qifu.service.ISysExpressionService;
import org.qifu.service.ISysService;
import org.qifu.service.logic.ISystemBeanHelpLogicService;
import org.qifu.vo.SysBeanHelpExprMapVO;
import org.qifu.vo.SysBeanHelpExprVO;
import org.qifu.vo.SysBeanHelpVO;
import org.qifu.vo.SysExpressionVO;
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
public class SystemBeanSupportAction extends BaseController {
	
	private ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService;
	private ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> sysBeanHelpExprMapService;
	private ISystemBeanHelpLogicService systemBeanHelpLogicService;
	
	public ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> getSysBeanHelpService() {
		return sysBeanHelpService;
	}
	
	@Autowired
	@Resource(name="core.service.SysBeanHelpService")
	@Required
	public void setSysBeanHelpService(ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService) {
		this.sysBeanHelpService = sysBeanHelpService;
	}
	
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}
	
	@Autowired
	@Resource(name="core.service.SysService")
	@Required
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}
	
	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}

	@Autowired
	@Resource(name="core.service.SysExpressionService")
	@Required	
	public void setSysExpressionService(ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}

	public ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> getSysBeanHelpExprService() {
		return sysBeanHelpExprService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpExprService")
	@Required	
	public void setSysBeanHelpExprService(ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService) {
		this.sysBeanHelpExprService = sysBeanHelpExprService;
	}

	public ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> getSysBeanHelpExprMapService() {
		return sysBeanHelpExprMapService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpExprMapService")
	@Required	
	public void setSysBeanHelpExprMapService(ISysBeanHelpExprMapService<SysBeanHelpExprMapVO, TbSysBeanHelpExprMap, String> sysBeanHelpExprMapService) {
		this.sysBeanHelpExprMapService = sysBeanHelpExprMapService;
	}

	public ISystemBeanHelpLogicService getSystemBeanHelpLogicService() {
		return systemBeanHelpLogicService;
	}
	
	@Autowired
	@Resource(name="core.service.logic.SystemBeanHelpLogicService")
	@Required
	public void setSystemBeanHelpLogicService(ISystemBeanHelpLogicService systemBeanHelpLogicService) {
		this.systemBeanHelpLogicService = systemBeanHelpLogicService;
	}
	
	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		if ("queryPage".equals(type) || "createPage".equals(type) || "editPage".equals(type)) {
			mv.addObject( "sysMap", sysService.findSysMap(this.getBasePath(request), true) );
		}
		if ("editExpressionPage".equals(type)) {
			mv.addObject( "runTypeMap", ScriptExpressionRunType.getRunTypeMap(true) );
			mv.addObject( "expressionMap", sysExpressionService.findExpressionMap(true) );
		}
	}
	
	private void fetchData(SysBeanHelpVO sysBeanHelp, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysBeanHelpVO> result = this.sysBeanHelpService.findObjectByOid(sysBeanHelp);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysBeanHelp = result.getValue();
		mv.addObject("sysBeanHelp", sysBeanHelp);
		
		TbSys sys = new TbSys();
		sys.setSysId( sysBeanHelp.getSystem() );
		DefaultResult<TbSys> sysResult = this.sysService.findEntityByUK(sys);
		if (sysResult.getValue() == null) {
			throw new ControllerException( sysResult.getSystemMessage().getValue() );
		}
		sys = sysResult.getValue();
		mv.addObject("systemOid", sys.getOid());
	}
	
	private void fetchExprData(SysBeanHelpExprVO sysBeanHelpExpr, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysBeanHelpExprVO> result = this.sysBeanHelpExprService.findObjectByOid(sysBeanHelpExpr);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysBeanHelpExpr = result.getValue();
		mv.addObject("sysBeanHelpExpr", sysBeanHelpExpr);
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003Q")
	@RequestMapping(value = "/core.sysBeanSupportManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0003Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-beansupport/sys-beansupport-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003Q")
	@RequestMapping(value = "/core.sysBeanSupportQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysBeanHelpVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysBeanHelpVO> > result = this.getQueryJsonResult("CORE_PROG003D0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysBeanHelpVO> > queryResult = this.sysBeanHelpService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003A")
	@RequestMapping(value = "/core.sysBeanSupportCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0003A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-beansupport/sys-beansupport-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003E")
	@RequestMapping(value = "/core.sysBeanSupportEdit.do")
	public ModelAndView editPage(HttpServletRequest request, SysBeanHelpVO sysBeanHelp) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0003E");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysBeanHelp, mv);
			viewName = "sys-beansupport/sys-beansupport-edit";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S01Q")
	@RequestMapping(value = "/core.sysBeanSupportExpression.do")
	public ModelAndView editExpressionPage(HttpServletRequest request, SysBeanHelpVO sysBeanHelp) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0003S01Q");
		try {
			this.init("editExpressionPage", request, mv);
			this.fetchData(sysBeanHelp, mv);
			viewName = "sys-beansupport/sys-beansupport-expr";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S01Q")
	@RequestMapping(value = "/core.sysBeanSupportExpressionQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysBeanHelpExprVO> > queryExpressionGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysBeanHelpExprVO> > result = this.getQueryJsonResult("CORE_PROG003D0003S01Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysBeanHelpExprVO> > queryResult = this.sysBeanHelpExprService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S02Q")
	@RequestMapping(value = "/core.sysBeanSupportExpressionParam.do")
	public ModelAndView editExpressionParamPage(HttpServletRequest request, SysBeanHelpExprVO sysBeanHelpExpr) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0003S02Q");
		try {
			this.init("editExpressionParamPage", request, mv);
			this.fetchExprData(sysBeanHelpExpr, mv);
			viewName = "sys-beansupport/sys-beansupport-expr-map";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S02Q")
	@RequestMapping(value = "/core.sysBeanSupportExpressionParamQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysBeanHelpExprMapVO> > queryExpressionParamGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysBeanHelpExprMapVO> > result = this.getQueryJsonResult("CORE_PROG003D0003S01Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysBeanHelpExprMapVO> > queryResult = this.sysBeanHelpExprMapService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	private void checkFields(DefaultControllerJsonResultObj<SysBeanHelpVO> result, SysBeanHelpVO sysBeanHelp, String systemOid) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("systemOid", ( this.noSelect(systemOid) ), "Please select system!")
		.testField("beanId", sysBeanHelp, "@org.apache.commons.lang3.StringUtils@isBlank( beanId )", "Bean Id is blank!")
		.testField("beanId", sysBeanHelp, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( beanId.replaceAll(\"[.]\", \"\") )", "Bean Id error!")
		.testField("method", sysBeanHelp, "@org.apache.commons.lang3.StringUtils@isBlank( method )", "Method is blank!")
		.testField("method", sysBeanHelp, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( method.replaceAll(\"_\", \"\") )", "Method name error!")
		.throwMessage();
	}
	
	private void checkFieldsForExpression(DefaultControllerJsonResultObj<SysBeanHelpExprVO> result, SysBeanHelpExprVO sysBeanHelpExpr, String sysBeanHelpOid, String expressionOid) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("expressionOid", ( this.noSelect(expressionOid) ), "Please select expression!")
		.testField("exprSeq", sysBeanHelpExpr, "@org.apache.commons.lang3.StringUtils@isBlank( exprSeq )", "Seq is blank!")
		.testField("exprSeq", sysBeanHelpExpr, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( exprSeq )", "Seq only normal character!")
		.testField("runType", ( this.noSelect(sysBeanHelpExpr.getRunType()) ), "Please select process type!")
		.throwMessage();		
	}
	
	private void checkFieldsForExpressionMap(DefaultControllerJsonResultObj<SysBeanHelpExprMapVO> result, SysBeanHelpExprMapVO sysBeanHelpExprMap, String sysBeanHelpExprOid) throws ControllerException, Exception {
		CheckControllerFieldHandler< SysBeanHelpExprMapVO > checkHandler = this.getCheckControllerFieldHandler(result)
		.testField("varName", sysBeanHelpExprMap, "@org.apache.commons.lang3.StringUtils@isBlank( varName )", "Variable name is blank!")
		.testField("varName", sysBeanHelpExprMap, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( varName )", "Variable name only normal character!");
		if (!YES.equals(sysBeanHelpExprMap.getMethodResultFlag())) {
			checkHandler
			.testField("methodParamClass", sysBeanHelpExprMap, "@org.apache.commons.lang3.StringUtils@isBlank( methodParamClass )", "Method class name is blank!")
			.testField("methodParamClass", sysBeanHelpExprMap, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( methodParamClass.replaceAll(\"[.]\", \"\") )", "Method class name error!")
			.testField("methodParamIndex", sysBeanHelpExprMap, "methodParamIndex < 0", "Method parameter index error!");
		}
		checkHandler.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<SysBeanHelpVO> result, SysBeanHelpVO sysBeanHelp, String systemOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysBeanHelp, systemOid);
		DefaultResult<SysBeanHelpVO> cResult = this.systemBeanHelpLogicService.create(sysBeanHelp, systemOid);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );		
	}	
	
	private void update(DefaultControllerJsonResultObj<SysBeanHelpVO> result, SysBeanHelpVO sysBeanHelp, String systemOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysBeanHelp, systemOid);
		DefaultResult<SysBeanHelpVO> uResult = this.systemBeanHelpLogicService.update(sysBeanHelp, systemOid);
		if ( uResult.getValue() != null ) {
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );		
	}	
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysBeanHelpVO sysBeanHelp) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemBeanHelpLogicService.delete(sysBeanHelp);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );		
	}	 
	
	private void saveExpression(DefaultControllerJsonResultObj<SysBeanHelpExprVO> result, SysBeanHelpExprVO sysBeanHelpExpr, String sysBeanHelpOid, String expressionOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFieldsForExpression(result, sysBeanHelpExpr, sysBeanHelpOid, expressionOid);
		DefaultResult<SysBeanHelpExprVO> cResult = this.systemBeanHelpLogicService.createExpr(sysBeanHelpExpr, sysBeanHelpOid, expressionOid);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );			
	}
	
	private void deleteExpression(DefaultControllerJsonResultObj<Boolean> result, SysBeanHelpExprVO sysBeanHelpExpr) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemBeanHelpLogicService.deleteExpr(sysBeanHelpExpr);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );			
	}
	
	private void saveExpressionMap(DefaultControllerJsonResultObj<SysBeanHelpExprMapVO> result, SysBeanHelpExprMapVO sysBeanHelpExprMap, String sysBeanHelpExprOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFieldsForExpressionMap(result, sysBeanHelpExprMap, sysBeanHelpExprOid);
		DefaultResult<SysBeanHelpExprMapVO> cResult = this.systemBeanHelpLogicService.createExprMap(sysBeanHelpExprMap, sysBeanHelpExprOid);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );			
	}	
	
	private void deleteExpressionMap(DefaultControllerJsonResultObj<Boolean> result, SysBeanHelpExprMapVO sysBeanHelpExprMap) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemBeanHelpLogicService.deleteExprMap(sysBeanHelpExprMap);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );			
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003A")
	@RequestMapping(value = "/core.sysBeanSupportSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBeanHelpVO> doSave(SysBeanHelpVO sysBeanHelp, @RequestParam("systemOid") String systemOid) {
		DefaultControllerJsonResultObj<SysBeanHelpVO> result = this.getDefaultJsonResult("CORE_PROG003D0003A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysBeanHelp, systemOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003E")
	@RequestMapping(value = "/core.sysBeanSupportUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBeanHelpVO> doUpdate(SysBeanHelpVO sysBeanHelp, @RequestParam("systemOid") String systemOid) {
		DefaultControllerJsonResultObj<SysBeanHelpVO> result = this.getDefaultJsonResult("CORE_PROG003D0003E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sysBeanHelp, systemOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003D")
	@RequestMapping(value = "/core.sysBeanSupportDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysBeanHelpVO sysBeanHelp) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0003D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysBeanHelp);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S01A")
	@RequestMapping(value = "/core.sysBeanSupportExpressionSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBeanHelpExprVO> doSaveExpression(SysBeanHelpExprVO sysBeanHelpExpr, @RequestParam("sysBeanHelpOid") String sysBeanHelpOid, @RequestParam("expressionOid") String expressionOid) {
		DefaultControllerJsonResultObj<SysBeanHelpExprVO> result = this.getDefaultJsonResult("CORE_PROG003D0003S01A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.saveExpression(result, sysBeanHelpExpr, sysBeanHelpOid, expressionOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S01D")
	@RequestMapping(value = "/core.sysBeanSupportExpressionDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDeleteExpression(SysBeanHelpExprVO sysBeanHelpExpr) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0003S01D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.deleteExpression(result, sysBeanHelpExpr);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S02A")
	@RequestMapping(value = "/core.sysBeanSupportExpressionParamSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysBeanHelpExprMapVO> doSaveExpressionMap(SysBeanHelpExprMapVO sysBeanHelpExprMap, @RequestParam("sysBeanHelpExprOid") String sysBeanHelpExprOid) {
		DefaultControllerJsonResultObj<SysBeanHelpExprMapVO> result = this.getDefaultJsonResult("CORE_PROG003D0003S02A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.saveExpressionMap(result, sysBeanHelpExprMap, sysBeanHelpExprOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0003S02D")
	@RequestMapping(value = "/core.sysBeanSupportExpressionParamDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDeleteExpressionMap(SysBeanHelpExprMapVO sysBeanHelpExprMap) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0003S02D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.deleteExpressionMap(result, sysBeanHelpExprMap);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}
	
}
