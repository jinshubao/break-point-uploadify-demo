package com.jean.sample.dao.impl;

import org.springframework.stereotype.Repository;

import com.jean.sample.dao.PersonDao;
import com.jean.sample.entity.Person;

@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao {

}
