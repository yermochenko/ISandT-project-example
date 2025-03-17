package by.vsu.ist.domain;

public enum Role {
	ADMIN("Администратор"),
	MANAGER("Менеджер"),
	CASHIER("Кассир");

	private final String name;

	Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
