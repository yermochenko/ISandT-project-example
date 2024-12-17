package by.vsu.ist.repository;

import by.vsu.ist.domain.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends Repository<Account> {
	List<Account> readAll() throws SQLException;

	List<Account> readActive() throws SQLException;

	Optional<Account> readByNumber(String number) throws SQLException;
}
