/**
 * 项目名称：ActiveMQSpringDemo
 * 项目包名：com.songfayuan.activemq.mq.producer.queue
 * 创建时间：2017年6月28日下午10:40:40
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.activemq.mq.producer.queue;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 描述：队列消息生产者，发送消息到队列
 * @author songfayuan
 * 2017年6月28日下午10:40:40
 */
@Service
//@Component("queueSender")
public class QueueSender {

	@Resource
//	@Autowired
	@Qualifier("jmsQueueTemplate")  //通过@Qualifier修饰符来注入对应的bean
	private JmsTemplate jmsTemplate;
	
	/**
	 * 描述：发送一条消息到指定的队列（目标）
	 * @param queueName 队列名称
	 * @param message  消息内容
	 * @author songfayuan
	 * 2017年6月28日下午10:45:56
	 */
	public void send(String queueName, final String message){
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
}
