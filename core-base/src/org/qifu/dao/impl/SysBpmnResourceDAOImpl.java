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
package org.qifu.dao.impl;

import java.util.List;

import org.qifu.base.dao.BaseDAO;
import org.qifu.dao.ISysBpmnResourceDAO;
import org.qifu.po.TbSysBpmnResource;
import org.qifu.vo.SysBpmnResourceVO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("core.dao.SysBpmnResourceDAO")
@Scope("prototype")
public class SysBpmnResourceDAOImpl extends BaseDAO<TbSysBpmnResource, String> implements ISysBpmnResourceDAO<TbSysBpmnResource, String> {
	
	public SysBpmnResourceDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysBpmnResourceVO> findForSimple() throws Exception {
		return this.getCurrentSession()
				.createQuery("select new org.qifu.vo.SysBpmnResourceVO(m.oid, m.id, m.deploymentId, m.name, m.description) from TbSysBpmnResource m order by m.id asc")
				.list();
	}
	
}
