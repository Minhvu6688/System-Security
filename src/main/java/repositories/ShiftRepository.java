package repositories;

import model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    List<Shift> findByAssignedEmployee(Integer assignedEmployee);
}
