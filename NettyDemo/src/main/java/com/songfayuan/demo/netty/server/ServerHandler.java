/**
 * 项目名称：NettyDemo
 * 项目包名：com.songfayuan.demo.netty.server
 * 创建时间：2017年7月10日下午2:02:11
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.demo.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 描述：具体处理客户端连接的代码
 * @author songfayuan
 * 2017年7月10日下午2:02:11
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		System.out.println("server channelRead...; reveived:" + msg);
		ctx.write(msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		System.out.println("server channelReadComplete...");
		//第一种方法：写一个空的buf，并刷新写出区域，完成后关闭socket channel连接
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		System.out.println("server occur exception:"+cause.getMessage());
		cause.printStackTrace();
		ctx.close(); //关闭发生异常的连接
	}
}
