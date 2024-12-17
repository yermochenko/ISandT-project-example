package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;
import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.RepositoryException;
import by.vsu.ist.repository.TransferRepository;
import by.vsu.ist.service.exception.AccountNotActiveServiceException;
import by.vsu.ist.service.exception.AccountNotExistsServiceException;
import by.vsu.ist.service.exception.InsufficientAccountFundsServiceException;
import by.vsu.ist.service.exception.ServiceException;

public class TransferServiceImpl extends BaseServiceImpl implements TransferService {
	private AccountRepository accountRepository;
	private TransferRepository transferRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void setTransferRepository(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

	@Override
	public void withdrawCash(String accountNumber, Long sum) throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			Account account = getAccount(accountNumber);
			if(sum > account.getBalance()) throw new InsufficientAccountFundsServiceException(account, sum);
			account.setBalance(account.getBalance() - sum);
			accountRepository.update(account);
			Transfer transfer = new Transfer();
			transfer.setSender(account);
			transfer.setSum(sum);
			transferRepository.create(transfer);
			getTransactionManager().commitTransaction();
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		} catch(ServiceException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw e;
		}
	}

	@Override
	public void depositCash(String accountNumber, Long sum) throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			Account account = getAccount(accountNumber);
			account.setBalance(account.getBalance() + sum);
			accountRepository.update(account);
			Transfer transfer = new Transfer();
			transfer.setReceiver(account);
			transfer.setSum(sum);
			transferRepository.create(transfer);
			getTransactionManager().commitTransaction();
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		} catch(ServiceException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw e;
		}
	}

	@Override
	public void transfer(String senderNumber, String receiverNumber, Long sum, String purpose) throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			Account sender = getAccount(senderNumber);
			Account receiver = getAccount(receiverNumber);
			if(sum > sender.getBalance()) throw new InsufficientAccountFundsServiceException(sender, sum);
			sender.setBalance(sender.getBalance() - sum);
			accountRepository.update(sender);
			receiver.setBalance(receiver.getBalance() + sum);
			accountRepository.update(receiver);
			Transfer transfer = new Transfer();
			transfer.setSender(sender);
			transfer.setReceiver(receiver);
			transfer.setSum(sum);
			transfer.setPurpose(purpose);
			transferRepository.create(transfer);
			getTransactionManager().commitTransaction();
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		} catch(ServiceException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw e;
		}
	}

	private Account getAccount(String number) throws RepositoryException, ServiceException {
		Account account = accountRepository.readByNumber(number).orElseThrow(() -> new AccountNotExistsServiceException(number));
		if(!account.isActive()) throw new AccountNotActiveServiceException(account);
		return account;
	}
}
