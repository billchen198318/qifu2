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
package org.qifu.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://127.0.0.1:8080/core-web/swagger-ui.html
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"org.qifu.swagger"}) // no effective, need use ** RequestHandlerSelectors.basePackage("org.qifu.swagger") **
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	
	private static final String VERSION = "0.1";
	
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("qifu2-business-api")
				.genericModelSubstitutes(DeferredResult.class)
				.forCodeGeneration(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.qifu.swagger"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo(){
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("qifu2 API")
				.description("This is qifu2 api")
				.termsOfServiceUrl("127.0.0.1:8080/core-web/api-docs")
				.version(VERSION)
				.build();
		return apiInfo;
	}	
	
}
