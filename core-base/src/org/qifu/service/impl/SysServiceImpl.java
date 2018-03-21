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

import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.base.service.BaseService;
import org.qifu.dao.ISysDAO;
import org.qifu.po.TbSys;
import org.qifu.service.ISysService;
import org.qifu.vo.SysVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("core.service.SysService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysServiceImpl extends BaseService<SysVO, TbSys, String> implements ISysService<SysVO, TbSys, String> {
	protected Logger logger=Logger.getLogger(SysServiceImpl.class);
	private ISysDAO<TbSys, String> sysDAO;
	
	public SysServiceImpl() {
		super();
	}

	public ISysDAO<TbSys, String> getSysDAO() {
		return sysDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysDAO")
	@Required		
	public void setSysDAO(ISysDAO<TbSys, String> sysDAO) {
		this.sysDAO = sysDAO;
	}

	@Override
	protected IBaseDAO<TbSys, String> getBaseDataAccessObject() {
		return sysDAO;
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
		return this.getQueryParamHandler(searchValue)
				.containingLike("name")
				.fullEquals4TextField("sysId")
				.getValue();
	}	
	
	@Override
	public QueryResult<List<SysVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;		
		QueryResult<List<SysVO>> result=this.sysDAO.findPageQueryResultByQueryName(
				"findSysPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}
	
	@Override
	public Map<String, String> findSysMap(String basePath, boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> sysMap = super.providedSelectZeroDataMap(pleaseSelect);
		List<TbSys> searchList = this.findListByParams(null);
		if (searchList==null || searchList.size()<1) {
			return sysMap;
		}
		for (TbSys sys : searchList) {
			sysMap.put(sys.getOid(), sys.getSysId() + " - " + sys.getName());
		}
		return sysMap;
	}

}
