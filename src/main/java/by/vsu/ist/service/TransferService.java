package by.vsu.ist.service;

import java.sql.SQLException;

public interface TransferService {
	void withdrawCash(String accountNumber, Long sum) throws SQLException;

	void depositCash(String accountNumber, Long sum) throws SQLException;

	void transfer(String senderNumber, String receiverNumber, Long sum, String purpose) throws SQLException;
}
