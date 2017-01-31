package org.p1.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.p1.dao.Account;
import org.p1.dao.ITransactionDAO;
import org.p1.dao.Transaction;
import org.p1.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class TransactionDAOImpl implements ITransactionDAO {
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public List<Transaction> getTransactionsByAccount(String number) {
		Account account = getAccount(number);
		if (account != null) {
			Query query = new Query();
			query.addCriteria(Criteria.where("account.$id").is(new ObjectId(account.getId())));
			return mongoOperations.find(query, Transaction.class);
		} else {
			return Collections.<Transaction>emptyList();
		}
		
	}

	@Override
	public List<Transaction> getTransactionsByUser(String loginName) {
		List<Transaction> transactions = Collections.<Transaction>emptyList();
		List<Account> userAccounts = getUserAccounts(getUser(loginName));
		if(!CollectionUtils.isEmpty(userAccounts)) {
			Query query = new Query();
			List<ObjectId> accObjIds= new ArrayList<ObjectId>(userAccounts.size());
			for (Account account : userAccounts) {
				accObjIds.add(new ObjectId(account.getId()));
			}
			query.addCriteria(Criteria.where("account.$id").in(accObjIds));
			transactions =  mongoOperations.find(query, Transaction.class);
		} 
		return transactions;
	}

	@Override
	public void deleteAccountTransactions(String accountNumber) {
		// TODO Auto-generated method stub
		
	}
	
	private Account getAccount(String number) {
		Query query = new Query();
		query.addCriteria(Criteria.where("number").is(number));
		return mongoOperations.findOne(query, Account.class);
	}
	
	private User getUser(String loginName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("loginName").is(loginName));
		return mongoOperations.findOne(query, User.class);
	}
	
	private List<Account> getUserAccounts(User user) {
		if (user != null) {
			Query query = new Query();
			query.addCriteria(Criteria.where("user.$id").is(new ObjectId(user.getId())));
			return mongoOperations.find(query, Account.class);
		} else {
			return Collections.<Account>emptyList();
		}
	}
	

}
