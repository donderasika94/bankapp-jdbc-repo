package com.capgemini.bankapp.service.impl;

import java.util.List;
import org.apache.log4j.*;


import com.capgemini.bankapp.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountServiceImpl implements BankAccountService {
	
	static final Logger logger = Logger.getLogger(BankAccountServiceImpl.class);

	private BankAccountDao bankAccountdao;

	public BankAccountServiceImpl() {
		bankAccountdao = new BankAccountDaoImpl();
	}

	@Override
	public double checkBalance(long accountId) throws BankAccountNotFoundException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance >= 0)
			return balance;
		throw new BankAccountNotFoundException("Bank account doesnt exist");
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance < 0)
			throw new BankAccountNotFoundException("BankAccount doesnt exist");
		else if(balance - amount >= 0) {
			balance = balance - amount;
			bankAccountdao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		}else 
			throw new LowBalanceException("You dont have sufficient fund");
	}

	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance < 0)
			throw new BankAccountNotFoundException("BankAccount doesnt exist");
		balance = balance + amount;
		bankAccountdao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException {
		boolean result = bankAccountdao.deleteBankAccount(accountId);
		if(result) {
			DbUtil.commit();
			return result;
		}
		throw new BankAccountNotFoundException("BanklAccount doesn't exist");
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result =  bankAccountdao.addNewBankAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountdao.findAllbankAccounts();
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException, BankAccountNotFoundException {
		try {
			double newBalance = withdrawForFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			DbUtil.commit();
			return newBalance;
		}
		catch(LowBalanceException | BankAccountNotFoundException e) {
		   logger.error("Exception ",e);
		   DbUtil.rollback();
		   throw e;
		}
	
	}

	private double withdrawForFundTransfer(long accountId, double amount)throws BankAccountNotFoundException, LowBalanceException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance < 0)
			throw new BankAccountNotFoundException("BankAccount doesnt exist");
		else if(balance - amount >= 0) {
			balance = balance - amount;
			bankAccountdao.updateBalance(accountId, balance);
			return balance;
		}else 
			throw new LowBalanceException("You dont have sufficient fund");
	}

	@Override
	public BankAccount searchAccountDetails(long accountId) throws BankAccountNotFoundException 	{
		BankAccount account = bankAccountdao.searchAccountDetails(accountId);
		if(account != null)
			return account;
		throw new BankAccountNotFoundException("BankAccount doesn't exist");
	}
	
	

}
