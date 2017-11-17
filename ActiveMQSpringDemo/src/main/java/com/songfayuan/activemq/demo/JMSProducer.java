/**
 * 项目名称：ActiveMQSpringDemo
 * 项目包名：com.songfayuan.activemq.demo
 * 创建时间：2017年6月28日下午2:01:36
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.activemq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 描述：消息生产者（发送者）
 * @author songfayuan
 * 2017年6月28日下午2:01:36
 */
public class JMSProducer {
	
	//默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	//发送的消息数量
	private static final Integer SENDNUM = 10;
	
	public static void main(String args[]){
		//连接工厂
		ConnectionFactory connectionFactory;
		//连接
		Connection connection = null;
		//会话   接受或者发送消息的线程
		Session session;
		//消息的目的地
		Destination destination;
		//消息生产者
		MessageProducer messageProducer;
		//实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD,JMSProducer.BROKEURL);
		
		
		try {
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//创建session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//创建一个名称为HelloWorld的消息队列
			destination = session.createQueue("HelloWorld");
			//创建消息生产者
			messageProducer = session.createProducer(destination);
			//发送消息
			sendMessage(session, messageProducer);
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 描述：发送消息
	 * @param session
	 * @param messageProducer 消息生产者
	 * @throws Exception
	 * @author songfayuan
	 * 2017年6月28日下午2:58:33
	 */
	public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception{
		for (int i = 0; i < JMSProducer.SENDNUM; i++) {
			//创建一条文本信息
			TextMessage message = session.createTextMessage("ActiveMQ发送消息"+i);
			System.out.println("发送消息：ActiveMQ发送消息");
			//通过消息生产者发出消息
			messageProducer.send(message);
		}
	}
	
}
