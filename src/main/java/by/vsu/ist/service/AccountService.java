package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.repository.AccountRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService extends BaseService {
	private AccountRepository accountRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<Account> findAll() throws SQLException {
		return accountRepository.readAll();
	}

	public Optional<Account> findById(Long id) throws SQLException {
		return accountRepository.read(id);
	}

	public void save(Account account) throws SQLException {
		if(account.getId() != null) {
			getTransactionManager().startTransaction();
			try {
				Account storedAccount = accountRepository.read(account.getId()).orElseThrow();
				storedAccount.setOwner(account.getOwner());
				storedAccount.setActive(account.isActive());
				accountRepository.update(storedAccount);
				getTransactionManager().commitTransaction();
			} catch(SQLException e) {
				getTransactionManager().rollbackTransaction();
				throw e;
			}
		} else {
			accountRepository.create(account);
		}
	}
}
