package org.p1.dao.impl;

import java.util.List;

import org.p1.dao.Bottle;
import org.p1.dao.Bottle.Type;
import org.p1.dao.IBottleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BottleDAOImpl implements IBottleDAO {
	
	@Autowired
	public MongoOperations mongoOperations;

	@Override
	public void save(Bottle bottle) {
		mongoOperations.save(bottle);
	}

	@Override
	public Bottle get(Type type, String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type).and("name").is(name));
		return mongoOperations.findOne(query, Bottle.class);
	}

	@Override
	public List<Bottle> find(String hasPattern) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(hasPattern));
		return mongoOperations.find(query, Bottle.class);
	}

	@Override
	public void delete(Type type, String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type).and("name").is(name));
		mongoOperations.findAndRemove(query, Bottle.class);
	}

}
