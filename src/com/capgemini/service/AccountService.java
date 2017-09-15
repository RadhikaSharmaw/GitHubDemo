package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitialAmountException;
import com.capgemini.exception.InvalidAccountNumberException;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialAmountException;
	int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException;
	int withdrawAmount(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException;
}
