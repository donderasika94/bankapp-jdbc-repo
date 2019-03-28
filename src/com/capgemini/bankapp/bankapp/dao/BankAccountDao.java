package com.capgemini.bankapp.bankapp.dao;

import java.util.List;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountDao {
	
	public double getBalance(long accountId)throws BankAccountNotFoundException ;
	public void updateBalance(long accountId, double newBalance);
	public boolean deleteBankAccount(long accountId);
	public boolean addNewBankAccount(BankAccount account);
	public List<BankAccount> findAllbankAccounts();
	public BankAccount searchAccountDetails(long accountId);
	

}
