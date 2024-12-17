package by.vsu.ist.repository.jdbc;

import by.vsu.ist.repository.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagerImpl implements TransactionManager {
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void startTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}

	@Override
	public void commitTransaction() throws SQLException {
		try {
			connection.commit();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch(SQLException e) {
				connection = null;
			}
		}
	}

	@Override
	public void rollbackTransaction() throws SQLException {
		try {
			connection.rollback();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch(SQLException e) {
				connection = null;
			}
		}
	}
}
