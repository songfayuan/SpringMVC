package com.xbq.demo;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xbq.demo.entity.User;
import com.xbq.demo.util.RedisCacheUtil;

/**
 * 测试redis操作
 */
public class RedisTest {

	private RedisCacheUtil redisCache;
	private static String key;
	private static String field;
	private static String value;
	
	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		redisCache = (RedisCacheUtil) context.getBean("redisCache");
	}

	// 初始化 数据
	static {
		key = "songfayuan:test:redisDemo";
		field = "stu_name4";
		value = "redis操作测试数据：一系列的关于student的信息！";
	}
	
	//测试增加数据
	@Test
	public void testHset() {
		redisCache.hset(key, field, value);
		System.out.println("数据保存成功！");
	}
	
	//测试增加数据
	@Test
	public void testHset2() {
		User user = new User();
		user.setKey("songfayuan:test:redisDemo");
		user.setField("stu_name1");
		user.setValue("redis操作测试数据：一系列的关于student的信息！");
		redisCache.hset(user.getKey(), user.getField(), user.getValue());
		System.out.println("数据保存成功！");
	}

	//测试删除数据
	@Test
	public void testHDelete(){
		redisCache.hdel(key, field);
		System.out.println("数据删除成功！");
	}
	
	//测试查询数据
	@Test
	public void testHget(){
		String re = redisCache.hget(key, field);
		System.out.println("查询到的数据：" + re);
	}
	
	//测试查询全部数据
	@Test
	public void testHgetAll(){
		Map<Object, Object> map = redisCache.hgetAll(key);
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			System.out.println("field:"+entry.getKey()+",value:"+entry.getValue());
		}
	}
	
	//测试数据的数量
	@Test
	public void testHsize(){
		long size = redisCache.hsize(key);
		System.out.println("查询到的数据的数量为：" + size);
	}
}
