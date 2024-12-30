package service;

import dto.UserDto;
import model.User;

import java.util.Optional;

public interface IUserService {
    User createUser(UserDto userDTo);
    Optional<User> getUserByName(String username);
    Optional<User> getUserById(Long id);
}
