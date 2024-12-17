package by.vsu.ist.service;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface AccountService {
	List<Account> findAll() throws ServiceException;

	List<Account> findActive() throws ServiceException;

	Optional<Account> findById(Long id) throws ServiceException;

	Optional<Account> findByIdWithTransfers(Long id) throws ServiceException;

	void save(Account account) throws ServiceException;

	Optional<Account> delete(Long id) throws ServiceException;
}
