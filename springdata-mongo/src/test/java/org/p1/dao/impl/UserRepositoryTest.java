package org.p1.dao.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.User;
import org.p1.dao.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class UserRepositoryTest extends BaseSpringTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void init() {
		System.out.println("***** ----- Running UserRepository test cases .....");
		Assert.assertNotNull(userRepository);
	}
	
	
	@Test
	public void save() {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "pavan.mane@novopay.in";
		User user = new User();
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName("Mane");
		user.setLoginName(loginName);
		user.setDob(new Date());
		userRepository.save(user);
		
		User user2 = new User();
		user2.setFirstName(firstName);
		user2.setMiddleName("Blah");
		user2.setLastName("Mane");
		user2.setLoginName("pamane@novopay.in");
		user2.setDob(new Date());
		userRepository.save(user2);
		
		
		Assert.assertTrue(userRepository.findByFirstName(firstName).size() == 2);
		
		Assert.assertNotNull(userRepository.findOneByLoginName(loginName).getMiddleName().equals(middleName));
		
		Assert.assertNotNull(userRepository.getByLoginName(loginName).getMiddleName().equals(middleName));
		
		userRepository.delete(user);
		userRepository.delete(user2);
	}
	
}
