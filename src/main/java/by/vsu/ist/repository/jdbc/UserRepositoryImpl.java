package by.vsu.ist.repository.jdbc;

import by.vsu.ist.domain.Role;
import by.vsu.ist.domain.User;
import by.vsu.ist.repository.RepositoryException;
import by.vsu.ist.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
	public UserRepositoryImpl() {
		super(
			"SELECT \"id\", \"login\", \"password\", \"role\" FROM \"user\" WHERE \"id\" = ?",
			"INSERT INTO \"user\"(\"login\", \"password\", \"role\") VALUES (?, ?, ?)",
			"UPDATE \"user\" SET \"login\" = ?, \"password\" = ?, \"role\" = ? WHERE \"id\" = ?",
			"DELETE FROM \"user\" WHERE \"id\" = ?"
		);
	}

	@Override
	public Optional<User> readByLoginAndPassword(String login, String password) throws RepositoryException {
		String sql = "SELECT \"id\", \"login\", \"password\", \"role\" FROM \"user\" WHERE \"login\" = ? AND \"password\" = ?";
		AtomicReference<User> user = new AtomicReference<>();
		read(
			sql,
			statement -> {
				statement.setString(1, login);
				statement.setString(2, password);
			},
			user::set
		);
		return Optional.ofNullable(user.get());
	}

	@Override
	protected User buildFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setLogin(resultSet.getString("login"));
		user.setPassword(resultSet.getString("password"));
		user.setRole(Role.valueOf(resultSet.getString("role")));
		return user;
	}

	@Override
	protected void fillInsertPreparedStatement(PreparedStatement statement, User user) throws SQLException {
		statement.setString(1, user.getLogin());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getRole().toString());
	}

	@Override
	protected void fillUpdatePreparedStatement(PreparedStatement statement, User user) throws SQLException {
		statement.setString(1, user.getLogin());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getRole().toString());
		statement.setLong(4, user.getId());
	}
}
