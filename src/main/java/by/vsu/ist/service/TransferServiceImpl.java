package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;
import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.TransferRepository;

import java.sql.SQLException;

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
	public void withdrawCash(String accountNumber, Long sum) throws SQLException {
		getTransactionManager().startTransaction();
		try {
			Account account = accountRepository.readByNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException("Account not found"));
			if(!account.isActive()) throw new IllegalArgumentException("Account is not active");
			if(sum > account.getBalance()) throw new IllegalArgumentException("Not enough money");
			account.setBalance(account.getBalance() - sum);
			accountRepository.update(account);
			Transfer transfer = new Transfer();
			transfer.setSender(account);
			transfer.setSum(sum);
			transferRepository.create(transfer);
			getTransactionManager().commitTransaction();
		} catch(SQLException | IllegalArgumentException e) {
			getTransactionManager().rollbackTransaction();
			throw e;
		}
	}

	@Override
	public void depositCash(String accountNumber, Long sum) throws SQLException {
		getTransactionManager().startTransaction();
		try {
			Account account = accountRepository.readByNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException("Account not found"));
			if(!account.isActive()) throw new IllegalArgumentException("Account is not active");
			account.setBalance(account.getBalance() + sum);
			accountRepository.update(account);
			Transfer transfer = new Transfer();
			transfer.setReceiver(account);
			transfer.setSum(sum);
			transferRepository.create(transfer);
			getTransactionManager().commitTransaction();
		} catch(SQLException | IllegalArgumentException e) {
			getTransactionManager().rollbackTransaction();
			throw e;
		}
	}

	@Override
	public void transfer(String senderNumber, String receiverNumber, Long sum, String purpose) throws SQLException {
		getTransactionManager().startTransaction();
		try {
			Account sender = accountRepository.readByNumber(senderNumber).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
			if(!sender.isActive()) throw new IllegalArgumentException("Sender is not active");
			Account receiver = accountRepository.readByNumber(receiverNumber).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
			if(!receiver.isActive()) throw new IllegalArgumentException("Receiver is not active");
			if(sum > sender.getBalance()) throw new IllegalArgumentException("Not enough money");
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
		} catch(SQLException | IllegalArgumentException e) {
			getTransactionManager().rollbackTransaction();
			throw e;
		}
	}
}
