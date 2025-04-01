package by.vsu.ist.repository.jdbc;

import by.vsu.ist.domain.Account;
import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AccountRepositoryImpl extends BaseRepository<Account> implements AccountRepository {
	public AccountRepositoryImpl() {
		super(
			"SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\" WHERE \"id\" = ?",
			"INSERT INTO \"account\"(\"number\", \"owner\") VALUES (?, ?)",
			"UPDATE \"account\" SET \"number\" = ?, \"owner\" = ?, \"balance\" = ?, \"active\" = ? WHERE \"id\" = ?",
			"DELETE FROM \"account\" WHERE \"id\" = ?"
		);
	}

	@Override
	public List<Account> readAll() throws RepositoryException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\"";
		List<Account> accounts = new ArrayList<>();
		read(sql, null, accounts::add);
		return accounts;
	}

	@Override
	public List<Account> readActive() throws RepositoryException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\" WHERE \"active\" = TRUE";
		List<Account> accounts = new ArrayList<>();
		read(sql, null, accounts::add);
		return accounts;
	}

	@Override
	public Optional<Account> readByNumber(String number) throws RepositoryException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\" WHERE \"number\" = ?";
		AtomicReference<Account> account = new AtomicReference<>();
		read(sql, statement -> statement.setString(1, number), account::set);
		return Optional.ofNullable(account.get());
	}

	@Override
	protected Account buildFromResultSet(ResultSet resultSet) throws SQLException {
		Account account = new Account();
		account.setId(resultSet.getLong("id"));
		account.setNumber(resultSet.getString("number"));
		account.setOwner(resultSet.getString("owner"));
		account.setBalance(resultSet.getLong("balance"));
		account.setActive(resultSet.getBoolean("active"));
		return account;
	}

	@Override
	protected void fillInsertPreparedStatement(PreparedStatement statement, Account account) throws SQLException {
		statement.setString(1, account.getNumber());
		statement.setString(2, account.getOwner());
	}

	@Override
	protected void fillUpdatePreparedStatement(PreparedStatement statement, Account account) throws SQLException {
		statement.setString(1, account.getNumber());
		statement.setString(2, account.getOwner());
		statement.setLong(3, account.getBalance());
		statement.setBoolean(4, account.isActive());
		statement.setLong(5, account.getId());
	}
}
