package by.vsu.ist.repository.jdbc;

import by.vsu.ist.domain.Role;
import by.vsu.ist.domain.User;
import by.vsu.ist.repository.RepositoryException;
import by.vsu.ist.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

public class UserRepositoryImpl extends BaseRepository implements UserRepository {
	@Override
	public Long create(User user) throws RepositoryException {
		String sql = "INSERT INTO \"user\"(\"login\", \"password\", \"role\") VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole().toString());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getLong(1);
		} catch(SQLException e) {
			throw new RepositoryException(e);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public Optional<User> readByLoginAndPassword(String login, String password) throws RepositoryException {
		String sql = "SELECT \"id\", \"login\", \"password\", \"role\" FROM \"user\" WHERE \"login\" = ? AND \"password\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, login);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			User user = null;
			if(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong("id"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setRole(Role.valueOf(resultSet.getString("role")));
			}
			return Optional.ofNullable(user);
		} catch(SQLException e) {
			throw new RepositoryException(e);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public Optional<User> read(Long id) throws RepositoryException {
		String sql = "SELECT \"id\", \"login\", \"password\", \"role\" FROM \"user\" WHERE \"id\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			User user = null;
			if(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong("id"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setRole(Role.valueOf(resultSet.getString("role")));
			}
			return Optional.ofNullable(user);
		} catch(SQLException e) {
			throw new RepositoryException(e);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public void update(User user) throws RepositoryException {
		String sql = "UPDATE \"user\" SET \"login\" = ?, \"password\" = ?, \"role\" = ? WHERE \"id\" = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole().toString());
			statement.setLong(4, user.getId());
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new RepositoryException(e);
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	@Override
	public void delete(Long id) throws RepositoryException {
		String sql = "DELETE FROM \"user\" WHERE \"id\" = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new RepositoryException(e);
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}
}
