package org.p1.base;

import static org.springframework.util.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



// p1: use SpringBootTest and ensure Application.java is in root of the package structure. Else Auto configuration of beans would not work.
// if the Application.java is not in root package use @ContextConfiguration, @EnableAutoConfiguration, @ComponentScan
// @ContextConfiguration(classes = MongoDBConfiguration.class)
// @EnableAutoConfiguration
// @ComponentScan(basePackages={"org.p1.config", "org.p1.dao.impl"})


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(value="classpath:application-test.properties")
public class BaseSpringTest  {

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	@Test
	public void testing() {
		System.out.println("***** Executing Test cases on DB: " + mongoTemplate.getDb().getName() + ", *****");
		notNull(mongoTemplate);
		
	}

}
