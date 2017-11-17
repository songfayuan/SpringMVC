/**
 * 项目名称：ActiveMQSpringDemo
 * 项目包名：com.songfayuan.activemq.mq.producer.topic
 * 创建时间：2017年6月28日下午9:14:29
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.activemq.mq.producer.topic;

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
 * 描述：Topic生产者发送消息到Topic
 * @author songfayuan
 * 2017年6月28日下午9:14:29
 */
@Service
//@Component("topicSender")
public class TopicSender {

	@Resource
//	@Autowired
	@Qualifier("jmsTopicTemplate")  // 通过@Qualifier修饰符来注入对应的bean
	private JmsTemplate jmsTemplate;
	
	/**
	 * 描述：发送一条消息到指定的队列（目标）
	 * @param topicName 队列名称
	 * @param message 消息内容
	 * @author songfayuan
	 * 2017年6月28日下午10:37:38
	 */
	public void send(String topicName, final String message){
		jmsTemplate.send(topicName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
}
