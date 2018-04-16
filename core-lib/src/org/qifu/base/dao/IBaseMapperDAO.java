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

import java.util.List;
import java.util.Map;

public interface IBaseMapperDAO<T extends java.io.Serializable> {
	
	public String getNameSpace();		
	
	public List<T> selectListByParams(Map<String, Object> paramMap) throws Exception;
	
	public T selectOneByValue(T mapperObj) throws Exception;	
	
	public int insert(T mapperObj) throws Exception;
	
	public int update(T mapperObj) throws Exception;
	
	public int delete(T mapperObj) throws Exception;
	
	public int count(Map<String, Object> paramMap) throws Exception;
	
	public int countByUK(Map<String, Object> paramMap) throws Exception;
	
}
