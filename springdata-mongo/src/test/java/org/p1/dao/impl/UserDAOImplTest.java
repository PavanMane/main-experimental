package org.p1.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.Bottle;
import org.p1.dao.Bottle.Type;
import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class UserDAOImplTest extends BaseSpringTest {

	@Autowired
	private IUserDAO userDAO;

	@BeforeClass
	public static void onLoad() {
		System.out.println("***** ----- Running UserDAOImplTest test cases .....");
	}
	
	@Before
	public void init() {
		Assert.notNull(userDAO);
		mongoTemplate.dropCollection(User.class);
	}
	
	@Test
	public void test() {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "pavan.mane@novopay.in";
		User user = new User();
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName("Mane");
		user.setLoginName(loginName);
		user.setDob(new Date());
		user.setBottles(getBottles());
		userDAO.save(user);
		
		User user2 = new User();
		user2.setFirstName(firstName);
		user2.setMiddleName("Blah");
		user2.setLastName("Mane");
		user2.setLoginName("pamane@novopay.in");
		user2.setDob(new Date());
		user2.setBottles(getBottles());
		userDAO.save(user2);
		
		
		Assert.isTrue(userDAO.findByFirstName(firstName).size() == 2);
		
		Assert.isTrue(userDAO.findOneByLoginName(loginName).getBottles().size() == 2);
		
		Assert.notNull(userDAO.findOneByLoginName(loginName).getMiddleName().equals(middleName));
		
		Assert.notNull(userDAO.getByLoginName(loginName).getMiddleName().equals(middleName));
		
		
		userDAO.delete(user);
		userDAO.delete(user2);
	}
	
	@Test
	public void testDuplicate() {
		boolean isException = false;
		String loginName = "pavan.mane@novopay.in";
		try {
			String firstName = "Pavan";
			String middleName = "Vasant";
			User user = new User();
			user.setFirstName(firstName);
			user.setMiddleName(middleName);
			user.setLastName("Mane");
			user.setLoginName(loginName);
			user.setDob(new Date());
			user.setBottles(getBottles());
			userDAO.save(user);
			
			User user2 = new User();
			user2.setFirstName(firstName);
			user2.setMiddleName("Blah");
			user2.setLastName("Mane");
			user2.setLoginName(loginName);
			user2.setDob(new Date());
			user.setBottles(getBottles());
			userDAO.save(user2);
		} catch (Exception e) {
			System.out.println("***** Expection exception: " + e.getMessage());
			isException = true;
		}
		userDAO.deleteByLoginName(loginName);
		Assert.isTrue(isException);
	}
	
	private List<Bottle> getBottles() {
		List<Bottle> bottles = new ArrayList<>();
		String pattern = "CamelBack";
		
		Bottle bottle2 = new Bottle();
		String name2 = "Test" + pattern + UUID.randomUUID();
		bottle2.setName(name2);
		bottle2.setType(Type.SIPPER);
		bottles.add(bottle2);
		
		String name = pattern + UUID.randomUUID();
		Bottle bottle1 = new Bottle();
		bottle1.setName(name);
		bottle1.setType(Type.SIPPER);
		bottles.add(bottle1);
		
		return bottles;
	}
}
