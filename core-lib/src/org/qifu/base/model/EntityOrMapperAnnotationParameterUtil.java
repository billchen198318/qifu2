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
package org.qifu.base.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EntityOrMapperAnnotationParameterUtil {
	
	public static <T> Object getPKOneValue(T entity) {  
		Method[] methods=entity.getClass().getMethods(); 
		if (methods==null) {
			return null;
		}
		Object value=null;
		for (int ix=0; ix<methods.length && value==null; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if(annotation instanceof javax.persistence.Id) {
					if (methods[ix].getName().indexOf("get")==0) {
						try {
							value=methods[ix].invoke(entity);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return value;
	}
	
	public static <T> String getPKOneName(T entity) {
		Method[] methods=entity.getClass().getMethods();
		Field[] fields=entity.getClass().getDeclaredFields();
		if (methods==null || fields==null) {
			return null;
		}
		String name=null;
		for (int ix=0; ix<methods.length && name==null; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if(annotation instanceof javax.persistence.Id) {
					for (Field filed : fields) {
						if (methods[ix].getName().replaceFirst("get", "")
								.toLowerCase().equals(filed.getName())) {
							name=filed.getName();
						}
					}
				}
			}
		}
		return name;
	}
	
	public static <T> Map<String, Object> getPKParameter(T entity) {
		Method[] methods=entity.getClass().getMethods();		
		if (methods==null) {
			return null;
		}		
		Map<String, Object> pkMap=new HashMap<String, Object>();
		for (int ix=0; ix<methods.length; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if(annotation instanceof EntityPK) {					
					EntityPK entityAnnotation=(EntityPK)annotation;		
					pkMap.put(entityAnnotation.name(), null);
					setParameter(entity, methods[ix], ((EntityPK) annotation).name(), pkMap);
				}
			}
		}
		return pkMap;
	}
	
	public static <T> Map<String, Object> getUKParameter(T entity) {
		Method[] methods=entity.getClass().getMethods();
		if (methods==null) {
			return null;
		}
		Map<String, Object> ukMap=new HashMap<String, Object>();
		for (int ix=0; ix<methods.length; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if(annotation instanceof EntityUK) {
					EntityUK entityAnnotation=(EntityUK)annotation;		
					ukMap.put(entityAnnotation.name(), null);
					setParameter(entity, methods[ix], ((EntityUK) annotation).name(), ukMap);
				}
			}
		}		
		return ukMap;
	}
	
	private static <T> void setParameter(
			T entity, Method method, String entityAnnotationParameterName, 
			Map<String, Object> dataMap) {
		
		if (method.getName().indexOf("get")==0) {
			try {
				dataMap.put(entityAnnotationParameterName, method.invoke(entity));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}			
		}
	}
	
}
