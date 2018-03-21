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

import org.apache.commons.text.StringEscapeUtils;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.model.MenuItemType;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysProg;
import org.qifu.service.ISysProgService;
import org.qifu.service.ISysService;
import org.qifu.vo.SysProgVO;
import org.qifu.vo.SysVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class CommonAction extends BaseController {
	
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private ISysService<SysVO, TbSys, String> sysService;
	
	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	@Required	
	public void setSysProgService(ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
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
	
	private TbSys findSys(String oid) throws ServiceException, Exception {
		TbSys sys = new TbSys();
		sys.setOid(oid);
		DefaultResult<TbSys> sysResult = this.sysService.findEntityByOid(sys);
		if (sysResult.getValue() == null) {
			throw new ControllerException( sysResult.getSystemMessage().getValue() );
		}
		sys = sysResult.getValue();
		return sys;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROGCOMM0001Q")
	@RequestMapping(value = "/core.getCommonProgramFolderJson.do")	
	public @ResponseBody DefaultControllerJsonResultObj<Map<String, String>> doQueryProgramFolder(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		DefaultControllerJsonResultObj<Map<String, String>> result = this.getDefaultJsonResult("CORE_PROGCOMM0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			result.setValue( this.getPleaseSelectMap(true) );
			return result;
		}		
		try {
			TbSys sys = this.findSys(oid);
			result.setValue( this.sysProgService.findSysProgFolderMap(this.getBasePath(request), sys.getSysId(), MenuItemType.FOLDER, true) );
			result.setSuccess( YES );
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			exceptionResult(result, e);
		}
		return result;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROGCOMM0002Q")
	@RequestMapping(value = "/core.getCommonProgramFolderMenuItemJson.do")	
	public @ResponseBody DefaultControllerJsonResultObj<Map<String, String>> doQueryProgramList(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		DefaultControllerJsonResultObj<Map<String, String>> result = this.getDefaultJsonResult("CORE_PROGCOMM0002Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			result.setValue( this.getPleaseSelectMap(true) );
			return result;
		}		
		try {
			TbSys sys = this.findSys(oid);
			List<SysProgVO> menuProgList = this.sysProgService.findForInTheFolderMenuItems(sys.getSysId(), null, null);
			Map<String, String> dataMap = this.getPleaseSelectMap(true);
			for (int i=0; menuProgList!=null && i<menuProgList.size(); i++) {
				SysProgVO sysProg = menuProgList.get(i);
				dataMap.put(sysProg.getOid(), StringEscapeUtils.escapeHtml4(sysProg.getName()));
			}
			result.setValue( dataMap );
			result.setSuccess( YES );
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			exceptionResult(result, e);
		}
		return result;
	}
	
}
