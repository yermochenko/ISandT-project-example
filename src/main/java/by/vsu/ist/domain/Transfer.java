package by.vsu.ist.domain;

import java.util.Date;
import java.util.Optional;

public class Transfer extends Entity {
	private Account sender;
	private Account receiver;
	private Long sum;
	private Date date;
	private String purpose;

	public final Optional<Account> getSender() {
		return Optional.ofNullable(sender);
	}

	public final void setSender(Account sender) {
		this.sender = sender;
	}

	public final Optional<Account> getReceiver() {
		return Optional.ofNullable(receiver);
	}

	public final void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	public final Long getSum() {
		return sum;
	}

	public final void setSum(Long sum) {
		this.sum = sum;
	}

	public final Date getDate() {
		return date;
	}

	public final void setDate(Date date) {
		this.date = date;
	}

	public final Optional<String> getPurpose() {
		return Optional.ofNullable(purpose);
	}

	public final void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Override
	public String toString() {
		return "Transfer{" +
				"id=" + getId() +
				", sender=" + getSender().orElse(new Account()).getId() +
				", receiver=" + getReceiver().orElse(new Account()).getId() +
				", sum=" + getSum() +
				", date=" + getDate() +
				", purpose=" + (getPurpose().isPresent() ? '\'' + getPurpose().get() + '\'' : null) +
				'}';
	}
}
