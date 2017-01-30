package org.p1.dao.impl;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.Bottle;
import org.p1.dao.Bottle.Type;
import org.p1.dao.IBottleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class BottleDAOImplTest extends BaseSpringTest {

	@Autowired
	private IBottleDAO bottleDAO;
	
	@Before
	public void init() {
		Assert.notNull(bottleDAO);
	}
	
	@Test
	public void saveGetTest() {
		String name = "CamelBack";
		Bottle bottle1 = new Bottle();
		bottle1.setName(name);
		bottle1.setType(Type.SIPPER);
		
		//save
		bottleDAO.save(bottle1);
		
		//get
		Assert.notNull(bottleDAO.get(Type.SIPPER, name));
		bottleDAO.delete(Type.SIPPER, name);
		Assert.isNull(bottleDAO.get(Type.SIPPER, name));
	}
	
	@Test
	public void saveDuplicateGetTest() {
		String name = "CamelBack" + UUID.randomUUID();
		Bottle bottle1 = new Bottle();
		bottle1.setName(name);
		bottle1.setType(Type.SIPPER);
		
		//save
		bottleDAO.save(bottle1);
		
		
		// duplicate
		Bottle bottle2 = new Bottle();
		bottle2.setName(name);
		bottle2.setType(Type.SIPPER);
		
		boolean exceptionThrown = false;
		//save should fail
		try {
			bottleDAO.save(bottle2);
		} catch (Exception e) {
			exceptionThrown = true;
			System.out.println("Expected Exception... all okay!!! ");
			e.printStackTrace();
		}
		Assert.isTrue(exceptionThrown);
		
		// find
		Assert.isTrue(bottleDAO.find(name).size() == 1);
		
		bottleDAO.delete(Type.SIPPER, name);
		Assert.isNull(bottleDAO.get(Type.SIPPER, name));
	}
	
	@Test
	public void find() {
		String pattern = "CamelBack";
		
		Bottle bottle2 = new Bottle();
		String name2 = "Test" + pattern + UUID.randomUUID();
		bottle2.setName(name2);
		bottle2.setType(Type.SIPPER);
		
		// save 2
		bottleDAO.save(bottle2);
		
		String name = pattern + UUID.randomUUID();
		Bottle bottle1 = new Bottle();
		bottle1.setName(name);
		bottle1.setType(Type.SIPPER);
		
		//save 1
		bottleDAO.save(bottle1);
		
		// find
		Assert.isTrue(bottleDAO.find(pattern).size() >= 2);
		
		bottleDAO.delete(Type.SIPPER, name);
		bottleDAO.delete(Type.SIPPER, name2);
	}
}
