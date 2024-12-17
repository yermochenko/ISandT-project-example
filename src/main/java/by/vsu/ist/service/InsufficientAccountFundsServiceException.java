package by.vsu.ist.service;

import by.vsu.ist.domain.Account;

public class InsufficientAccountFundsServiceException extends ServiceException {
	private final Account account;
	private final Long requestedSum;

	public InsufficientAccountFundsServiceException(Account account, Long requestedSum) {
		super(String.format(
			"Account number %s have insufficient funds. Actual balance %,d.%02d. Requested sum %,d.%02d.",
			account.getNumber(),
			account.getBalance() / 100,
			account.getBalance() % 100,
			requestedSum / 100,
			requestedSum % 100
		));
		this.account = account;
		this.requestedSum = requestedSum;
	}

	public Account getAccount() {
		return account;
	}

	public Long getRequestedSum() {
		return requestedSum;
	}
}
