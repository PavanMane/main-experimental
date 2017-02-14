package org.p1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "org.p1.dao.impl")
public class MongoDBConfiguration extends AbstractMongoConfiguration {

	@Autowired
	private MongoDBCredentials mongoDBCredentials;

	@Override
	protected String getDatabaseName() {
		return mongoDBCredentials.getDatabaseName();
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(mongoDBCredentials.getHost(), Integer.parseInt(mongoDBCredentials.getPort()));
	}

	@Override
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}

}
