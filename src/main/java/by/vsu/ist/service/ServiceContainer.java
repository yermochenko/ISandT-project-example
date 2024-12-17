package by.vsu.ist.service;

import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.jdbc.AccountRepositoryImpl;
import by.vsu.ist.repository.jdbc.DatabaseConnector;
import by.vsu.ist.repository.TransactionManager;
import by.vsu.ist.repository.TransferRepository;
import by.vsu.ist.repository.jdbc.TransactionManagerImpl;
import by.vsu.ist.repository.jdbc.TransferRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceContainer implements AutoCloseable {
	private AccountService accountService;
	public AccountService getAccountServiceInstance() throws SQLException {
		if(accountService == null) {
			AccountServiceImpl accountService = new AccountServiceImpl();
			accountService.setTransactionManager(getTransactionManagerInstance());
			accountService.setAccountRepository(getAccountRepositoryInstance());
			accountService.setTransferRepository(getTransferRepositoryInstance());
			this.accountService = accountService;
		}
		return accountService;
	}

	private TransferService transferService;
	public TransferService getTransferServiceInstance() throws SQLException {
		if(transferService == null) {
			TransferServiceImpl transferService = new TransferServiceImpl();
			transferService.setTransactionManager(getTransactionManagerInstance());
			transferService.setAccountRepository(getAccountRepositoryInstance());
			transferService.setTransferRepository(getTransferRepositoryInstance());
			this.transferService = transferService;
		}
		return transferService;
	}

	private TransactionManager transactionManager;
	private TransactionManager getTransactionManagerInstance() throws SQLException {
		if(transactionManager == null) {
			TransactionManagerImpl transactionManager = new TransactionManagerImpl();
			transactionManager.setConnection(getConnectionInstance());
			this.transactionManager = transactionManager;
		}
		return transactionManager;
	}

	private AccountRepository accountRepository;
	private AccountRepository getAccountRepositoryInstance() throws SQLException {
		if(accountRepository == null) {
			AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
			accountRepository.setConnection(getConnectionInstance());
			this.accountRepository = accountRepository;
		}
		return accountRepository;
	}

	private TransferRepository transferRepository;
	private TransferRepository getTransferRepositoryInstance() throws SQLException {
		if(transferRepository == null) {
			TransferRepositoryImpl transferRepository = new TransferRepositoryImpl();
			transferRepository.setConnection(getConnectionInstance());
			this.transferRepository = transferRepository;
		}
		return transferRepository;
	}

	private Connection connection;
	private Connection getConnectionInstance() throws SQLException {
		if(connection == null) {
			connection = DatabaseConnector.getConnection();
		}
		return connection;
	}

	@Override
	public void close() {
		if(connection != null) {
			try {
				connection.close();
			} catch(SQLException ignored) {}
		}
	}
}
