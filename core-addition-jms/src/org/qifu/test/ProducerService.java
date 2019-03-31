package org.qifu.test;

import javax.jms.Destination;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProducerService {
	
	public void sendMessageAsJson(Destination destination, ProductVO product) throws JsonProcessingException, Exception;
	
}
