package cn.tf.mongodb.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import cn.tf.mongodb.demo.bean.User2;
import cn.tf.mongodb.demo.util.Page;



public interface DemoDao {

	//添加
	void add(Object obj);

	//查询全部数据
	<T> List<T> findAll(Class<T> entityClass);

	//根据id查询数据
	<T> T findById(Class<T> entityClass, String id);

	//修改数据
	void saveOrUpdate(Object obj);

	//删除数据
	void remove(Object obj);

	//根据条件查询
	<T> Long count(Class<T> entityClass, Query query);

	//分页查询
	Page<User2> findAllByPage(User2 user, Integer pageno, Integer pageSize);

}
