package by.vsu.ist.repository.jdbc;

import by.vsu.ist.domain.Account;
import by.vsu.ist.repository.AccountRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccountRepositoryImpl extends BaseRepository implements AccountRepository {
	@Override
	public Long create(Account account) throws SQLException {
		String sql = "INSERT INTO \"account\"(\"number\", \"owner\") VALUES (?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, account.getNumber());
			statement.setString(2, account.getOwner());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getLong(1);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public List<Account> readAll() throws SQLException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Account> accounts = new ArrayList<>();
			while(resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setNumber(resultSet.getString("number"));
				account.setOwner(resultSet.getString("owner"));
				account.setBalance(resultSet.getLong("balance"));
				account.setActive(resultSet.getBoolean("active"));
				accounts.add(account);
			}
			return accounts;
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public List<Account> readActive() throws SQLException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\" WHERE \"active\" = TRUE";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Account> accounts = new ArrayList<>();
			while(resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setNumber(resultSet.getString("number"));
				account.setOwner(resultSet.getString("owner"));
				account.setBalance(resultSet.getLong("balance"));
				account.setActive(resultSet.getBoolean("active"));
				accounts.add(account);
			}
			return accounts;
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public Optional<Account> readByNumber(String number) throws SQLException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\" WHERE \"number\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, number);
			resultSet = statement.executeQuery();
			Account account = null;
			if(resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setNumber(resultSet.getString("number"));
				account.setOwner(resultSet.getString("owner"));
				account.setBalance(resultSet.getLong("balance"));
				account.setActive(resultSet.getBoolean("active"));
			}
			return Optional.ofNullable(account);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public Optional<Account> read(Long id) throws SQLException {
		String sql = "SELECT \"id\", \"number\", \"owner\", \"balance\", \"active\" FROM \"account\" WHERE \"id\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Account account = null;
			if(resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setNumber(resultSet.getString("number"));
				account.setOwner(resultSet.getString("owner"));
				account.setBalance(resultSet.getLong("balance"));
				account.setActive(resultSet.getBoolean("active"));
			}
			return Optional.ofNullable(account);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public void update(Account account) throws SQLException {
		String sql = "UPDATE \"account\" SET \"number\" = ?, \"owner\" = ?, \"balance\" = ?, \"active\" = ? WHERE \"id\" = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, account.getNumber());
			statement.setString(2, account.getOwner());
			statement.setLong(3, account.getBalance());
			statement.setBoolean(4, account.isActive());
			statement.setLong(5, account.getId());
			statement.executeUpdate();
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		String sql = "DELETE FROM \"account\" WHERE \"id\" = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}
}
