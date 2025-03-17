package by.vsu.ist.repository;

import by.vsu.ist.domain.User;

import java.util.Optional;

public interface UserRepository extends Repository<User> {
	Optional<User> readByLoginAndPassword(String login, String password) throws RepositoryException;
}
