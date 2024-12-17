package by.vsu.ist.repository;

import by.vsu.ist.domain.Entity;

import java.sql.SQLException;
import java.util.Optional;

public interface Repository<E extends Entity> {
	Long create(E entity) throws SQLException;

	Optional<E> read(Long id) throws SQLException;

	void update(E entity) throws SQLException;

	void delete(Long id) throws SQLException;
}
