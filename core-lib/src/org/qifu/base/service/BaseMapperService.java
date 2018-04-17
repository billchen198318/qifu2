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
package org.qifu.base.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.qifu.base.AppContext;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseMapperDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.BaseMapper;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.EntityOrMapperAnnotationParameterUtil;
import org.qifu.base.model.ServiceMethodAuthority;
import org.qifu.base.model.ServiceMethodType;
import org.qifu.base.model.SystemMessage;
import org.qifu.util.SimpleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class BaseMapperService<T extends java.io.Serializable> implements IBaseMapperService<T> {
	
	protected PlatformTransactionManager platformTransactionManager;
	protected TransactionTemplate transactionTemplate;
	
	public BaseMapperService() {
		super();
	}
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		if (this.platformTransactionManager==null) {
			this.platformTransactionManager=(PlatformTransactionManager)AppContext.getBean("transactionManager");
		}
		return platformTransactionManager;
	}
	
	@Autowired
	@Resource(name="transactionManager")
	@Required
	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	
	public TransactionTemplate getTransactionTemplate() {
		if (this.transactionTemplate==null) {			
			this.transactionTemplate=(TransactionTemplate)AppContext.getBean("transactionTemplate");
		}
		return transactionTemplate;
	}	
	
	@Autowired
	@Resource(name="transactionTemplate")
	@Required
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	public boolean isSuperRole() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(Constants.SUPER_ROLE_ADMIN) || subject.hasRole(Constants.SUPER_ROLE_ALL)) {
			return true;
		}
		return false;
	}
	
	public String getAccountId() {		
		Subject subject = SecurityUtils.getSubject();		
		return this.defaultString((String)subject.getPrincipal());		
	}	
	
	public String generateOid() {
		return SimpleUtils.getUUIDStr();
	}
	
	public String defaultString(String source) {
		return SimpleUtils.getStr(source, "");
	}
	
	public Date generateDate() {		
		return new Date();
	}	
	
	protected abstract IBaseMapperDAO<T> getBaseDataAccessObject();
	
	public DefaultResult<List<T>> selectListByParams(Map<String, Object> paramMap) throws ServiceException, Exception {
		if (paramMap == null) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.OBJ_NULL));
		}
		DefaultResult<List<T>> result = new DefaultResult<List<T>>();
		List<T> searchList = (List<T>)this.getBaseDataAccessObject().selectListByParams(paramMap);
		if (searchList!=null && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)));
		}
		return result;
	}	
	
	public DefaultResult<T> selectOneByValue(T mapperObj) throws ServiceException, Exception {
		if (null == mapperObj) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.OBJ_NULL));
		}
		DefaultResult<T> result = new DefaultResult<T>();
		T searchResult = this.getBaseDataAccessObject().selectOneByValue(mapperObj);
		if (searchResult!=null) {
			result.setValue(searchResult);
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)));
		}		
		return result;
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<T> insert(T mapperObj) throws ServiceException, Exception {
		if (null == mapperObj) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.OBJ_NULL));
		}
		DefaultResult<T> result = new DefaultResult<T>();
		((BaseMapper) mapperObj).setOid( this.generateOid() );
		((BaseMapper) mapperObj).setCuserid( this.getAccountId() );
		((BaseMapper) mapperObj).setCdate( this.generateDate() );
		if (this.countByUK(mapperObj) > 0) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.DATA_IS_EXIST) );
		}
		int size = this.getBaseDataAccessObject().insert(mapperObj);
		if (size != 1) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.INSERT_FAIL) );
		}
		result.setValue(mapperObj);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS)));
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<T> insertIgnoreUK(T mapperObj) throws ServiceException, Exception {
		if (null == mapperObj) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.OBJ_NULL));
		}
		DefaultResult<T> result = new DefaultResult<T>();
		((BaseMapper) mapperObj).setOid( this.generateOid() );
		((BaseMapper) mapperObj).setCuserid( this.getAccountId() );
		((BaseMapper) mapperObj).setCdate( this.generateDate() );
		int size = this.getBaseDataAccessObject().insert(mapperObj);
		if (size != 1) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.INSERT_FAIL) );
		}
		result.setValue(mapperObj);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS)));
		return result;		
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRES_NEW, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@SuppressWarnings("rawtypes")
	public DefaultResult<T> update(T mapperObj) throws ServiceException, Exception {
		if (null == mapperObj) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.OBJ_NULL));
		}
		DefaultResult<T> result = new DefaultResult<T>();
		((BaseMapper) mapperObj).setUuserid( this.getAccountId() );
		((BaseMapper) mapperObj).setUdate( this.generateDate() );		
		int size = this.getBaseDataAccessObject().update(mapperObj);
		if (size != 1) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.UPDATE_FAIL) );
		}
		result.setValue(mapperObj);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.UPDATE_SUCCESS)));		
		return result;
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	public DefaultResult<Boolean> delete(T mapperObj) throws ServiceException, Exception {
		if (null == mapperObj) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.OBJ_NULL));
		}
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		T findMapperObj = this.getBaseDataAccessObject().selectOneByValue(mapperObj);
		if (null == findMapperObj) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA) );
		}
		int size = this.getBaseDataAccessObject().delete(mapperObj);
		if (size != 1) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.DELETE_FAIL) );
		}
		result.setValue(true);
		result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.DELETE_SUCCESS)));				
		return result;
	}
	
	public int count(Map<String, Object> paramMap) throws ServiceException, Exception {
		return this.getBaseDataAccessObject().count(paramMap);
	}
	
	public int countByUK(T mapperObj) throws ServiceException, Exception {
		Map<String, Object> ukMap=EntityOrMapperAnnotationParameterUtil.getUKParameter((BaseMapper<?>)mapperObj);
		return this.getBaseDataAccessObject().countByUK(ukMap);
	}
	
}
