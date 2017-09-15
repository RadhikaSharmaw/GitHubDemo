package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitialAmountException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService{
	AccountRepository accountRepository;
	public AccountServiceImpl(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
	}
	public Account createAccount(int accountNumber, int amount) throws InsufficientInitialAmountException{
		if(amount<500||accountNumber<0||amount<0){
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		if(accountRepository.save(account)){
			return account;
		}
		return account;
	}
	
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException{
		if(accountNumber<0){
			throw new InvalidAccountNumberException();
		}
		Account account = accountRepository.searchAccount(accountNumber);
		account.setAmount(account.getAmount()+amount);
		return account.getAmount();
	}
	
	public int withdrawAmount(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException{
		if(accountNumber<0){
			throw new InvalidAccountNumberException();
		}
		Account account = accountRepository.searchAccount(accountNumber);
		if(amount>account.getAmount()){
			throw new InsufficientBalanceException();
		}
		account.setAmount(account.getAmount()-amount);
		return account.getAmount();
	}
	
}
