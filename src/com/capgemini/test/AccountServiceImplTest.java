package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import com.capgemini.beans.Account;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitialAmountException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountServiceImplTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	@Test(expected= com.capgemini.exception.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValueInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	
	@Test(expected= com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenInvalidAccountNumberIsPassed() throws InvalidAccountNumberException{
		accountService.depositAmount(-101, 100);
	}
	
	@Test
	public void whenValidInfoIsPassedAmountShouldBeDeposited() throws InvalidAccountNumberException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(200);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(400, accountService.depositAmount(101, 200));
	}
	
	@Test(expected= com.capgemini.exception.InsufficientBalanceException.class)
	public void whenInvalidAmountIsPassed() throws InvalidAccountNumberException, InsufficientBalanceException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(200);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(0, accountService.withdrawAmount(101, 500));
	}
	
	@Test
	public void whenValidInfoIsPassedAmountShouldBeWithdrawn() throws InvalidAccountNumberException, InsufficientBalanceException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(200);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(0, accountService.withdrawAmount(101, 200));
	}
}
