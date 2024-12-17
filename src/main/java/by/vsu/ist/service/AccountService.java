package by.vsu.ist.service;

import by.vsu.ist.domain.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountService {
	List<Account> findAll() throws SQLException;

	List<Account> findActive() throws SQLException;

	Optional<Account> findById(Long id) throws SQLException;

	Optional<Account> findByIdWithTransfers(Long id) throws SQLException;

	void save(Account account) throws SQLException;

	Optional<Account> delete(Long id) throws SQLException;
}
