/**
 * 项目名称：ActiveMQSpringDemo
 * 项目包名：com.songfayuan.activemq.mq.consumer.topic
 * 创建时间：2017年6月28日下午10:47:28
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.activemq.mq.consumer.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 描述：Topic消息监听器
 * @author songfayuan
 * 2017年6月28日下午10:47:28
 */
@Service
//@Component
public class TopicReceiver2 implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("TopicReceiver2接收到消息："+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	
	
}
