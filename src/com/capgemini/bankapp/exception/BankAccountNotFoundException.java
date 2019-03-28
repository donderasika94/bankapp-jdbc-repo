package com.capgemini.bankapp.exception;

public class BankAccountNotFoundException extends Exception {
public BankAccountNotFoundException() {
		
	}
	
	public BankAccountNotFoundException(String message) {
		super(message);
	}


}
