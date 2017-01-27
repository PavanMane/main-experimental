package org.p1.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.p1.config.MongoDBConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoDBConfiguration.class)
@EnableAutoConfiguration
@ComponentScan(basePackages={"org.p1.config", "org.p1.dao.impl"})
public class BaseSpringTest  {

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	@Test
	public void testing() {
		System.out.println("***** Executing Test cases on DB: " + mongoTemplate.getDb().getName() + ", *****");
		Assert.notNull(mongoTemplate);
		
	}

}
