package by.vsu.ist.service;

public class AccountNotExistsServiceException extends ServiceException {
	private final String accountNumber;

	public AccountNotExistsServiceException(String accountNumber) {
		super(String.format("Account number %s does not exist", accountNumber));
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
}
