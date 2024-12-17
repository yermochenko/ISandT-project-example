package by.vsu.ist.service.exception;

import by.vsu.ist.domain.Account;

public class AccountNotActiveServiceException extends ServiceException {
	private final Account account;

	public AccountNotActiveServiceException(Account account) {
		super(String.format("Account number %s is not active", account.getNumber()));
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}
