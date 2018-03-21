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
package org.qifu.base.aspect;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class AspectConstants {
	
	/**
	 * 注意 base.dao.*.* 不是 base.dao..*.*
	 */
	public static final String DATA_ACCESS_OBJECT_PACKAGE = " execution(* org.qifu.base.dao.*.*(..) ) || execution(* org.qifu.dao..*.*(..) ) || execution(* org.qifu.common.dao..*.*(..) ) || execution(* org.qifu.mes.dao..*.*(..) ) ";
	
	/**
	 * 注意 service.*.*   不是 service..*.* , 如果兩個點..*.* 就會包含 service.logic.
	 * 注意 base.service.*.* 不是 base.service..*.*
	 */
	public static final String BASE_SERVICE_PACKAGE = " execution(* org.qifu.base.service.*.*(..) ) || execution(* org.qifu.service.*.*(..) ) || execution(* org.qifu.common.service.*.*(..) ) || execution(* org.qifu.mes.service.*.*(..) ) ";
	
	/**
	 * 注意 base.service.logic.*.* 不是 base.service.logic..*.*
	 */
	public static final String LOGIC_SERVICE_PACKAGE = " execution(* org.qifu.base.service.logic.*.*(..) ) || execution(* org.qifu.service.logic..*.*(..) ) || execution(* org.qifu.common.service.logic..*.*(..) ) || execution(* org.qifu.mes.service.logic..*.*(..) ) ";
	
	public static boolean isLogicService(String serviceId) {
		if (StringUtils.defaultString(serviceId).indexOf(".service.logic.") > -1) {
			return true;
		}
		return false;
	}
	
	public static String getServiceId(Annotation[] annotations) {
		String serviceId = "";
		if (annotations == null) {
			return serviceId;
		}
		for (Annotation anno : annotations) {
			if (anno instanceof Service) {
				serviceId = ((Service)anno).value();
			}
		}
		return serviceId;
	}
	
	public static String getRepositoryId(Annotation[] annotations) {
		String repoId = "";
		if (annotations == null) {
			return repoId;
		}
		for (Annotation anno : annotations) {
			if (anno instanceof Repository) {
				repoId = ((Repository)anno).value();
			}
		}
		return repoId;
	}	
	
}
