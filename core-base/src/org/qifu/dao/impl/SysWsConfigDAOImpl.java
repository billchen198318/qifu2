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

import org.qifu.base.dao.BaseDAO;
import org.qifu.dao.ISysWsConfigDAO;
import org.qifu.po.TbSysWsConfig;
import org.springframework.stereotype.Repository;

@Repository("core.dao.SysWsConfigDAO")
public class SysWsConfigDAOImpl extends BaseDAO<TbSysWsConfig, String> implements ISysWsConfigDAO<TbSysWsConfig, String> {
	
	public SysWsConfigDAOImpl() {
		super();
	}
	
}
