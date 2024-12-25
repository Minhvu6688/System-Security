package repositories;

import model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    // Tìm các log theo userId
    List<Log> findByUserId(Long userId);

    // Tìm các log gần đây
    List<Log> findTop10ByOrderByCreatedAtDesc();
}
