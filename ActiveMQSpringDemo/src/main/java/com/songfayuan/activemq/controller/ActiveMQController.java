/**
 * 项目名称：ActiveMQSpringDemo
 * 项目包名：com.songfayuan.activemq.controller
 * 创建时间：2017年6月28日下午9:00:19
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.activemq.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.songfayuan.activemq.mq.producer.queue.QueueSender;
import com.songfayuan.activemq.mq.producer.topic.TopicSender;

/**
 * 描述：controller测试
 * @author songfayuan
 * 2017年6月28日下午9:00:19
 */
@Controller
@RequestMapping("/activemq")
public class ActiveMQController {

	@Resource
	private QueueSender queueSender;
	@Resource
	private TopicSender topicSender;
	
	/**
	 * 描述：发送消息到队列
	 * 	   Queue队列：仅有一个订阅者会收到消息，消息一旦被处理就不会存在队列中
	 * @param message
	 * @return String
	 * @author songfayuan
	 * 2017年6月28日下午11:05:31
	 */
	@RequestMapping("queueSender")
	@ResponseBody
	public String queueSender(@RequestParam("message") String message){
		String opt = "";
		try {
			queueSender.send("test.queue", message);
			opt = "suc";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
	/**
	 * 描述：发送消息到主题（一对多）
	 *     Topic主题：放入一个消息，所有订阅者都会收到
	 * @param message
	 * @return
	 * @author songfayuan
	 * 2017年6月28日下午11:10:38
	 */
	@RequestMapping("topicSender")
	@ResponseBody
	public String topicSender(@RequestParam("message") String message){
		String opt = "";
		try {
			topicSender.send("test.topic", message);
			opt = "suc";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
}
