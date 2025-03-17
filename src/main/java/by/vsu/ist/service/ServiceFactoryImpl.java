package by.vsu.ist.service;

import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.TransactionManager;
import by.vsu.ist.repository.TransferRepository;
import by.vsu.ist.repository.UserRepository;
import by.vsu.ist.repository.jdbc.AccountRepositoryImpl;
import by.vsu.ist.repository.jdbc.TransactionManagerImpl;
import by.vsu.ist.repository.jdbc.TransferRepositoryImpl;
import by.vsu.ist.repository.jdbc.UserRepositoryImpl;
import by.vsu.ist.repository.jdbc.cp.ConnectionPool;
import by.vsu.ist.repository.jdbc.cp.ConnectionPoolException;
import by.vsu.ist.service.exception.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceFactoryImpl implements ServiceFactory {
	private AccountService accountService;
	@Override
	public AccountService getAccountServiceInstance() throws ServiceException {
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
	@Override
	public TransferService getTransferServiceInstance() throws ServiceException {
		if(transferService == null) {
			TransferServiceImpl transferService = new TransferServiceImpl();
			transferService.setTransactionManager(getTransactionManagerInstance());
			transferService.setAccountRepository(getAccountRepositoryInstance());
			transferService.setTransferRepository(getTransferRepositoryInstance());
			this.transferService = transferService;
		}
		return transferService;
	}

	private UserService userService;
	@Override
	public UserService getUserServiceInstance() throws ServiceException {
		if(userService == null) {
			UserServiceImpl userService = new UserServiceImpl();
			userService.setTransactionManager(getTransactionManagerInstance());
			userService.setUserRepository(getUserRepositoryInstance());
			this.userService = userService;
		}
		return userService;
	}

	private TransactionManager transactionManager;
	private TransactionManager getTransactionManagerInstance() throws ServiceException {
		if(transactionManager == null) {
			TransactionManagerImpl transactionManager = new TransactionManagerImpl();
			transactionManager.setConnection(getConnectionInstance());
			this.transactionManager = transactionManager;
		}
		return transactionManager;
	}

	private AccountRepository accountRepository;
	private AccountRepository getAccountRepositoryInstance() throws ServiceException {
		if(accountRepository == null) {
			AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
			accountRepository.setConnection(getConnectionInstance());
			this.accountRepository = accountRepository;
		}
		return accountRepository;
	}

	private TransferRepository transferRepository;
	private TransferRepository getTransferRepositoryInstance() throws ServiceException {
		if(transferRepository == null) {
			TransferRepositoryImpl transferRepository = new TransferRepositoryImpl();
			transferRepository.setConnection(getConnectionInstance());
			this.transferRepository = transferRepository;
		}
		return transferRepository;
	}

	private UserRepository userRepository;
	private UserRepository getUserRepositoryInstance() throws ServiceException {
		if(userRepository == null) {
			UserRepositoryImpl userRepository = new UserRepositoryImpl();
			userRepository.setConnection(getConnectionInstance());
			this.userRepository = userRepository;
		}
		return userRepository;
	}

	private Connection connection;
	private Connection getConnectionInstance() throws ServiceException {
		if(connection == null) {
			try {
				connection = ConnectionPool.getInstance().getConnection();
			} catch(ConnectionPoolException e) {
				throw new ServiceException(e);
			}
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
