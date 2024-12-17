package by.vsu.ist.service;

public interface TransferService {
	void withdrawCash(String accountNumber, Long sum) throws ServiceException;

	void depositCash(String accountNumber, Long sum) throws ServiceException;

	void transfer(String senderNumber, String receiverNumber, Long sum, String purpose) throws ServiceException;
}
