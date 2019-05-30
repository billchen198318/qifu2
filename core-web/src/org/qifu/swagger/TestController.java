/* 
 * Copyright 2012-2019 qifu of copyright Chen Xin Nien
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
package org.qifu.swagger;

import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.YesNo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("API")
@Api(value="Test",tags="Test play API")
public class TestController {

	// http://127.0.0.1:8080/core-web/API/play.do
	@ControllerMethodAuthority(check=false)
	@ApiOperation(value="play API", notes="play an message!")
	@RequestMapping(value = "/play.do", method = {RequestMethod.POST} , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DefaultControllerJsonResultObj<String> play(@RequestBody String str) {
		DefaultControllerJsonResultObj<String> obj = new DefaultControllerJsonResultObj<String>();
		obj.setValue("request_str["+str+"]");
		obj.setSuccess(YesNo.YES);
		return obj;
	}
	
}
