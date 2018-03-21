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
package org.qifu.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.qifu.base.model.YesNo;
import org.qifu.sys.WsAuthenticateUtils;
import org.qifu.util.MailClientUtils;
import org.qifu.webservice.ISendMailService;
import org.springframework.stereotype.Service;

@Service("core.webservice.SendMailService")
@WebService
public class SendMailServiceImpl implements ISendMailService {
	
	public SendMailServiceImpl() {
		super();
	}

	@WebMethod
	@Override
	public String sendSimple(
			@WebParam(name="authenticate") String authenticate, 
			@WebParam(name="from") String from, 
			@WebParam(name="to") String to,
			@WebParam(name="subject") String subject, 
			@WebParam(name="text") String text) throws Exception {
		
		String message = YesNo.NO;
		try {
			if ( !WsAuthenticateUtils.valid(authenticate) ) {
				return message;
			}
			MailClientUtils.send(from, to, subject, text);
			message = YesNo.YES;
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage().toString();
		}
		return message;
	}

}
