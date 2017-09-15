package com.capgemini.repository;

import com.capgemini.beans.Account;

public interface AccountRepository {
	public boolean save(Account account);
	public Account searchAccount(int accountNumber);
}
