package com.wellshang.data.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IMongoDAO<T> {
	public abstract void setEntityClass(Class<T> entityClass);
	public abstract void test();
	public abstract void createCollection();
	public abstract List<T> load(Criteria criteria, int skip, int limit);
	public abstract T find(String id);
	public abstract T find(String key, String value);
	public abstract void insert(T entity);
	public abstract void upsert(Query query, Update update);
	public abstract void delete(T entity);
}