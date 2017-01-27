package org.p1.dao.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.Assert;

@EnableAutoConfiguration
public class UserDAOImplTest extends BaseSpringTest {

	@Autowired
	private IUserDAO userDAO;
	
	@Before
	public void init() {
		System.out.println("***** ----- Running UserDAOImplTest test cases .....");
		Assert.notNull(userDAO);
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
		userDAO.save(user);
		
		User user2 = new User();
		user2.setFirstName(firstName);
		user2.setMiddleName("Blah");
		user2.setLastName("Mane");
		user2.setLoginName("pamane@novopay.in");
		user2.setDob(new Date());
		userDAO.save(user2);
		
		
		Assert.isTrue(userDAO.findByFirstName(firstName).size() == 2);
		
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
			userDAO.save(user);
			
			User user2 = new User();
			user2.setFirstName(firstName);
			user2.setMiddleName("Blah");
			user2.setLastName("Mane");
			user2.setLoginName(loginName);
			user2.setDob(new Date());
			userDAO.save(user2);
		} catch (Exception e) {
			System.out.println("***** Expection exception: " + e.getMessage());
			isException = true;
		}
		userDAO.deleteByLoginName(loginName);
		Assert.isTrue(isException);
	}
}
