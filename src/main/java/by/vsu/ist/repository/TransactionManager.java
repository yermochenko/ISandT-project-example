package by.vsu.ist.repository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void startTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}

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
