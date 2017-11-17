/**
 * 项目名称：WebScoketServerByNetty
 * 项目包名：com.song.netty.webscoket
 * 创建时间：2017年4月18日下午2:13:14
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.song.netty.webscoket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 描述：NettyService
 * @author songfayuan
 * 2017年4月18日下午2:13:14
 */
public class NettyServer {
	
	public static void main(String[] args) {
		new NettyServer().run();
	}
	
	public void run(){

		//配置服务端的NIO线程组 一个用于服务端接收客户端的连接，另一个用于进行SocketChannel的网络读写
		//boss用来监控tcp链接,具体的说,boss执行 server.accept()操作.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//worker用来处理io事件,worker处理事件的读写到业务逻辑处理等后续操作.
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			//ServerBootstrap是Netty用于启动NIO服务端的辅助类，目的是降低服务端的开发难度。
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			//指定NioServerSocketChannel类初始化channel用来接受客户端请求。
			b.channel(NioServerSocketChannel.class);
			//绑定childChannelHandler,其作用类似于Reactor模式中的handler类，主要用于处理网络I/O事件，例如记录日志、对消息进行编解码等。
			b.childHandler(new ChildChannelHandler());
			
			System.out.println("服务端开启等待客户端连接 ... ...");
			
			//绑定端口，同步等待成功
			//使用bind绑定监听端口，随后，调用它的同步阻塞方法sync等待绑定操作完成，完成后Netty会返回一个ChannelFuture,主要用于异步操作的通知回调。
			Channel ch = b.bind(7397).sync().channel();
			
			//等待服务器监听端口关闭
			ch.closeFuture().sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
	
} 
