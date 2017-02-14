package org.p1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.p1.controller.UserControllerTest;
import org.p1.dao.impl.BottleDAOImplTest;
import org.p1.dao.impl.TransactionDAOImplTest;
import org.p1.dao.impl.UserDAOImplTest;
import org.p1.dao.impl.UserRepositoryTest;
import org.p1.service.impl.UserServiceImplTest;

@RunWith(Suite.class)
@SuiteClasses({UserRepositoryTest.class, UserDAOImplTest.class, BottleDAOImplTest.class, TransactionDAOImplTest.class, UserServiceImplTest.class, UserControllerTest.class})
public class UserTestSuite  {

	@Test
	public void testSuite() {
		
	}
}
