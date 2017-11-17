<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ECharts">
    <meta name="author" content="kener.linfeng@gmail.com">
    <title>ECharts · Example</title>
</head>
<body>
	<script src="echart/build/dist/jquery-1.6.2.min.js"></script>
    <script src="echart/build/dist/echarts.js"></script>
	<script src="echart/build/dist/theme/macarons.js"></script>
    <!-- Fixed navbar -->
	<div class="nav2">
		<div id="main" style="height:600px"></div>
	</div>
   <script type="text/javascript">
		 // 路径配置
        require.config({
            paths: {
                echarts: 'echart/build/dist'
            }
        });
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main'),theme); 
                option = {
						 title : {
							 x : 'center',
							text: 'CPU运行情况'
						},
						tooltip : {
							trigger: 'axis'
						},
						legend: {
							x : '60px',
							y : '40px',
							data:['CPU']
						},
						toolbox: {
							show : true,
							feature : {
								mark : {show: true},
								dataView : {show: true, readOnly: false},
								restore : {show: true},
								saveAsImage : {show: true}
							}
						},
						calculable : true,
						xAxis : [
							{
								type : 'category',
								boundaryGap : false,
								data : ['','','','','','','','','','','','','','','','','','','','']
							}
						],
						yAxis : [
							{
								type : 'value',
								axisLabel : {
									formatter: '{value}'
								}
							}
						],
						series : [
							{
								name:'CPU',
								type:'line',
								data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
							}
						]
					};	
					
					//页面定时器方法
					//var axisData;
					//var timeTicket;
					//clearInterval(timeTicket);
					//timeTicket = setInterval(function (){
					//	axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
						// 动态数据接口 addData
					//	myChart.addData([
					//		[
					//			0,        // 系列索引   0
					//			Math.round(Math.random() * 1000), // 新增数据   Math.round(Math.random() * 1000),  新增数据
					//			false,    // 新增数据是否从队列头部插入
					//			true,
					//			axisData,			// 坐标轴标签
					//		]
					//	]);						
					//}, 2100);
					
					
					//接收到消息的回调方法
					var option1;
				      websocket.onmessage = function(){
				    	  console.log("回调的数据："+event.data);
				          option1= eval('(' + event.data + ')');
				          //option1 = event.data;
				          //option1 = [[0,343,false,true,'09:43:02']];
				          myChart.addData([
				          	[
				          		option1.sy,
				          		option1.sj,
				          		option1.tbcj,
				          		option1.lj,
				          		option1.xz
				          	]
				          ]);	
				      }
						
                // 为echarts对象加载数据 
                myChart.setOption(option); 	
            }
        );
        
        
      var websocket = null;
       
      //判断当前浏览器是否支持WebSocket
      if('WebSocket' in window){
          websocket = new WebSocket("ws://localhost:8080/websocket/dongtai");
      }
      else{
          alert('Not support websocket')
      }
       
      //连接发生错误的回调方法
      websocket.onerror = function(){
          setMessageInnerHTML("error");
      };
       
      //连接成功建立的回调方法
      websocket.onopen = function(event){
          //setMessageInnerHTML("open");
          alert("成功建立连接！");
      }
       
     
      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function(){
          websocket.close();
      }
       
      //关闭连接
      function closeWebSocket(){
          websocket.close();
      }
       
      //发送消息
      function send(){
          var message = document.getElementById('text').value;
          websocket.send(message);
      }
	</script>

</body>
</html>
