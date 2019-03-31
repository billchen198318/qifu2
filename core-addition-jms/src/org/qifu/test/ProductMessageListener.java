package org.qifu.test;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductMessageListener implements MessageListener {
	protected static Logger logger = Logger.getLogger(ProductMessageListener.class);
	
	@Override
	public void onMessage(Message message) {
		logger.info("recive message...");
		String text = "";
		TextMessage textMessage = (TextMessage) message;
		try {
			text = textMessage.getText();
			logger.info( "content: " + text );
			
			ProductVO product = this.getProduct(text);
			if (product != null) {
				System.out.println(">>> " + product.toString());
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private ProductVO getProduct(String jsonStr) {
		ProductVO product = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			product = (ProductVO) mapper.readValue(jsonStr, ProductVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return product;
	}
	
}
