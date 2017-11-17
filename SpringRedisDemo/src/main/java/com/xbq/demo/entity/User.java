/**
 * 项目名称：SpringRedisDemo
 * 项目包名：com.xbq.demo.entity
 * 创建时间：2017年7月5日下午8:04:49
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.xbq.demo.entity;

import java.io.Serializable;

/**
 * 描述：
 * @author songfayuan
 * 2017年7月5日下午8:04:49
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 6833629858635758517L;
	
	String key;
	String field;
	String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
