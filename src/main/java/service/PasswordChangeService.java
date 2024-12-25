//package service;
//
//import dto.PasswordChangeDTO;
//import lombok.RequiredArgsConstructor;
//import model.PasswordHistory;
//import model.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import repositories.PasswordChangeRepository;
//import repositories.UserRepository;
//
//@Service
//@RequiredArgsConstructor
//public class PasswordChangeService implements IPasswordChangeService {
//    private final UserRepository userRepository;
//    private final PasswordChangeRepository passwordChangeRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//
//    @Override
//    public User changePassword(Long userId, PasswordChangeDTO passwordChangeDTO) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
//            throw new RuntimeException("Current password is incorrect");
//        }
//
//        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getRetypePassword())) {
//            throw new RuntimeException("New passwords do not match");
//        }
//
//        PasswordHistory passwordHistory = new PasswordHistory();
//        passwordHistory.setUser(user);
//        passwordHistory.setOldPassword(user.getPassword());
//        passwordChangeRepository.save(passwordHistory);
//
//        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
//        return userRepository.save(user);
//    }
//}
