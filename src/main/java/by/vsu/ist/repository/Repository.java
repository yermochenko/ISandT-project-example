package by.vsu.ist.repository;

import by.vsu.ist.domain.Entity;

import java.util.Optional;

public interface Repository<E extends Entity> {
	Long create(E entity) throws RepositoryException;

	Optional<E> read(Long id) throws RepositoryException;

	void update(E entity) throws RepositoryException;

	void delete(Long id) throws RepositoryException;
}
