package by.vsu.ist.repository.jdbc.cp;

public class ConnectionPoolException extends Exception {
	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}
}
