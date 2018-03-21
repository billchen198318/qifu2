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

import javax.annotation.Resource;

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
import org.qifu.po.TbSysWsConfig;
import org.qifu.service.ISysService;
import org.qifu.service.ISysWsConfigService;
import org.qifu.service.logic.ISystemWebServiceConfigLogicService;
import org.qifu.vo.SysVO;
import org.qifu.vo.SysWsConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ServiceAuthority(check=true)
@Service("core.service.logic.SystemWebServiceConfigLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SystemWebServiceConfigLogicServiceImpl extends BaseLogicService implements ISystemWebServiceConfigLogicService {
	protected Logger logger=Logger.getLogger(SystemWebServiceConfigLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService;
	
	public SystemWebServiceConfigLogicServiceImpl() {
		super();
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

	public ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> getSysWsConfigService() {
		return sysWsConfigService;
	}

	@Autowired
	@Resource(name="core.service.SysWsConfigService")	
	@Required		
	public void setSysWsConfigService(
			ISysWsConfigService<SysWsConfigVO, TbSysWsConfig, String> sysWsConfigService) {
		this.sysWsConfigService = sysWsConfigService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<SysWsConfigVO> create(SysWsConfigVO config, String systemOid) throws ServiceException, Exception {
		if (config==null || super.isBlank(systemOid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sResult = this.sysService.findObjectByOid(sys);
		if (sResult.getValue()==null) {
			throw new ServiceException(sResult.getSystemMessage().getValue());
		}
		sys = sResult.getValue();
		config.setSystem(sys.getSysId());
		if (super.defaultString(config.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			config.setDescription( config.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		return this.sysWsConfigService.saveObject(config);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<SysWsConfigVO> update(SysWsConfigVO config, String systemOid) throws ServiceException, Exception {
		if (config==null || super.isBlank(config.getOid()) || super.isBlank(systemOid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<SysWsConfigVO> oldResult = this.sysWsConfigService.findObjectByOid(config);
		if (oldResult.getValue()==null) {
			throw new ServiceException(oldResult.getSystemMessage().getValue());
		}		
		SysVO sys = new SysVO();
		sys.setOid(systemOid);
		DefaultResult<SysVO> sResult = this.sysService.findObjectByOid(sys);
		if (sResult.getValue()==null) {
			throw new ServiceException(sResult.getSystemMessage().getValue());
		}
		sys = sResult.getValue();
		config.setWsId( oldResult.getValue().getWsId() );
		config.setSystem(sys.getSysId());
		if (super.defaultString(config.getDescription()).length() > MAX_DESCRIPTION_LENGTH) {
			config.setDescription( config.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );
		}
		return this.sysWsConfigService.updateObject(config);
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(SysWsConfigVO config) throws ServiceException, Exception {
		if (config==null || super.isBlank(config.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		return this.sysWsConfigService.deleteObject(config);
	}
	
}
