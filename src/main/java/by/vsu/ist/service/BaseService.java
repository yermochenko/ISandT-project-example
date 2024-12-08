package by.vsu.ist.service;

import by.vsu.ist.repository.TransactionManager;

public class BaseService {
	private TransactionManager transactionManager;

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	protected TransactionManager getTransactionManager() {
		return transactionManager;
	}
}
