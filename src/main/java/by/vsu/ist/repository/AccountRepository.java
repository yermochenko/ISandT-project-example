package by.vsu.ist.repository;

import by.vsu.ist.domain.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
	Long create(Account account) throws SQLException;

	List<Account> readAll() throws SQLException;

	List<Account> readActive() throws SQLException;

	Optional<Account> readByNumber(String number) throws SQLException;

	Optional<Account> read(Long id) throws SQLException;

	void update(Account account) throws SQLException;

	void delete(Long id) throws SQLException;
}
