/* 
 * Copyright 2012-2018 qifu of copyright Chen Xin Nien
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
package org.qifu.base.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.qifu.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class BaseMapperDAO<T extends java.io.Serializable> implements IBaseMapperDAO<T> {
	protected Logger logger=Logger.getLogger(BaseMapperDAO.class);
	
	private SqlSession sqlSession;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	
	private static final String NAMESPACE_SYMBOL = ".";
	
	private static String MAPPER_DEFINE_ID_SELECT_BY_PARAMS = NAMESPACE_SYMBOL + "selectByParams";
	private static String MAPPER_DEFINE_ID_SELECT_BY_VALUE = NAMESPACE_SYMBOL + "selectByValue";
	private static String MAPPER_DEFINE_ID_INSERT = NAMESPACE_SYMBOL + "insert";
	private static String MAPPER_DEFINE_ID_UPDATE = NAMESPACE_SYMBOL + "update";
	private static String MAPPER_DEFINE_ID_DELETE = NAMESPACE_SYMBOL + "delete";
	private static String MAPPER_DEFINE_ID_COUNT = NAMESPACE_SYMBOL + "count";
	private static String MAPPER_DEFINE_ID_COUNT_BY_UK = NAMESPACE_SYMBOL + "countByUK";
	
	public BaseMapperDAO() {
		super();
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	
	@Autowired
	@Required
	@Resource(name="sqlSession")
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	@Required
	@Resource(name="jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	@Autowired
	@Required
	@Resource(name="namedParameterJdbcTemplate")	
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public Connection getConnection() throws Exception {
		return DataUtils.getConnection();
	}
	
	public abstract String getNameSpace();
	
	public String getNameSpaceSymbol() {
		return NAMESPACE_SYMBOL;
	}
	
	public String getNameSpacePlus() {
		return this.getNameSpace() + this.getNameSpaceSymbol();
	}	
	
	@Override
	public List<T> selectListByParams(Map<String, Object> paramMap) throws Exception {
		return this.getSqlSession().selectList(this.getNameSpace()+MAPPER_DEFINE_ID_SELECT_BY_PARAMS, paramMap);
	}

	@Override
	public T selectOneByValue(T mapperObj) throws Exception {
		return this.getSqlSession().selectOne(this.getNameSpace()+MAPPER_DEFINE_ID_SELECT_BY_VALUE, mapperObj);
	}	
	
	@Override
	public int insert(T mapperObj) throws Exception {
		return this.getSqlSession().insert(this.getNameSpace() + MAPPER_DEFINE_ID_INSERT, mapperObj);
	}
	
	@Override
	public int update(T mapperObj) throws Exception {
		return this.getSqlSession().update(this.getNameSpace() + MAPPER_DEFINE_ID_UPDATE, mapperObj);
	}
	
	@Override
	public int delete(T mapperObj) throws Exception {
		return this.getSqlSession().delete(this.getNameSpace() + MAPPER_DEFINE_ID_DELETE, mapperObj);
	}
	
	@Override
	public int count(Map<String, Object> paramMap) throws Exception {
		return this.getSqlSession().selectOne(this.getNameSpace() + MAPPER_DEFINE_ID_COUNT, paramMap);
	}
	
	@Override
	public int countByUK(Map<String, Object> paramMap) throws Exception {
		return this.getSqlSession().selectOne(this.getNameSpace() + MAPPER_DEFINE_ID_COUNT_BY_UK, paramMap);
	}
	
}
