package service;

import dto.PasswordChangeDTO;
import dto.UserDTo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.Log;
import model.PasswordHistory;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repositories.LogRepository;
import repositories.PasswordChangeRepository;
import repositories.UserRepository;
import java.util.Optional;


@Service

public class UserService  implements IUserService{

    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final PasswordChangeRepository passwordChangeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor thủ công để khởi tạo các biến `final`
    public UserService(UserRepository userRepository,
                       LogRepository logRepository,
                       PasswordChangeRepository passwordChangeRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
        this.passwordChangeRepository = passwordChangeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public User createUser(UserDTo userDTo) {
        if (userRepository.findByUsername(userDTo.getUsername()).isPresent() ||
                userRepository.findByEmail(userDTo.getEmail()).isPresent()) {
            throw new RuntimeException("Username or Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(userDTo.getPassword());

        User user = new User();
        user.setFullName(userDTo.getFullName());
        user.setUsername(userDTo.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(userDTo.getEmail());
        user.setActive(userDTo.getActive());

        User savedUser = userRepository.save(user);

        logAction(savedUser, "User created");
        return savedUser;
    }

    @Override
    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User changePassword(Long userId, PasswordChangeDTO passwordChangeDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Kiểm tra mật khẩu mới và mật khẩu xác nhận có khớp không
        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getRetypePassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        // Kiểm tra mật khẩu mới không trùng với mật khẩu cũ
        if (passwordEncoder.matches(passwordChangeDTO.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password cannot be the same as the current password");
        }

        // Lưu lịch sử mật khẩu cũ
        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUser(user);
        passwordHistory.setOldPassword(user.getPassword());
        passwordChangeRepository.save(passwordHistory);

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);

        logAction(user, "Password changed");

        return user;
    }
    private void logAction(User user, String action) {
        Log log = new Log();
        log.setUserId(user.getId());
        log.setAction(action);
        logRepository.save(log);
    }
}
