package by.vsu.ist.domain;

public class Account extends Entity {
	private String number;
	private String owner;
	private Long balance;
	private boolean active;

	public final String getNumber() {
		return number;
	}

	public final void setNumber(String number) {
		this.number = number;
	}

	public final String getOwner() {
		return owner;
	}

	public final void setOwner(String owner) {
		this.owner = owner;
	}

	public final Long getBalance() {
		return balance;
	}

	public final void setBalance(Long balance) {
		this.balance = balance;
	}

	public final boolean isActive() {
		return active;
	}

	public final void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + getId() +
				", number='" + getNumber() + '\'' +
				", owner='" + getOwner() + '\'' +
				", balance=" + getBalance() +
				", active=" + isActive() +
				'}';
	}
}
