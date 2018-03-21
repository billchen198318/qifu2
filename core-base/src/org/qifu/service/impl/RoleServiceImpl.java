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
package org.qifu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.base.service.BaseService;
import org.qifu.dao.IRoleDAO;
import org.qifu.po.TbRole;
import org.qifu.service.IRoleService;
import org.qifu.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("core.service.RoleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class RoleServiceImpl extends BaseService<RoleVO, TbRole, String> implements IRoleService<RoleVO, TbRole, String> {
	protected Logger logger=Logger.getLogger(RoleServiceImpl.class);
	private IRoleDAO<TbRole, String> roleDAO;
	
	public RoleServiceImpl() {
		super();
	}

	public IRoleDAO<TbRole, String> getRoleDAO() {
		return roleDAO;
	}

	@Autowired
	@Resource(name="core.dao.RoleDAO")
	@Required		
	public void setRoleDAO(IRoleDAO<TbRole, String> roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	protected IBaseDAO<TbRole, String> getBaseDataAccessObject() {
		return roleDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue).fullEquals4TextField("role").getValue();
	}

	/**
	 * SELECT OID, ROLE, DESCRIPTION from TB_ROLE
	 * WHERE ROLE = :role
	 * 
	 * @param searchValue
	 * @param pageOf
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public QueryResult<List<RoleVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<RoleVO>> result=this.roleDAO.findPageQueryResultByQueryName("findRolePageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	/**
	 * 查帳戶下有的 role
	 * 
	 * @param account
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public List<RoleVO> findForAccount(String account) throws ServiceException, Exception {
		if (StringUtils.isBlank(account)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		return this.roleDAO.findForAccount(account);
	}

	/**
	 * select OID, ROLE, DESCRIPTION from tb_role
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public List<RoleVO> findForAll() throws ServiceException, Exception {		
		return this.roleDAO.findForAll();
	}

	/**
	 * 查某隻程式屬於的role
	 * 
	 * select OID, ROLE, DESCRIPTION from tb_role where ROLE in (
	 * 		select ROLE from tb_sys_menu_role WHERE PROG_ID = :progId 
	 * )
	 * 
	 * @param progId
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public List<RoleVO> findForProgram(String progId) throws ServiceException, Exception {
		if (StringUtils.isBlank(progId)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		return this.roleDAO.findForProgram(progId);
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect, boolean normal) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<TbRole> roles = this.findListByParams(null);
		for (int i=0; roles!=null && i<roles.size(); i++) {
			TbRole role = roles.get( i );
			if (normal && (Constants.SUPER_ROLE_ADMIN.equals(role.getRole()) || Constants.SUPER_ROLE_ALL.equals(role.getRole())) ) {
				continue;
			}
			dataMap.put(role.getOid(), role.getRole());
		}
		return dataMap;
	}

}
