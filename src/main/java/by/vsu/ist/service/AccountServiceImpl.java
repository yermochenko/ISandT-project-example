package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Transfer;
import by.vsu.ist.repository.AccountRepository;
import by.vsu.ist.repository.RepositoryException;
import by.vsu.ist.repository.TransferRepository;
import by.vsu.ist.service.exception.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountServiceImpl extends BaseServiceImpl implements AccountService {
	private AccountRepository accountRepository;
	private TransferRepository transferRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void setTransferRepository(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}

	@Override
	public List<Account> findAll() throws ServiceException {
		try {
			return accountRepository.readAll();
		} catch(RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Account> findActive() throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			List<Account> accounts = accountRepository.readActive();
			for(Account account : accounts) {
				account.setTransfers(transferRepository.readByAccount(account.getId()));
			}
			getTransactionManager().commitTransaction();
			return accounts;
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<Account> findById(Long id) throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			Optional<Account> account = accountRepository.read(id);
			if(account.isPresent()) {
				List<Transfer> transfers = transferRepository.readByAccount(id);
				account.get().setTransfers(transfers);
			}
			getTransactionManager().commitTransaction();
			return account;
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<Account> findByIdWithTransfers(Long id) throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			Optional<Account> account = accountRepository.read(id);
			if(account.isPresent()) {
				List<Transfer> transfers = transferRepository.readByAccount(id);
				account.get().setTransfers(transfers);
				Map<Long, Account> accountMap = new HashMap<>();
				accountMap.put(account.get().getId(), account.get());
				for(Transfer transfer : transfers) {
					transfer.setSender(restore(accountMap, transfer.getSender().orElse(null)));
					transfer.setReceiver(restore(accountMap, transfer.getReceiver().orElse(null)));
				}
			}
			getTransactionManager().commitTransaction();
			return account;
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		}
	}

	@Override
	public void save(Account account) throws ServiceException {
		if(account.getId() != null) {
			try {
				getTransactionManager().startTransaction();
				Account storedAccount = accountRepository.read(account.getId()).orElseThrow();
				storedAccount.setOwner(account.getOwner());
				storedAccount.setActive(account.isActive());
				accountRepository.update(storedAccount);
				getTransactionManager().commitTransaction();
			} catch(RepositoryException e) {
				try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
				throw new ServiceException(e);
			}
		} else {
			try {
				accountRepository.create(account);
			} catch(RepositoryException e) {
				throw new ServiceException(e);
			}
		}
	}

	@Override
	public Optional<Account> delete(Long id) throws ServiceException {
		try {
			getTransactionManager().startTransaction();
			Optional<Account> account = accountRepository.read(id);
			if(account.isPresent()) {
				accountRepository.delete(id);
			}
			getTransactionManager().commitTransaction();
			return account;
		} catch(RepositoryException e) {
			try { getTransactionManager().rollbackTransaction(); } catch(RepositoryException ignored) {}
			throw new ServiceException(e);
		}
	}

	private Account restore(Map<Long, Account> cache, Account account) throws RepositoryException {
		if(account != null) {
			Long id = account.getId();
			Account cachedAccount = cache.get(id);
			if(cachedAccount != null) return cachedAccount;
			account = accountRepository.read(id).orElse(null);
			if(account != null) cache.put(id, account);
		}
		return account;
	}
}
