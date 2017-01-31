package org.p1.dao;

import java.util.List;

public interface ITransactionDAO {
	
	List<Transaction> getTransactionsByAccount(String accountNumber);
	
	List<Transaction> getTransactionsByUser(String loginName);
	
	void deleteAccountTransactions(String accountNumber);
}
