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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.qifu.base.AppContext;
import org.qifu.base.exception.ServiceException;
import org.qifu.job.BaseJob;
import org.qifu.util.ApplicationSiteUtils;
import org.qifu.util.UploadSupportUtils;
import org.qifu.vo.SysVO;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.context.ContextLoader;

@DisallowConcurrentExecution
public class ClearTempDataJobImpl extends BaseJob implements Job {
	protected static Logger log = Logger.getLogger(ClearTempDataJobImpl.class);
	
	public ClearTempDataJobImpl() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (ContextLoader.getCurrentWebApplicationContext() == null) {
			log.warn( "ApplicationContext no completed, AppContext.getApplicationContext() == null" );			
			return;
		}		
		log.info("begin....");
		if (this.checkCurrentlyExecutingJobs(context, this)) {
			log.warn("Same schedule job, current working...");
			return;
		}
		try {
			
			/**
			 * document reference:
			 * com.netsteadfast.greenstep.support.CleanTempUploadForContextInitAndDestroy.java
			 */
			this.login();
			List<SysVO> systems = ApplicationSiteUtils.getSystems();
			if (systems==null || systems.size()<1) {
				return;
			}
			for (SysVO sys : systems) {
				UploadSupportUtils.cleanTempUpload(sys.getSysId());
			}
			
			/**
			 * document reference:
			 * com.netsteadfast.greenstep.bsc.support.CleanJasperReportTempDataForContextInitAndDestroy.java
			 * 
			 */
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate)
					AppContext.getBean("namedParameterJdbcTemplate");
			
			// this is OLD code for bambooBSC, the qifu2 no use it.
			/*
			Map<String, Object> paramMap = new HashMap<String, Object>();
			namedParameterJdbcTemplate.update("delete from bb_swot_report_mst", paramMap);
			namedParameterJdbcTemplate.update("delete from bb_swot_report_dtl", paramMap);
			*/
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("end....");
	}

}
