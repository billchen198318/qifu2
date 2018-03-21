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
import org.qifu.dao.ISysBpmnResourceDAO;
import org.qifu.po.TbSysBpmnResource;
import org.qifu.service.ISysBpmnResourceService;
import org.qifu.vo.SysBpmnResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("core.service.SysBpmnResourceService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysBpmnResourceServiceImpl extends BaseService<SysBpmnResourceVO, TbSysBpmnResource, String> implements ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> {
	protected Logger logger=Logger.getLogger(SysBpmnResourceServiceImpl.class);
	private ISysBpmnResourceDAO<TbSysBpmnResource, String> sysBpmnResourceDAO;
	
	public SysBpmnResourceServiceImpl() {
		super();
	}

	public ISysBpmnResourceDAO<TbSysBpmnResource, String> getSysBpmnResourceDAO() {
		return sysBpmnResourceDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysBpmnResourceDAO")
	@Required		
	public void setSysBpmnResourceDAO(ISysBpmnResourceDAO<TbSysBpmnResource, String> sysBpmnResourceDAO) {
		this.sysBpmnResourceDAO = sysBpmnResourceDAO;
	}

	@Override
	protected IBaseDAO<TbSysBpmnResource, String> getBaseDataAccessObject() {
		return sysBpmnResourceDAO;
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
		return super.getQueryParamHandler(searchValue)
				.fullEquals4TextField("id")
				.containingLike("name")
				.getValue();
	}

	@Override
	public QueryResult<List<SysBpmnResourceVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysBpmnResourceVO>> result=this.sysBpmnResourceDAO.findPageQueryResultByQueryName(
				"findSysBpmnResourcePageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	@Override
	public Map<String, String> findForMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<SysBpmnResourceVO> searchList = this.sysBpmnResourceDAO.findForSimple();
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			dataMap.put(searchList.get(i).getOid(), searchList.get(i).getName());
		}
		return dataMap;
	}

}
