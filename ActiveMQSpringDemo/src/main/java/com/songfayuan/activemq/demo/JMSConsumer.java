/**
 * 项目名称：ActiveMQSpringDemo
 * 项目包名：com.songfayuan.activemq.demo
 * 创建时间：2017年6月28日下午3:06:46
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.activemq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 描述：消息的消费者（接受者）
 * @author songfayuan
 * 2017年6月28日下午3:06:46
 */
public class JMSConsumer {

	//默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	public static void main(String args[]){
		
		//连接工厂
		ConnectionFactory connectionFactory;
		//连接
		Connection connection = null;
		//会话  接受或者发送消息的线程
		Session session;
		//消息目的地
		Destination destination;
		//消息的消费者
		MessageConsumer messageConsumer;
		//实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
		
		try {
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//创建session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//创建一个连接HelloWorld的消息队列
			destination = session.createQueue("HelloWorld");
			//创建消息消费者
			messageConsumer = session.createConsumer(destination);
			
			while (true) {
				TextMessage textMessage = (TextMessage)messageConsumer.receive(100000);
				if (textMessage != null) {
					System.out.println("收到的消息："+textMessage.getText());
				} else {
					break;
				}
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
