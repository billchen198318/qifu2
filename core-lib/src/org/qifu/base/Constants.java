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
package org.qifu.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.qifu.base.model.YesNo;

public class Constants {
	/**
	 * 不要更改這個設定
	 */
	public static final String BASE_ENCODING = "utf-8";
	
	/**
	 * EncryptorUtils 要用的 key1
	 */
	public static final String ENCRYPTOR_KEY1 = "pOk%$ewQaIUyBvCS@Oj!~%O$kW1p2Rh9";
	
	/**
	 * EncryptorUtils 要用的 key2
	 */
	public static final String ENCRYPTOR_KEY2 = "7913670289654325";		
	
	/**
	 * 保留查詢參數名稱 for PageOf , BaseDAO
	 */
	public static final String _RESERVED_PARAM_NAME_QUERY_SORT_TYPE = "sortType";
	
	/**
	 * 保留查詢參數名稱 for PageOf , BaseDAO
	 */
	public static final String _RESERVED_PARAM_NAME_QUERY_ORDER_BY = "orderBy";	
	
	public static final String QUERY_TYPE_OF_SELECT="select"; // BaseService 查詢 grid 要用
	public static final String QUERY_TYPE_OF_COUNT="count"; // BaseService 查詢 grid 要用	
	
	public static final String SUPER_ROLE_ALL = "*";
	public static final String SUPER_ROLE_ADMIN = "admin";
	public static final String SUPER_PERMISSION = "*";
	public static final String SYSTEM_BACKGROUND_USER = "system"; // 背景程式要用 , 配 SubjectBuilderForBackground.java 與 shiro.ini
	public static final String SYSTEM_BACKGROUND_PASSWORD = "password99"; // 背景程式要用 , 配 SubjectBuilderForBackground.java 與 shiro.ini
	
	public static final String SERVICE_ID_TYPE_DISTINGUISH_SYMBOL = "#"; // logic service 用來組 service id 與 ServiceMethodType 成字串, 查有沒有權限
	
	public static final String SESS_ACCOUNT="SESSION_QIFU_ACCOUNT"; // 登入 account id 放到 session 變數名
	public static final String SESS_LANG = "SESSION_QIFU_LANG";
	public static final String SESS_SYSCURRENT_ID = "SESSION_QIFU_SYSCURRENT_ID";
	
	public static final String APP_SITE_CURRENTID_COOKIE_NAME = "QIFU_SYSCURRENT_ID"; // 跨站 cookie 要用的名稱
	
	public static final String QIFU_PAGE_IN_TAB_IFRAME = "isQifuPageChange";
	public static final String QIFU_PAGE_PROG_PARAM = "qifuProgId";
	
	/**
	 * GreenStepBaseFormAuthenticationFilter 要用的
	 */
	public static final String NO_LOGIN_JSON_DATA = "{ \"success\":\"" + YesNo.NO + "\",\"message\":\"Please login!\",\"login\":\"" + YesNo.NO + "\",\"isAuthorize\":\"" + YesNo.NO + "\" }";
	/**
	 * GreenStepBaseFormAuthenticationFilter 要用的
	 */	
	public static final String NO_AUTHZ_JSON_DATA = "{ \"success\":\"" + YesNo.NO + "\",\"message\":\"no authorize!\",\"login\":\"" + YesNo.YES + "\",\"isAuthorize\":\"" + YesNo.NO + "\" }";
	
	public static final String PAGE_MESSAGE="pageMessage";
	
	public static final String HTML_SELECT_NO_SELECT_ID="all";
	public static final String HTML_SELECT_NO_SELECT_NAME=" - please select - ";	
	
	public static final String ID_DELIMITER = ";"; // 有時要將多筆 OID 或 key 組成一組字串 , 這是就用這個符號來區分	
	
	public static final String INPUT_NAME_DELIMITER = ":"; // 有時輸入欄位id或名稱,想要有區分一些有意義的資料時用
	
	public static final String TMP_SUB_DIR_NAME = "qifu";
	
	public static final String HTML_BR = "<br>";
	
	public static String getTmpDir() {
		return System.getProperty("java.io.tmpdir");
	}
	
	public static String getWorkTmpDir() {
		String dirPath = getTmpDir() + "/" + TMP_SUB_DIR_NAME + "/";
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			try {
				FileUtils.forceMkdir(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file = null;
		return dirPath;
	}
	
	/**
	 * map data from applicationContext-appSettings.xml app.config.appSettings
	 */
	private static Map<String, Object> appSettingsMap = null;
	
	/**
	 * get applicationContext-appSettings.xml app.config.appSettings
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSettingsMap() {
		if (appSettingsMap!=null) {
			return appSettingsMap;
		}
		appSettingsMap = (Map<String, Object>)AppContext.getBean("app.config.appSettings");
		if (appSettingsMap==null) {
			appSettingsMap=new HashMap<String, Object>();
		}
		return appSettingsMap;
	}
	
	/**
	 * 系統上傳用目錄夾
	 * 
	 * @return
	 */
	public static String getUploadDir() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.uploadDir");
	}
	
	/**
	 * 本系統的ID , 需要與 TB_SYS.SYS_ID 配合
	 * 
	 * @return
	 */
	public static String getSystem() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.system");
	}
	
	/**
	 * 主系統的ID , 需要與 TB_SYS.SYS_ID 配合 , 目前是CORE
	 * 
	 * @return
	 */
	public static String getMainSystem() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.mainSystem");		
	}
	
	/**
	 * 必須與 web.xml 中的設定一樣
	 * 
	 * @return
	 */
	public static String getCxfWebServiceMainPathName() {
		return "services";
	}
	
	/**
	 * 給 CXF JAXRSServerFactoryBean addrss 設定的值 , 如 /jaxrs/
	 * @return
	 */
	public static String getJAXRSServerFactoryBeanAddress() {
		getSettingsMap();
		return (String)appSettingsMap.get("cxf.JAXRSServerFactoryBean.address");
	}
	
	/**
	 * 取到系統要部屬jasper-report 的目錄位址
	 * @return
	 */
	public static String getDeployJasperReportDir() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.deployJasperReportDir");
	}
	
	/**
	 * 系統加密用的key1 , EncryptorUtils 要用的 key1
	 * @return
	 */
	public static String getEncryptorKey1() {
		getSettingsMap();
		String key = (String)appSettingsMap.get("base.encryptorKey1");
		if (StringUtils.isBlank(key)) {
			key = ENCRYPTOR_KEY1;
		}
		return key;
	}
	
	/**
	 * 系統加密用的key2 , EncryptorUtils 要用的 key2
	 * @return
	 */
	public static String getEncryptorKey2() {
		getSettingsMap();
		String key = (String)appSettingsMap.get("base.encryptorKey2");
		if (StringUtils.isBlank(key)) {
			key = ENCRYPTOR_KEY2;
		}
		return key;
	}	
	
	/**
	 * 登入頁面是否需要輸入 CaptchaCode
	 * @return
	 */
	public static String getLoginCaptchaCodeEnable() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.loginCaptchaCodeEnable");
	}
	
	/**
	 * 是否更新 tb_sys.host 的設定
	 * config update tb_sys.host when start CORE-WEB,GSBSC-WEB,QCHARTS-WEB
	 * 1 - only first one 只有第一次啟動時, check 有 log 檔案後就不更新了
	 * 2 - always update 每次都會更新
	 * @return
	 */
	public static String getApplicationSiteHostUpdateMode() {
		getSettingsMap();
		return (String)appSettingsMap.get("base.applicationSiteHostUpdateMode");
	}
	
}
