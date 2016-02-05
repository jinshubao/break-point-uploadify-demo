package com.jean.mongodb.dao.impl;

import org.springframework.stereotype.Repository;

import com.jean.mongodb.dao.PersonDao;
import com.jean.mongodb.entity.Person;

@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao {

}
