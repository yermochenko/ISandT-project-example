package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.repository.AccountRepository;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
	private AccountRepository accountRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<Account> findAll() throws SQLException {
		return accountRepository.readAll();
	}
}
