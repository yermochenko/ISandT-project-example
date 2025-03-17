package by.vsu.ist.service;

import by.vsu.ist.service.exception.ServiceException;

public interface ServiceFactory extends AutoCloseable {
	AccountService getAccountServiceInstance() throws ServiceException;

	TransferService getTransferServiceInstance() throws ServiceException;

	UserService getUserServiceInstance() throws ServiceException;

	@Override
	void close();

	static ServiceFactory getInstance() {
		return new ServiceFactoryImpl();
	}
}
