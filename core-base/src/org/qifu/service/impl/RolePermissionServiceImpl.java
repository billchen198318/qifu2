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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.base.service.BaseService;
import org.qifu.dao.IRolePermissionDAO;
import org.qifu.po.TbRolePermission;
import org.qifu.service.IRolePermissionService;
import org.qifu.vo.RolePermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("core.service.RolePermissionService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class RolePermissionServiceImpl extends BaseService<RolePermissionVO, TbRolePermission, String> implements IRolePermissionService<RolePermissionVO, TbRolePermission, String> {
	protected Logger logger=Logger.getLogger(RolePermissionServiceImpl.class);
	private IRolePermissionDAO<TbRolePermission, String> rolePermissionDAO;
	
	public RolePermissionServiceImpl() {
		super();
	}

	public IRolePermissionDAO<TbRolePermission, String> getRolePermissionDAO() {
		return rolePermissionDAO;
	}

	@Autowired
	@Resource(name="core.dao.RolePermissionDAO")
	@Required		
	public void setRolePermissionDAO(IRolePermissionDAO<TbRolePermission, String> rolePermissionDAO) {
		this.rolePermissionDAO = rolePermissionDAO;
	}

	@Override
	protected IBaseDAO<TbRolePermission, String> getBaseDataAccessObject() {
		return rolePermissionDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}
	
	private Map<String, Object> getQueryParameters(SearchValue searchValue) {
		Map<String, Object> params = new HashMap<String, Object>();
		String role = searchValue.getParameter().get("role");
		if (!StringUtils.isBlank(role)) {
			params.put("role", role);
		}
		return params;		
	}

	@Override
	public QueryResult<List<RolePermissionVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params = this.getQueryParameters(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<RolePermissionVO>> result=this.rolePermissionDAO.findPageQueryResultByQueryName(
				"findRolePermissionPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

}
