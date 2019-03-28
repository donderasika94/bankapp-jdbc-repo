package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {

	public static void main(String[] args) throws BankAccountNotFoundException {
		int choice;
		String accountHolderName;
		String accountType;
		double accountBalance;
		long accountId;
		double amount;
		long fromAccount;
		long toAccount;
		BankAccountService bankService = new BankAccountServiceImpl();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("1.Open new Bank Account\n2.Cash Withdraw\n3.Cash Deposit\n4.Fund Transfer");
				System.out.println("5.Delete Bank Account \n6.Display the details of all Bank Accounts");
				System.out.println("7.Fetch your Acoount Details\n8.Check Account Balance\n9.Exit \n");

				System.out.println("Please Enter Your Choice:");
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
				case 1:
					System.out.println("Enter account holder name: ");
					accountHolderName = reader.readLine();
					System.out.println("Enter account type: ");
					accountType = reader.readLine();
					System.out.println("Enter account balance: ");
					accountBalance = Double.parseDouble(reader.readLine());
					BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);
					if (bankService.addNewBankAccount(account))
						System.out.println("Account created successfully...\n");
					else
						System.out.println("failed to create new account...\n");
					break;

				case 2:
					System.out.println("Enter BankAccount Id:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Your Current balance:" + bankService.checkBalance(accountId));
					System.out.println("Enter Amount for Withdraw:");
					amount = Double.parseDouble(reader.readLine());
					try {
						accountBalance = bankService.withdraw(accountId, amount);

					} catch (LowBalanceException e) {
						e.printStackTrace();
					}
					System.out.println("After withdraw your Balance:" + bankService.checkBalance(accountId));
					break;

				case 3:
					System.out.println("Enter BankAccount Id");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Amount to be credited:");
					amount = Double.parseDouble(reader.readLine());
					System.out.println("Balance after deposit:" + bankService.deposit(accountId, amount));

					break;

				case 4:
					System.out.println("Enter account Id for debit amount:");
					fromAccount = Long.parseLong(reader.readLine());
					System.out.println("Enter account Id for credit amount:");
					toAccount = Long.parseLong(reader.readLine());
					System.out.println("Enter amount to transfer funds:");
					amount = Double.parseDouble(reader.readLine());
					try {
						accountBalance = bankService.fundTransfer(fromAccount, toAccount, amount);
					} catch (LowBalanceException e) {
						e.printStackTrace();
					}
					System.out.println("Balance after Debit:" + bankService.checkBalance(fromAccount));
					System.out.println("Balance after Credit:" + bankService.checkBalance(toAccount));

					break;
					
				case 5:
					System.out.println("Enter account Id to delete Bank Account:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Account Deleted"+bankService.deleteBankAccount(accountId));
					
					break;
					
				case 6:
				System.out.println("BankAccount Details:"+bankService.findAllBankAccounts());
					
					break;
					
				case 7:
					System.out.println("Enter account Id:");
					accountId=Long.parseLong(reader.readLine());
					System.out.println("Account Details:"+bankService.searchAccountDetails(accountId));
					
					break;
					
				case 8:
					System.out.println("Enter account Id:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Accont Balance:"+bankService.checkBalance(accountId));
					
					break;

				case 9:
					System.out.println("Thank You for banking with us.");
					System.exit(0);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}


