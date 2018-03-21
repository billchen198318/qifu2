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
package org.qifu.service;

import java.util.List;
import java.util.Map;

import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.base.service.IBaseService;
import org.qifu.vo.RoleVO;

public interface IRoleService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseService<T, E, PK> {
	
	public static String MAPPER_ID_PO2VO="role.po2vo";
	public static String MAPPER_ID_VO2PO="role.vo2po";
	
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
	public QueryResult<List<RoleVO>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception;
	
	/**
	 * 查帳戶下有的 role
	 * 
	 * @param account
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public List<RoleVO> findForAccount(String account) throws ServiceException, Exception;
	
	/**
	 * select OID, ROLE, DESCRIPTION from tb_role
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public List<RoleVO> findForAll() throws ServiceException, Exception;
	
	
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
	public List<RoleVO> findForProgram(String progId) throws ServiceException, Exception;
	
	public Map<String, String> findForMap(boolean pleaseSelect, boolean normal) throws ServiceException, Exception;
	
}
