package by.vsu.ist.repository;

import java.sql.SQLException;

public interface TransactionManager {
	void startTransaction() throws SQLException;

	void commitTransaction() throws SQLException;

	void rollbackTransaction() throws SQLException;
}
