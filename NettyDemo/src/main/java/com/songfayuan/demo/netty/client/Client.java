/**
 * 项目名称：NettyDemo
 * 项目包名：com.songfayuan.demo.netty.client
 * 创建时间：2017年7月10日下午2:49:02
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.demo.netty.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 描述：客户端具体连接代码
 * @author songfayuan
 * 2017年7月10日下午2:49:02
 */
public class Client {

	private final String host;
	private final int port;
	
	public Client() {
		this(0);
	}

	public Client(int port) {
		this("localhost", port);
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group) //注册线程池
					.channel(NioSocketChannel.class) //使用NioSocketChannel来作为连接用的channel类
					.remoteAddress(new InetSocketAddress(this.host, this.port)) //绑定连接端口和host信息
					.handler(new ChannelInitializer<SocketChannel>() { //绑定连接初始化器
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							System.out.println("connected...");
							ch.pipeline().addLast(new ClientHandler());
						}
					});
			System.out.println("created...");
			ChannelFuture cf = bootstrap.connect().sync(); //异步连接服务器
			System.out.println("connected complete..."); //连接完成
			cf.channel().closeFuture().sync(); //异步等待关闭连接channel
			System.out.println("closed..."); //关闭完成
		} finally {
			group.shutdownGracefully().sync(); //释放线程资源池
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Client("127.0.0.1", 10086).start(); // 连接127.0.0.1/65535，并启动
	}
	
}
