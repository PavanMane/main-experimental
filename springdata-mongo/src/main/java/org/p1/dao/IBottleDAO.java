package org.p1.dao;

import java.util.List;

public interface IBottleDAO {

	void save(Bottle bottle);
	
	Bottle get(Bottle.Type type, String name);
	
	List<Bottle> find(String hasPattern);
	
	void delete(Bottle.Type type, String name);
	
}
