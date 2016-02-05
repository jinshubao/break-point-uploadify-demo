package com.jean.mongodb.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.jean.mongodb.dao.BaseDao;
import com.mongodb.WriteResult;

public class BaseDaoImpl<T extends Serializable> implements BaseDao<T> {

	protected Class<T> type = null;

	@Autowired
	private MongoTemplate mongoTemplate;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<T> findAll() {
		return mongoTemplate.find(new Query(), type);
	}

	@Override
	public WriteResult upsert(String id, Update update) {
		return mongoTemplate.upsert(new Query(Criteria.where("id").is(id)), update, type);
	}

	@Override
	public WriteResult updateFirst(String id, Update update) {
		return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), update, type);
	}

	@Override
	public List<T> findByRegex(String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Criteria criteria = new Criteria("name").regex(pattern.toString());
		return mongoTemplate.find(new Query(criteria), type);

	}

	@Override
	public T findOne(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), type);
	}

	@Override
	public void insert(T entity) {
		mongoTemplate.insert(entity);
	}

	@Override
	public void removeAll() {
		List<T> list = this.findAll();
		if (list != null) {
			for (T t : list) {
				mongoTemplate.remove(t);
			}
		}
	}

	@Override
	public WriteResult removeOne(String id) {
		T t = findOne(id);
		if (t != null) {
			return mongoTemplate.remove(t);
		}
		return null;
	}
}
