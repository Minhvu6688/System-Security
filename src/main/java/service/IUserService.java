package service;

import dto.PasswordChangeDTO;
import dto.UserDTo;
import model.User;

import java.util.Optional;

public interface IUserService {
    User createUser(UserDTo userDTo);
    Optional<User> getUserByName(String username);
    Optional<User> getUserById(Long id);
    User changePassword(Long userId, PasswordChangeDTO passwordChangeDTO);
}
