/**
 * 项目名称：NettyDemo
 * 项目包名：com.songfayuan.demo.netty.server
 * 创建时间：2017年7月10日上午11:50:25
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.demo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 描述：Server代码，监听连接
 * @author songfayuan
 * 2017年7月10日上午11:50:25
 */
public class Server {

	private final int port;

	public Server(int port) {
		this.port = port;
	}
	
	public void start() throws Exception{
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(eventLoopGroup) //绑定线程池
					.channel(NioServerSocketChannel.class) //指定使用的channel
					.localAddress(this.port) //绑定监听端口
					.childHandler(new ChannelInitializer<SocketChannel>() { //绑定客户端连接时触发操作

						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							// TODO Auto-generated method stub
							System.out.println("connected...; Client:" + socketChannel.remoteAddress());
							socketChannel.pipeline().addLast(new ServerHandler()); //客户端触发操作
						}

					});
			ChannelFuture channelFuture = serverBootstrap.bind().sync(); //服务器异步创建绑定
			System.out.println(Server.class + " start and listen on " + channelFuture.channel().localAddress());
			channelFuture.channel().closeFuture().sync(); //关闭服务器通道
		} finally {
			eventLoopGroup.shutdownGracefully().sync();  //释放线程资源
		}
	}
	
	public static void main(String args[]) throws Exception {
		new Server(10086).start();//启动
	}
	
}
