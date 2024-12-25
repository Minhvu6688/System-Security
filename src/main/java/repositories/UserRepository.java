package repositories;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm kiếm người dùng theo username
    Optional<User> findByUsername(String username);

    // Tìm kiếm người dùng theo email
    Optional<User> findByEmail(String email);

    // Tìm kiếm người dùng theo id
    Optional<User> findById(Long id);
}
