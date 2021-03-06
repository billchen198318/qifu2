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
package org.qifu.dao.impl;

import java.util.List;

import org.qifu.base.dao.BaseDAO;
import org.qifu.dao.IAccountDAO;
import org.qifu.po.TbAccount;
import org.qifu.vo.AccountVO;
import org.springframework.stereotype.Repository;

@Repository("core.dao.AccountDAO")
public class AccountDAOImpl extends BaseDAO<TbAccount, String> implements IAccountDAO<TbAccount, String> {
	
	public AccountDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountVO> findForAll() throws Exception {
		return this.getCurrentSession().createQuery(
				"SELECT new org.qifu.vo.AccountVO(a.oid, a.account) FROM TbAccount a ORDER BY a.account ASC ")
				.list();
	}
	
}
