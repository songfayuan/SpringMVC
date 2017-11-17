/**
 * 项目名称：WebScoketServerByNetty
 * 项目包名：com.song.netty.common
 * 创建时间：2017年4月18日下午2:10:25
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.song.netty.common;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 描述：
 * @author songfayuan
 * 2017年4月18日下午2:10:25
 */
public class Global {
	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
} 