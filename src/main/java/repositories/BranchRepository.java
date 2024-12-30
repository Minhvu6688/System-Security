package repositories;

import model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
    List<Branch> findByParentId(Long parentId);

    List<Branch> id(Long id);
}
