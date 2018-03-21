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
package org.qifu.job.impl;

import org.apache.log4j.Logger;
import org.qifu.base.exception.ServiceException;
import org.qifu.job.BaseJob;
import org.qifu.util.SystemExpressionJobUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;

/**
 * 注意: 這個Job 在 Quartz 中的設定, 要每分鐘都需執行處理
 *
 */
public class SysExpressionJobImpl extends BaseJob implements Job {
	protected static Logger log = Logger.getLogger(SysExpressionJobImpl.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (ContextLoader.getCurrentWebApplicationContext() == null) {
			log.warn( "ApplicationContext no completed, AppContext.getApplicationContext() == null" );			
			return;
		}
		try {
			this.loginForBackgroundProgram();
			SystemExpressionJobUtils.executeJobs();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.logoutForBackgroundProgram();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
	}
	
}
