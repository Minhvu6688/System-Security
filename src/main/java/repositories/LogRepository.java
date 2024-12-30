package repositories;

import model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    // Tìm các log theo userId
    List<Log> findByUserId(Integer userId);
}
