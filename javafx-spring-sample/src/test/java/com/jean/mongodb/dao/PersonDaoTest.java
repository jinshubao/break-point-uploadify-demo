package com.jean.mongodb.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jean.sample.dao.PersonDao;
import com.jean.sample.entity.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class PersonDaoTest {

	@Autowired
	PersonDao personDao;

	@Test
	public void testInsert() {
		for (int i = 0; i < 10; i++) {
			personDao.insert(new Person("zhangsan_" + i, 10 + i));
		}
	}

	@Test
	public void testFindAll() {
		List<Person> persons = personDao.findAll();
		for (Person person : persons) {
			System.out.println(person);
		}
	}

	@Test
	public void testFindOne() {
		Person person = personDao.findOne("56a1cbcaab522c0c98ac2ef8");
		System.out.println(person);
	}

	@Test
	public void testFindByRegex() {
	}

	@Test
	public void testRemoveOne() {
		personDao.removeOne("id56a1cbcaab522c0c98ac2ef8");
		List<Person> persons = personDao.findAll();
		for (Person person : persons) {
			System.out.println(person);
		}
	}

	@Test
	public void testRemoveAll() {
		personDao.removeAll();
		List<Person> persons = personDao.findAll();
		for (Person person : persons) {
			System.out.println(person);
		}
	}

	@Test
	public void testUpsert() {
		personDao.upsert("56a1cbcaab522c0c98ac2ef8", new Update().set("name", "sunwukong"));
	}

	@Test
	public void testUpdateFirst() {
		personDao.updateFirst("56a1cbcaab522c0c98ac2ef8", new Update().set("age", "100"));
	}

}
