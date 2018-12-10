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
package org.qifu.base.config;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.qifu.base.Constants;
import org.qifu.util.EncryptorUtils;
import org.qifu.util.SimpleUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class RevisePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	protected Logger logger = Logger.getLogger(RevisePropertyPlaceholderConfigurer.class);
	
	private List<String> encryptPropertyNames;
	
	public List<String> getEncryptPropertyNames() {
		return encryptPropertyNames;
	}

	public void setEncryptPropertyNames(List<String> encryptPropertyNames) {
		this.encryptPropertyNames = encryptPropertyNames;
	}
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		@SuppressWarnings("unchecked")
		Enumeration<String> propertyNames = (Enumeration<String>) props.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = propertyNames.nextElement();
			for (String encryptItemName : this.encryptPropertyNames) {
				if (!propertyName.equals(encryptItemName)) {
					continue;
				}
				this.resetPropertyValue(props, propertyName);
			}
		}		
		super.processProperties(beanFactory, props);
	}
	
	private void resetPropertyValue(Properties props, String propertyName) {
		String originalValue = props.getProperty(propertyName);
		if (StringUtils.isBlank(originalValue)) {
			return;
		}
		String newValue = EncryptorUtils.decrypt(Constants.ENCRYPTOR_KEY1, Constants.ENCRYPTOR_KEY2, SimpleUtils.deHex(originalValue));
		if (!StringUtils.isBlank(newValue)) {
			props.setProperty(propertyName, newValue);
			logger.info("reset property: " + propertyName);
		}
	}
	
}
