

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.tf.mongodb.demo.bean.User2;
import cn.tf.mongodb.demo.dao.DemoDao;
import cn.tf.mongodb.demo.util.Page;

/**
 * 描述：mongodb基本操作
 * 	增加，删除，修改，全部查询，按条件查询一条，分页查询....
 * @author songfayuan
 * 2017年7月5日下午2:53:10
 */
@org.junit.runner.RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:sysconfig/spring/db.xml" })
public class UserTest2 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	public static String userid = "595ba1e798c12f479211225e";
	
	@Autowired
	DemoDao demoDao;
	
	//保存-插入
	@Test
	public void testAdd(){
		User2 user=new User2();
		user.setUsername("宋发元");
		user.setPassword("123456");
		user.setGender("男");
		user.setAge(24);
		Date creatTime=new Date();
		user.setCreateTime(creatTime);
		this.demoDao.add(user);
		System.out.println("----------------------新增数据----------------------");
	}
	
	//查询全部数据
	@Test
	public void testFindAll(){
		List<User2> list = this.demoDao.findAll(User2.class);
		for (User2 user : list) {
			System.out.println("id:"+user.getId()+" username:"+user.getUsername()+" password:"+user.getPassword()+" gender:"+user.getGender()+" age:"+user.getAge()+" creatTime:"+user.getCreateTime().toString());
		}
		System.out.println("----------------------获取全部的数据----------------------");
	}
	
	//根据id查询数据
	@Test
	public void testFindById(){
		User2 user = this.demoDao.findById(User2.class, userid);
		System.out.println("id:"+user.getId()+" username:"+user.getUsername()+" password:"+user.getPassword()+" gender:"+user.getGender()+" age:"+user.getAge()+" creatTime:"+user.getCreateTime().toString());
		System.out.println("----------------------获取单个对象----------------------");
	}
	
	//修改数据
	@Test
	public void testUpdate(){
		User2 user = this.demoDao.findById(User2.class, userid);
		user.setGender("女");
		this.demoDao.saveOrUpdate(user);
		User2 newUser = this.demoDao.findById(User2.class, userid);
		System.out.println("id:"+newUser.getId()+" username:"+newUser.getUsername()+" password:"+newUser.getPassword()+" gender:"+newUser.getGender()+" age:"+newUser.getAge()+" creatTime:"+newUser.getCreateTime().toString());
		System.out.println("----------------------修改数据成功----------------------");
	}
	
	//删除数据
	@Test
	public void testRemove(){
		User2 user = this.demoDao.findById(User2.class, userid);
		this.demoDao.remove(user);
		User2 oldUser = this.demoDao.findById(User2.class, userid);
		if (oldUser == null) {
			System.out.println("----------------------删除对象成功----------------------");
		}
		//this.demoDao.add(user);
	}
	
	//根据条件查询
	@Test
	public void testCount(){
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("username").is("宋发元");
		query.addCriteria(criteria);
		Long total = this.demoDao.count(User2.class, query);
		System.out.println("----------------------用户总数："+total+"----------------------");
	}
	
	//分页查询
	@Test
//	public void testFindAllByPage(User2 user, Integer page, Integer pageSize){
	public void testFindAllByPage(){
		User2 user = new User2();
//		user.setUsername("文");
		Integer pageno = 1;
		Integer pageSize = 10;
		Page<User2> list = this.demoDao.findAllByPage(user, pageno, pageSize);
		for (User2 user2 : list.getData()) {
			System.out.println("id:"+user2.getId()+" username:"+user2.getUsername()+" password:"+user2.getPassword()+" gender:"+user2.getGender()+" age:"+user2.getAge()+" creatTime:"+user2.getCreateTime().toString());
		}
		System.out.println("----------------------获取分页查询的数据----------------------");
	}
	
}
