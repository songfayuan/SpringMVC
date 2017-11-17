/**
 * 项目名称：WebScoketServerByNetty
 * 项目包名：com.song.netty.webscoket
 * 创建时间：2017年4月18日下午2:11:31
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.song.netty.webscoket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 描述：
 * @author songfayuan
 * 2017年4月18日下午2:11:31
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
	@Override
	protected void initChannel(SocketChannel e) throws Exception {
		
		e.pipeline().addLast("http-codec",new HttpServerCodec());
		e.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
		e.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
		e.pipeline().addLast("handler",new MyWebSocketServerHandler());
	}
} 
