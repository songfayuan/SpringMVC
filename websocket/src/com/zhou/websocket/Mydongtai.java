package com.zhou.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;  
import java.util.Timer;  
import java.util.TimerTask;  
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.zhou.cpu.service.CpuService;
import com.zhou.cpu.serviceImp.CpuServiceImp;
import com.zhou.pojo.MonitorInfoBean;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/dongtai")
public class Mydongtai {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
     
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<Mydongtai> webSocketSet = new CopyOnWriteArraySet<Mydongtai>();
     
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    //为了线程安全，  尝试多种办法后，定义一个静态类，使每一个访问此任务的用户在此进程下创建一个线程。
    //使多个线程同时运行。   并且当某个用户退出时，  session和此线程需要同时退出。(对这个线程问题需要加强了解)
    private static Timer timer = new Timer(true); 
    CpuService cpu = new CpuServiceImp();
     
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
      //发送信息。每2秒发送一次信息。  启动定时线程。
        timer.schedule(task, 1000, 2000);  
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1    
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        task.cancel();
        System.out.println(this.session + "关闭此线程任务。");
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
         
       
    }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
     
    /*  
     * 发送信息  
     */  
    SimpleDateFormat mf2 = new SimpleDateFormat("HH:mm:ss");
    public void sendLong(double param) {  
        try {  
//        	data = "";
//        	data +="[[";
//        	data +="0,";
//        	data +=String.valueOf(param)+",";
//        	data +="false,";
//        	data +="true,";
//        	data +="'"+mf2.format(new Date())+"'";
//        	data +="]]";
        	String data = "{sy:0,sj:"+String.valueOf(param)+",tbcr:false,lj:false,xz:'"+mf2.format(new Date())+"'}";
            if (session.isOpen()) {  
                this.session.getBasicRemote().sendText(data);  
            }  
            
            System.out.println(this.session +"---"+ data);
        } catch (IOException e) {  
            try {  
                this.session.close();  
            } catch (IOException e1) {}  
        }  
    }  
      
    /**  
     * cpu任务。发送随机数。  
     */  
    Random random = new Random();
    TimerTask task = new TimerTask() {  
        public void run() {     
        	//long param = random.nextInt(1000);  
            try {
				sendLong(cpu.getCupnum());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }     
    };  
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
    	Mydongtai.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
    	Mydongtai.onlineCount--;
    }
	
}
