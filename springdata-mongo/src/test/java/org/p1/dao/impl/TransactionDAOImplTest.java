package org.p1.dao.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.Account;
import org.p1.dao.ITransactionDAO;
import org.p1.dao.Transaction;
import org.p1.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

public class TransactionDAOImplTest extends BaseSpringTest {
	
	@Autowired
	private ITransactionDAO transactionDAO;
	
	@Before
	public void init() {
		Assert.notNull(transactionDAO);
		Query query = new Query();
		query.addCriteria(Criteria.where("loginName").regex(".*"));
		mongoTemplate.remove(query, User.class);
		
		query = new Query();
		query.addCriteria(Criteria.where("number").regex(".*"));
		mongoTemplate.remove(query, Account.class);
		
		query = new Query();
		query.addCriteria(Criteria.where("details").regex(".*"));
		mongoTemplate.remove(query, Transaction.class);
		
	}
	

	@Test
	public void testGetTransactonsByAccNum() {
		String acc1Num = "123456-7890";
		Account acc1 = new Account();
		acc1.setAccountType("Savings");
		acc1.setNumber(acc1Num);
		mongoTemplate.save(acc1);
		
		Account acc2 = new Account();
		acc2.setAccountType("Current");
		acc2.setNumber("0987654-321");
		mongoTemplate.save(acc2);
		
		
		Transaction tx1 = new Transaction();
		tx1.setDetails("1");
		tx1.setAccount(acc1);
		mongoTemplate.save(tx1);
		
		Transaction tx2 = new Transaction();
		tx2.setDetails("2");
		tx2.setAccount(acc2);
		mongoTemplate.save(tx2);
		
		Transaction tx3 = new Transaction();
		tx3.setDetails("3");
		tx3.setAccount(acc1);
		mongoTemplate.save(tx3);
		
		Transaction tx4 = new Transaction();
		tx4.setDetails("4");
		tx4.setAccount(acc2);
		mongoTemplate.save(tx4);
		
		Assert.isTrue(transactionDAO.getTransactionsByAccount(acc1Num).size() >= 2);
	}
	
	@Test
	public void testIncorrectAccNum() {
		Assert.isTrue(transactionDAO.getTransactionsByAccount("wrong").size() == 0);
	}
	
	
	@Test
	public void testGetTransactonsByLoginName() {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "pavan.mane@novopay.in";
		User user = new User();
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName("Mane");
		user.setLoginName(loginName);
		user.setDob(new Date());
		user.setBottles(UserDAOImplTest.getBottles());
		mongoTemplate.save(user);
		
		String acc1Num = "123456-7890";
		Account acc1 = new Account();
		acc1.setAccountType("Savings");
		acc1.setNumber(acc1Num);
		acc1.setUser(user);
		user.setBottles(UserDAOImplTest.getBottles());
		mongoTemplate.save(acc1);
		
		Account acc2 = new Account();
		acc2.setAccountType("Current");
		acc2.setNumber("0987654-321");
		acc2.setUser(user);
		mongoTemplate.save(acc2);
		
		
		Transaction tx1 = new Transaction();
		tx1.setDetails("1");
		tx1.setAccount(acc1);
		mongoTemplate.save(tx1);
		
		Transaction tx2 = new Transaction();
		tx2.setDetails("2");
		tx2.setAccount(acc2);
		mongoTemplate.save(tx2);
		
		Transaction tx3 = new Transaction();
		tx3.setDetails("3");
		tx3.setAccount(acc1);
		mongoTemplate.save(tx3);
		
		Transaction tx4 = new Transaction();
		tx4.setDetails("4");
		tx4.setAccount(acc2);
		mongoTemplate.save(tx4);
		
		Assert.isTrue(transactionDAO.getTransactionsByUser(loginName).size() >= 4);
	}
	
	
}
