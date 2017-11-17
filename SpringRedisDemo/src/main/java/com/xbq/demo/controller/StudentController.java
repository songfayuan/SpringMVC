package com.xbq.demo.controller;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xbq.demo.util.RedisCacheUtil;

/**
 * 控制层
 * @author xbq
 */
@Controller
@RequestMapping("/student/")
public class StudentController {

	@Resource
	private RedisCacheUtil redisCache;
	
	// 查询数据
	@RequestMapping("list")
	public String list(HttpServletResponse response, HttpServletRequest request){
		String re = redisCache.hget("tb_student", "stu_id");
		try {
			this.write(response, re);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 回写到页面上
	 * @param response
	 * @param o
	 * @throws Exception
	 */
	private void write(HttpServletResponse response, Object o) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
}
