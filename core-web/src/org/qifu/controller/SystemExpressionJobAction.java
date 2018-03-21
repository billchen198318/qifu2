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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
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
import org.qifu.model.ExpressionJobConstants;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysExprJob;
import org.qifu.po.TbSysExpression;
import org.qifu.service.ISysExprJobService;
import org.qifu.service.ISysExpressionService;
import org.qifu.service.ISysService;
import org.qifu.service.logic.ISystemExpressionLogicService;
import org.qifu.util.SystemExpressionJobUtils;
import org.qifu.vo.SysExprJobLogVO;
import org.qifu.vo.SysExprJobVO;
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
public class SystemExpressionJobAction extends BaseController {
	
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService; 
	private ISystemExpressionLogicService systemExpressionLogicService;
		
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

	public ISysExprJobService<SysExprJobVO, TbSysExprJob, String> getSysExprJobService() {
		return sysExprJobService;
	}

	@Autowired
	@Resource(name="core.service.SysExprJobService")
	@Required
	public void setSysExprJobService(ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService) {
		this.sysExprJobService = sysExprJobService;
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
		if ("createPage".equals(type) || "editPage".equals(type)) {
			mv.addObject("expressionMap", this.sysExpressionService.findExpressionMap(true));
			mv.addObject("sysMap", this.sysService.findSysMap(this.getBasePath(request), true));
			
			Map<String, String> runDayOfWeekMap = new LinkedHashMap<String, String>();
			Map<String, String> runHourMap = new LinkedHashMap<String, String>();
			Map<String, String> runMinuteMap = new LinkedHashMap<String, String>();
			
			runDayOfWeekMap.put(ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL, ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL);
			for (int day=1; day<=7; day++) {
				runDayOfWeekMap.put(String.valueOf(day), String.valueOf(day));
			}
			
			runHourMap.put(ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL, ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL);
			for (int hour=0; hour<=23; hour++) {
				runHourMap.put(String.valueOf(hour), String.valueOf(hour));
			}
			
			runMinuteMap.put(ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL, ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL);
			for (int minute=0; minute<=59; minute++) {
				runMinuteMap.put(String.valueOf(minute), String.valueOf(minute));
			}
			
			mv.addObject("runDayOfWeekMap", runDayOfWeekMap);
			mv.addObject("runHourMap", runHourMap);
			mv.addObject("runMinuteMap", runMinuteMap);
		}
	}
	
	private void fetchData(SysExprJobVO sysExprJob, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<SysExprJobVO> result = this.sysExprJobService.findObjectByOid(sysExprJob);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		sysExprJob = result.getValue();
		mv.addObject("sysExprJob", sysExprJob);
		
		TbSys sys = new TbSys();
		sys.setSysId(sysExprJob.getSystem());
		DefaultResult<TbSys> sysResult = this.sysService.findEntityByUK(sys);
		if (sysResult.getValue() == null) {
			throw new ControllerException( sysResult.getSystemMessage().getValue() );
		}
		sys = sysResult.getValue();
		mv.addObject("systemOid", sys.getOid());
		
		TbSysExpression expression = new TbSysExpression();
		expression.setExprId( sysExprJob.getExprId() );
		DefaultResult<TbSysExpression> exprResult = this.sysExpressionService.findEntityByUK(expression);
		if (exprResult.getValue() == null) {
			throw new ControllerException( exprResult.getSystemMessage().getValue() );
		}
		expression = exprResult.getValue();
		mv.addObject("expressionOid", expression.getOid());
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006Q")
	@RequestMapping(value = "/core.sysExpressionJobManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0006Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "sys-exprjob/sys-exprjob-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006Q")
	@RequestMapping(value = "/core.sysExpressionJobQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj< List<SysExprJobVO> > queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj< List<SysExprJobVO> > result = this.getQueryJsonResult("CORE_PROG003D0006Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult< List<SysExprJobVO> > queryResult = this.sysExprJobService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006A")
	@RequestMapping(value = "/core.sysExpressionJobCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0006A");
		try {
			this.init("createPage", request, mv);
			viewName = "sys-exprjob/sys-exprjob-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006E")
	@RequestMapping(value = "/core.sysExpressionJobEdit.do")
	public ModelAndView editPage(HttpServletRequest request, SysExprJobVO sysExprJob) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("CORE_PROG003D0006E");
		try {
			this.init("editPage", request, mv);
			this.fetchData(sysExprJob, mv);
			viewName = "sys-exprjob/sys-exprjob-edit";
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
	
	private void checkFields(DefaultControllerJsonResultObj<SysExprJobVO> result, SysExprJobVO sysExprJob, String systemOid, String expressionOid) throws ControllerException, Exception {
		CheckControllerFieldHandler<SysExprJobVO> checkHandler = this.getCheckControllerFieldHandler(result)
		.testField("systemOid", this.noSelect(systemOid), "Please select system!")
		.testField("id", sysExprJob, "@org.apache.commons.lang3.StringUtils@isBlank( id )", "Id is required!")
		.testField("id", sysExprJob, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09( id.replaceAll(\"-\", \"\").replaceAll(\"_\", \"\") )", "Id only normal character!")
		.testField("name", sysExprJob, "@org.apache.commons.lang3.StringUtils@isBlank( name )", "Name is required!")
		.testField("systemOid", this.noSelect(expressionOid), "Please select expression!")
		.testField("contact", ( !ExpressionJobConstants.CONTACT_MODE_NO.equals( sysExprJob.getContactMode() ) && StringUtils.isBlank(sysExprJob.getContact()) ), "Contact is required!");
		if (!ExpressionJobConstants.CONTACT_MODE_NO.equals( sysExprJob.getContactMode() )) {
			checkHandler.testField("contact", sysExprJob, " @org.apache.commons.lang3.StringUtils@defaultString( contact ).indexOf(\"@\") < 1 || @org.apache.commons.lang3.StringUtils@defaultString( contact ).length < 3 ", "Contact value is not email address!");
		}
		checkHandler.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<SysExprJobVO> result, SysExprJobVO sysExprJob, String systemOid, String expressionOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysExprJob, systemOid, expressionOid);
		DefaultResult<SysExprJobVO> cResult = this.systemExpressionLogicService.createJob(sysExprJob, systemOid, expressionOid);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( cResult.getSystemMessage().getValue() );		
	}	
	
	private void update(DefaultControllerJsonResultObj<SysExprJobVO> result, SysExprJobVO sysExprJob, String systemOid, String expressionOid) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, sysExprJob, systemOid, expressionOid);
		DefaultResult<SysExprJobVO> uResult = this.systemExpressionLogicService.updateJob(sysExprJob, systemOid, expressionOid);
		if ( uResult.getValue() != null ) {
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );		
	}	
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, SysExprJobVO sysExprJob) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.systemExpressionLogicService.deleteJob(sysExprJob);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );		
	}
	
