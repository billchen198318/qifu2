package org.qifu.test;

import javax.jms.Destination;

import org.qifu.base.jms.BaseSendService;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("test.jms.ProducerService")
public class ProducerServiceImpl extends BaseSendService implements ProducerService {

	@Override
	public void sendMessageAsJson(Destination destination, ProductVO product) throws JsonProcessingException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		super.sendMessage(destination, objectMapper.writeValueAsString(product));
	}
	
}
