package cn.tf.mongodb.demo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cn.tf.mongodb.demo.bean.User2;
import cn.tf.mongodb.demo.dao.DemoDao;
import cn.tf.mongodb.demo.util.Page;

@Repository
public class DemoDaoImpl implements DemoDao{

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	/**
	 * 添加对象
	 * 
	 * @param obj
	 *            要添加的Mongo对象
	 */
	@Override
	public void add(Object obj) {
		this.mongoTemplate.insert(obj);
	}

	/**
	 * 根据类获取全部的对象列表
	 * 
	 * @param entityClass
	 *            返回类型
	 * @return List<T> 返回对象列表
	 */
	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return this.mongoTemplate.findAll(entityClass);
	}

	/**
	 * 根据主键id返回对象
	 * 
	 * @param id
	 *            唯一标识
	 * @return T 对象
	 */
	@Override
	public <T> T findById(Class<T> entityClass, String id) {
		return this.mongoTemplate.findById(id, entityClass);
	}

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的Mongo对象
	 */
	@Override
	public void saveOrUpdate(Object obj) {
		this.mongoTemplate.save(obj);
	}

	/**
	 * 删除一个对象
	 * 
	 * @param obj
	 *            要删除的Mongo对象
	 */
	@Override
	public void remove(Object obj) {
		this.mongoTemplate.remove(obj);
	}

	/**
	 * 
	 * @param entityClass
	 *            查询对象
	 * @param query
	 *            查询条件
	 * @return
	 */
	@Override
	public <T> Long count(Class<T> entityClass, Query query) {
		return this.mongoTemplate.count(query, entityClass);
	}

	//分页查询
	@Override
	public Page<User2> findAllByPage(User2 user, Integer pageno, Integer pageSize) {
		Integer offset = pageno > 0 ? (pageno-1) * pageSize : 0;
		
		List<User2> list = new ArrayList<>();
		Query query = new Query();
		if (null != user.getUsername()) {
			query.addCriteria(Criteria.where("username").regex( ".*?" + user.getUsername() + ".*" ));
		}
		if (null != user.getAge()) {
			query.addCriteria(Criteria.where("age").is(user.getAge()));
		}
		
		long count = this.mongoTemplate.count(query, User2.class);
		if (count > 0) {
			query.skip(offset);  //skip相当于从那条记录开始
			query.limit(pageSize);  //从skip开始，取多少条记录
			query.with(new Sort(Direction.DESC, "createTime"));
			list = this.mongoTemplate.find(query, User2.class);
		}
		Page<User2> pageList = new Page<>(pageno, pageSize, (int)count);
		pageList.setData(list);
		System.out.println("共{"+pageList.getTotal()+"}页，每页{"+pageSize+"}条，当前是第{"+pageno+"}页");
		return pageList;
	}

}
