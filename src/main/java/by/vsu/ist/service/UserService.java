package by.vsu.ist.service;

import by.vsu.ist.domain.User;
import by.vsu.ist.service.exception.ServiceException;

import java.util.Optional;

public interface UserService {
	Optional<User> findByLoginAndPassword(String login, String password) throws ServiceException;
}
