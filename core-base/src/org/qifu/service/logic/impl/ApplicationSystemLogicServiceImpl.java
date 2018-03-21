/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
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
package org.qifu.service.logic.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.ServiceAuthority;
import org.qifu.base.model.ServiceMethodAuthority;
import org.qifu.base.model.ServiceMethodType;
import org.qifu.base.service.logic.BaseLogicService;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysIcon;
import org.qifu.po.TbSysProg;
import org.qifu.service.ISysIconService;
import org.qifu.service.ISysProgService;
import org.qifu.service.ISysService;
import org.qifu.service.logic.IApplicationSystemLogicService;
import org.qifu.vo.SysIconVO;
import org.qifu.vo.SysProgVO;
import org.qifu.vo.SysVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ServiceAuthority(check=true)
@Service("core.service.logic.ApplicationSystemLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ApplicationSystemLogicServiceImpl extends BaseLogicService implements IApplicationSystemLogicService {
	protected Logger logger=Logger.getLogger(ApplicationSystemLogicServiceImpl.class);
	private ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	
	public ApplicationSystemLogicServiceImpl() {
		super();
	}

	public ISysIconService<SysIconVO, TbSysIcon, String> getSysIconService() {
		return sysIconService;
	}

	@Autowired
	@Resource(name="core.service.SysIconService")
	@Required			
	public void setSysIconService(ISysIconService<SysIconVO, TbSysIcon, String> sysIconService) {
		this.sysIconService = sysIconService;
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

	public ISysProgService<SysProgVO, TbSysProg, String> getSysProgService() {
		return sysProgService;
	}

	@Autowired
	@Resource(name="core.service.SysProgService")
	@Required		
	public void setSysProgService(ISysProgService<SysProgVO, TbSysProg, String> sysProgService) {
		this.sysProgService = sysProgService;
	}
	
	/**
	 * 建立 TB_SYS 資料
	 * 
	 * @param sys
	 * @param iconOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysVO> create(SysVO sys, String iconOid) throws ServiceException, Exception {
		
		SysIconVO sysIcon = new SysIconVO();
		sysIcon.setOid(iconOid);
		DefaultResult<SysIconVO> iconResult = this.sysIconService.findObjectByOid(sysIcon);
		if (iconResult.getValue()==null) {
			throw new ServiceException(iconResult.getSystemMessage().getValue());
		}		
		sys.setIcon(iconResult.getValue().getIconId());
		return this.sysService.saveObject(sys);
	}

	/**
	 * 刪除 TB_SYS 資料
	 * 
	 * @param sys
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysVO sys) throws ServiceException, Exception {
		
		if (sys==null || StringUtils.isBlank(sys.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysVO> sysResult = this.sysService.findObjectByOid(sys);
		if (sysResult.getValue()==null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("progSystem", sys.getSysId());
		if (this.sysProgService.countByParams(params)>0) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_CANNOT_DELETE));
		}		
		return this.sysService.deleteObject(sys);		
	}

	/**
	 * 更新 TB_SYS 資料
	 * 
	 * @param sys
	 * @param iconOid
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysVO> update(SysVO sys, String iconOid) throws ServiceException, Exception {
		
		if (null == sys || StringUtils.isBlank(sys.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		SysIconVO sysIcon = new SysIconVO();
		sysIcon.setOid(iconOid);
		DefaultResult<SysIconVO> iconResult = this.sysIconService.findObjectByOid(sysIcon);
		if (iconResult.getValue()==null) {
			throw new ServiceException(iconResult.getSystemMessage().getValue());
		}		
		sys.setIcon(iconResult.getValue().getIconId());
		return this.sysService.updateObject(sys);
	}
	
}
