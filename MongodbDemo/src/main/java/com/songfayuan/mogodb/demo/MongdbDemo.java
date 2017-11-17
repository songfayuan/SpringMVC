/**
 * 项目名称：MongoDB
 * 项目包名：com.songfayuan.mogodb.demo
 * 创建时间：2017年7月2日下午11:51:30
 * 创建者：Administrator-宋发元
 * 创建地点：杭州钜元网络科技有限公司
 */
package com.songfayuan.mogodb.demo;
import java.net.UnknownHostException;

import com.mongodb.*;

/**
 * 描述： MongoDB入门Java操作
 * @author songfayuan
 * 2017年7月2日下午11:51:30
 */
public class MongdbDemo {   
	  
    private static Mongo mongo = null;  
    private static DBCollection dbCollection = null;  
  
    /** 
     * 初始化连接Mongdb,此处使用默认连接(本地地址,默认端口) 
     * 
     * @return mongo 
     * @throws MongoException 
     * @throws UnknownHostException 
     */  
    private static Mongo initMongo() throws UnknownHostException, MongoException {  
        System.out.println("连接Mongo");  
//        mongo = new Mongo();  
        mongo = new MongoClient( "120.24.84.77" , 27017 );  
        //查询所有的Database  
        for (String name : mongo.getDatabaseNames()) {  
            System.out.println("dbName: " + name);  
        }  
        System.out.println(mongo);
        return mongo;  
    }  
  
    /** 
     * 根据集合名名称获取集合对象 
     * 
     * @param name 
     * @return dbCollection 
     */  
    private static DBCollection getDBCollection(String name) {  
        System.out.println("根据集合名名称获取集合对象");  
        DB db = mongo.getDB("FirstDB");//有此数据库就返回,没有就创建一个新的数据库  
        System.out.println(db.getStats());  
        //查询所有的聚集集合  
        for (String n : db.getCollectionNames()) {  
            System.out.println("collectionName: " + n);  
        }  
        dbCollection = db.getCollection(name);//有集合就返回,无就根据名字创建一个新的集合  
        return dbCollection;  
    }  
  
    /** 
     * 插入 
     */  
    private static void insert() {  
        System.out.println("插入数据:");  
        for (int i = 0; i < 2; i++) {  
            BasicDBObject basicDBObject = new BasicDBObject();  
            basicDBObject.put("name", "宋发元"+i);  
            basicDBObject.put("age", 24);  
            basicDBObject.put("sex", "man");  
            dbCollection.save(basicDBObject);//存入值  
  
            BasicDBObject basicDBObject_2 = new BasicDBObject();  
            basicDBObject_2.put("name", "张晨卓"+i);  
            basicDBObject_2.put("age", 26);  
            basicDBObject_2.append("friend_1", basicDBObject);  
            dbCollection.save(basicDBObject_2);//存入值  
        }  
        System.out.println("插入数据成功");  
  
    }  
  
    /** 
     * 更新指定文档指定字段的值 
     */  
    private static void update() {  
        System.out.println("更新数据:");  
        //构建更新文档的条件  
        DBObject query = new BasicDBObject();  
        query.put("name", "宋发元0");  
        //构建需要更新的字段的值  
        DBObject updateValue = new BasicDBObject();  
        updateValue.put("age", 100);  
  
        DBObject updateSetValue = new BasicDBObject("$set",updateValue);  
        dbCollection.update(query, updateSetValue);  
  
        System.out.println("更新数据成功");  
    }  
  
    /** 
     * 删除 
     */  
    private static void delete() {  
        System.out.println("删除数据:");  
        DBObject query = new BasicDBObject();  
        query.put("name", "张晨卓1");  
        dbCollection.remove(query);  
        System.out.println("删除数据成功");  
    }  
  
    /** 
     * 条件查询() 
     */  
    private static void select() {  
        System.out.println("条件查询:");  
        BasicDBObject basicDBObject = new BasicDBObject();  
        basicDBObject.put("age", 24);  
        DBCursor cur1 = dbCollection.find(basicDBObject);  
        System.out.println(cur1.getCollection());
        System.out.println(cur1.next());  
        DBObject dbObject  = cur1.next();  
        System.out.println(dbObject.get("name"));  
  
        //条件查询一个,不输入条件之间findOne()将返回集合中第一个文档  
        DBObject query_2 = new BasicDBObject();  
        query_2.put("age", 24);  
        DBObject dbObject_2 = dbCollection.findOne(query_2);  
        if (null!=dbObject_2) {  
            System.out.println("只查询一个:" + dbObject_2.get("name") + ";" + dbObject_2.get("sex"));  
        }  
    }  
  
    /** 
     * 查询当前集合下所有数据 
     */  
    private static void selectAll() {  
        System.out.println("查询所有数据:");  
        DBCursor cur = dbCollection.find();  
        while (cur.hasNext()) {  
            System.out.println("while=" + cur.next());  
        }  
    }  
  
    public static void main(String[] args) throws UnknownHostException, MongoException {  
        initMongo();  
        getDBCollection("songfayuan_test");  
        insert();  
        update();  
        select();  
        selectAll();  
        delete();  
    }  
}  
