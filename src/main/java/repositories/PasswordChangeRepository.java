package repositories;

import model.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordChangeRepository extends JpaRepository <PasswordHistory, Long> {
    List<PasswordHistory> findByUserId(Long userId);
}