	private void manualExecute(DefaultControllerJsonResultObj<SysExprJobVO> result, HttpServletRequest request, SysExprJobVO sysExprJob) throws AuthorityException, ControllerException, ServiceException, Exception {
		String accountId = super.getAccountId();
		DefaultResult<SysExprJobVO> rResult = this.sysExprJobService.findObjectByOid(sysExprJob);
		if (rResult.getValue() == null) {
			throw new ControllerException( rResult.getSystemMessage().getValue() );
		}
		sysExprJob = rResult.getValue();
		if (Constants.getMainSystem().equals(sysExprJob.getSystem())) { // 是自己 CORE系統, 所以不用觸發遠端的服務
			SystemExpressionJobUtils.executeJobForManual(sysExprJob.getOid());
		} else {
			SysExprJobLogVO jobLog = SystemExpressionJobUtils.executeJobForManualWebClient(sysExprJob, accountId, request);
			if (null != jobLog && !StringUtils.isBlank(jobLog.getFaultMsg())) {
				throw new ControllerException( jobLog.getFaultMsg() );
			}
			if (null == jobLog) {
				throw new ControllerException( SysMessageUtil.get(SysMsgConstants.DATA_ERRORS) );
			}
		}	
		result.setValue( sysExprJob );
		result.setSuccess( YES );
		result.setMessage( SysMessageUtil.get(SysMsgConstants.UPDATE_SUCCESS) );
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006A")
	@RequestMapping(value = "/core.sysExpressionJobSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysExprJobVO> doSave(SysExprJobVO sysExprJob, @RequestParam("systemOid") String systemOid, @RequestParam("expressionOid") String expressionOid) {
		DefaultControllerJsonResultObj<SysExprJobVO> result = this.getDefaultJsonResult("CORE_PROG003D0006A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, sysExprJob, systemOid, expressionOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006E")
	@RequestMapping(value = "/core.sysExpressionJobUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysExprJobVO> doUpdate(SysExprJobVO sysExprJob, @RequestParam("systemOid") String systemOid, @RequestParam("expressionOid") String expressionOid) {
		DefaultControllerJsonResultObj<SysExprJobVO> result = this.getDefaultJsonResult("CORE_PROG003D0006E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, sysExprJob, systemOid, expressionOid);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006D")
	@RequestMapping(value = "/core.sysExpressionJobDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(SysExprJobVO sysExprJob) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("CORE_PROG003D0006D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, sysExprJob);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROG003D0006D")
	@RequestMapping(value = "/core.sysExpressionJobManualExecuteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<SysExprJobVO> doManualExecute(HttpServletRequest request, SysExprJobVO sysExprJob) {
		DefaultControllerJsonResultObj<SysExprJobVO> result = this.getDefaultJsonResult("CORE_PROG003D0006D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.manualExecute(result, request, sysExprJob);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
}
