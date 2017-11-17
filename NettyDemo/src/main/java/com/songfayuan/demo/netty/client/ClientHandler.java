/**
 * 项目名称：NettyDemo
 * 项目包名：com.songfayuan.demo.netty.client
 * 创建时间：2017年7月10日下午2:24:12
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.demo.netty.client;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 描述：客户端连接具体通信代码
 * @author songfayuan
 * 2017年7月10日下午2:24:12
 */
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("client channelActive...");
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));  //必须有flush
	}
	
	/*public void channelRead(ChannelHandlerContext ctx, ByteBuf msg) {
		System.out.println("client channelRead...");
		ByteBuf buf = msg.readBytes(msg.readableBytes());
		System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
	}*/
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("client channelRead...");
		ByteBuf buf = msg.readBytes(msg.readableBytes());
		System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
	}
}
