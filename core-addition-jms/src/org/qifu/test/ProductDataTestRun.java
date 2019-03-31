package org.qifu.test;

import java.math.BigDecimal;

import javax.jms.Destination;

import org.qifu.base.AppContext;

public class ProductDataTestRun {
	
	public ProductDataTestRun() {
		try {
			this.doSendData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doSendData() throws Exception {
		ProducerService producerService = (ProducerService) AppContext.getBean("test.jms.ProducerService");
		Destination destination = (Destination) AppContext.getBean("queueDestination");
		for (int i=0; i<3; i++) {
			ProductVO product = new ProductVO();
			product.setNo("PROD-"+System.currentTimeMillis()+"");
			product.setName("BOOK_PRODUCT_"+i);
			product.setAmount( new BigDecimal( (i+1)*100) );
			producerService.sendMessageAsJson(destination, product);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		new ProductDataTestRun();
	}
	
}
