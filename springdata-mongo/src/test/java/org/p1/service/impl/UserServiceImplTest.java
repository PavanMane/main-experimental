package org.p1.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.p1.dto.UserDTO;
import org.p1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

public class UserServiceImplTest extends BaseSpringTest {

	@MockBean
	private IUserDAO userDAO;

	@Autowired
	private IUserService userService;

	@BeforeClass
	public static  void onLoad() {
		System.out.println("**** Runnning UserServiceImpl test cases ****");
	}
	
	@Before
	public void init() {
		Assert.notNull(userService);
	}

	@Test
	public void save() throws Exception {
		String loginName = "blah_" + UUID.randomUUID();

		User user = new User();
		user.setLoginName(loginName);

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(loginName);

		// save test
		when(userDAO.save(any())).thenReturn(user);
		userService.saveUser(userDTO);
		verify(userDAO, times(1)).save(any());

	}

	@Test
	public void get() throws Exception {
		String loginName = "blah_" + UUID.randomUUID();
		User user = new User();
		user.setLoginName(loginName);

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(loginName);
		// get test
		when(userDAO.getByLoginName(loginName)).thenReturn(user);
		userDTO = userService.getUser(loginName);
		verify(userDAO, times(1)).getByLoginName(loginName);
		Assert.isTrue(loginName.equals(userDTO.getLoginName()));
	}

	@Test
	public void update() throws Exception {
		String loginName = "blah_" + UUID.randomUUID();

		User user = new User();
		user.setLoginName(loginName);

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(loginName);

		// update test
		String lastName = "Mane";
		user.setLastName(lastName);
		when(userDAO.getByLoginName(loginName)).thenReturn(user);
		when(userDAO.update(any())).thenReturn(user);
		userService.updateUser(userDTO);
		verify(userDAO, times(1)).getByLoginName(any());
		verify(userDAO, times(1)).update(any());
		Assert.isTrue(loginName.equals(user.getLoginName()));
		Assert.isTrue(lastName.equals(user.getLastName()));
	}

	@Test
	public void updateWithException() throws Exception {
		boolean exceptionThrown = false;
		String loginName = "blah_" + UUID.randomUUID();

		User user = new User();
		user.setLoginName(loginName);

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(loginName);

		// update test
		String lastName = "Mane";
		user.setLastName(lastName);
		when(userDAO.getByLoginName(loginName)).thenReturn(null);
		when(userDAO.update(any())).thenReturn(user);
		try {
			userService.updateUser(userDTO);
		} catch (Exception e) {
			exceptionThrown = true;
			System.out.println("Expected exception..... everything okay!!!");
			e.printStackTrace();
		}
		
		verify(userDAO, times(1)).getByLoginName(any());
		verify(userDAO, times(0)).update(any());
		
		Assert.isTrue(exceptionThrown);
	}

	@Test
	public void delete() throws Exception {
		String loginName = "blah_" + UUID.randomUUID();

		User user = new User();
		user.setLoginName(loginName);

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(loginName);

		// delete test
		when(userDAO.getByLoginName(loginName)).thenReturn(user);
		userService.deleteUser(loginName);
		verify(userDAO, times(1)).getByLoginName(loginName);
		verify(userDAO, times(1)).deleteByLoginName(loginName);
	}
}
