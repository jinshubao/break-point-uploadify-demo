package com.jean.sample.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public interface BaseDao<T extends Serializable> {

	public void insert(T entity);

	public T findOne(String id);

	public List<T> findAll();

	public List<T> findByRegex(String regex);

	public WriteResult removeOne(String id);

	public void removeAll();

	public WriteResult upsert(String id, Update update);

	public WriteResult updateFirst(String id, Update update);

}
