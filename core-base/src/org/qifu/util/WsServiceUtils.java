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
package org.qifu.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.AppContext;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.TbSysWsService;
import org.qifu.service.ISysWsServiceService;
import org.qifu.vo.SysWsServiceVO;

@SuppressWarnings("unchecked")
public class WsServiceUtils {
	private static final int TIMEOUT = 10000; // 10-sec
	private static ISysWsServiceService<SysWsServiceVO, TbSysWsService, String> sysWsServiceService;
	
	static {
		sysWsServiceService = (ISysWsServiceService<SysWsServiceVO, TbSysWsService, String>)
				AppContext.getBean("core.service.SysWsServiceService");
	}
	
	public static Object getServiceByResource(String id) throws ServiceException, Exception {
		return getServiceByResource(Constants.getSystem(), id);
	}
	
	public static Object getServiceByResource(String system, String id) throws ServiceException, Exception {
		if (StringUtils.isBlank(system) || StringUtils.isBlank(id)) {
			throw new Exception( SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK) );
		}
		SysWsServiceVO wsService = new SysWsServiceVO();
		wsService.setSystem(system);
		wsService.setId(id);
		DefaultResult<SysWsServiceVO> result = sysWsServiceService.findByUK(wsService);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		wsService = result.getValue();
		return getService(wsService.getBeanId(), wsService.getWsdlAddress());
	}
	
	public static Object getService(String wsClientBeanId, String wsdlAddress) throws Exception {
		if (StringUtils.isBlank(wsClientBeanId)) {
			throw new IllegalArgumentException("error, bean-Id is required!");
		}
		Object serviceObj = AppContext.getBean(wsClientBeanId);
		if (!StringUtils.isBlank(wsdlAddress)) { // 更改連線wsdl位置, 不使用原本xml設定檔中設定的連線wsdl位置 
			BindingProvider bindingProvider = (BindingProvider) serviceObj;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlAddress);			
		}
		return serviceObj;
	}
	
	public static Object getService(String wsClientBeanId) throws Exception {
		return getService(wsClientBeanId, null);
	}
	
	public static boolean testConnection(String wsdlAddress) throws Exception {
		if (StringUtils.isBlank(wsdlAddress)) {
			return true;
		}
		boolean status = false;
		try {
			URL url = new URL(wsdlAddress);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			if (connection.getContent()!=null) {
				status = true;
			}			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
	
}
