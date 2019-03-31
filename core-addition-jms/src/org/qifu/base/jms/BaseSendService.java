package org.qifu.base.jms;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public abstract class BaseSendService {
	protected static Logger logger = Logger.getLogger(BaseSendService.class);
	
	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	@Autowired
	@Required
	@Resource(name = "jmsTemplate")
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void sendMessage(Destination destination, final String message) {
		logger.info("send message: [" + message + "]");
		this.jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
}
