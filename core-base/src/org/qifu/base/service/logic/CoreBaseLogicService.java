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
package org.qifu.base.service.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.TbAccount;
import org.qifu.po.TbRole;
import org.qifu.po.TbSysUpload;
import org.qifu.po.TbUserRole;
import org.qifu.service.IAccountService;
import org.qifu.service.IRoleService;
import org.qifu.service.ISysUploadService;
import org.qifu.service.IUserRoleService;
import org.qifu.util.UploadSupportUtils;
import org.qifu.vo.AccountVO;
import org.qifu.vo.RoleVO;
import org.qifu.vo.SysUploadVO;
import org.qifu.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public abstract class CoreBaseLogicService extends BaseLogicService {
	
	private IAccountService<AccountVO, TbAccount, String> accountService;
	private IRoleService<RoleVO, TbRole, String> roleService;
	private IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	
	public CoreBaseLogicService() {
		super();
	}
	
	public IAccountService<AccountVO, TbAccount, String> getAccountService() {
		return accountService;
	}

	@Autowired
	@Resource(name="core.service.AccountService")
	@Required		
	public void setAccountService(IAccountService<AccountVO, TbAccount, String> accountService) {
		this.accountService = accountService;
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

	public IUserRoleService<UserRoleVO, TbUserRole, String> getUserRoleService() {
		return userRoleService;
	}

	@Autowired
	@Resource(name="core.service.UserRoleService")
	@Required		
	public void setUserRoleService(
			IUserRoleService<UserRoleVO, TbUserRole, String> userRoleService) {
		this.userRoleService = userRoleService;
	}	
	
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")
	@Required					
	public void setSysUploadService(ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}		
	
	public AccountVO findAccountData() throws ServiceException, Exception {
		return this.findAccountData( super.getAccountId() );
	}
	
	public AccountVO findAccountData(String accountId) throws ServiceException, Exception {
		if (super.isBlank(accountId)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		AccountVO accountObj = new AccountVO();
		accountObj.setAccount(accountId);
		DefaultResult<AccountVO> result = this.accountService.findByUK(accountObj);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().toString() );
		}
		accountObj = result.getValue();
		return accountObj;
	}
	
	public List<TbUserRole> findUserRoles() throws ServiceException, Exception {
		return this.findUserRoles( super.getAccountId() );
	}
	
	public List<TbUserRole> findUserRoles(String accountId) throws ServiceException, Exception {
		if (super.isBlank(accountId)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("account", super.getAccountId());
		return this.userRoleService.findListByParams(paramMap);
	}
	
	public SysUploadVO findUploadData(String oid) throws ServiceException, Exception {
		if (super.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		return UploadSupportUtils.findUpload(oid);	
	}
	
	public SysUploadVO findUploadDataForNoByteContent(String oid) throws ServiceException, Exception {
		if (super.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		return UploadSupportUtils.findUploadNoByteContent(oid);
	}
	
}
