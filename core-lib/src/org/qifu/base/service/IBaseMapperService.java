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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;

public interface IBaseMapperService<T extends java.io.Serializable> {
	
	public String getAccountId();
	public String generateOid();
	public String defaultString(String source);
	public Date generateDate();	
	
	public DefaultResult<List<T>> selectListByParams(Map<String, Object> paramMap) throws ServiceException, Exception;
	
	public DefaultResult<T> selectOneByValue(T mapperObj) throws ServiceException, Exception;
	
	public DefaultResult<T> insert(T mapperObj) throws ServiceException, Exception;
	
	public DefaultResult<T> insertIgnoreUK(T mapperObj) throws ServiceException, Exception;
	
	public DefaultResult<T> update(T mapperObj) throws ServiceException, Exception;
	
	public DefaultResult<Boolean> delete(T mapperObj) throws ServiceException, Exception;
	
	public int count(Map<String, Object> paramMap) throws ServiceException, Exception;
	
	public int countByUK(T mapperObj) throws ServiceException, Exception;
	
}
