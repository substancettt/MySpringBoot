package com.wellshang.data.dao.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.wellshang.data.dao.IMongoDAO;

@ComponentScan
@Repository("genericDao")
public class MongoDAOImpl<T> implements IMongoDAO<T> {

	private Class<T> entityClass;
	public static final Logger logger = LoggerFactory
			.getLogger(MongoDAOImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public void test() {
		Set<String> colls = this.mongoTemplate.getCollectionNames();
		for (String coll : colls) {
			logger.info("CollectionName: " + coll);
		}
		DB db = this.mongoTemplate.getDb();
		logger.info("db: " + db.toString());
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(entityClass)) {
			this.mongoTemplate.createCollection(entityClass);
		}
	}

	@Override
	public List<T> load(Criteria criteria, int skip, int limit) {
		Query query;
		if (criteria == null) {
			query = new Query();
		} else {
			query = new Query(criteria);
		}
		query.with(new Sort(new Order(Direction.ASC, "_id")));
		query.skip(skip).limit(limit);
		return this.mongoTemplate.find(query, entityClass);
	}

	@Override
	public T find(String id) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		return this.mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public T find(String key, String value) {
		Query query = new Query(Criteria.where(key).is(value));
		return this.mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public void insert(T entity) {
		this.mongoTemplate.insert(entity);
	}

	@Override
	public void upsert(Query query, Update update) {
	    this.mongoTemplate.upsert(query, update, entityClass);
	}

	@Override
	public void delete(T entity) {
		this.mongoTemplate.remove(entity);
	}

}