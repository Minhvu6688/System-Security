/*
package com.project.System.Security.service;

import com.project.System.Security.dto.ShiftDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.project.System.Security.model.Shift;
import com.project.System.Security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.ShiftRepository;
import com.project.System.Security.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ShiftService implements IShiftService{

    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {
        Shift shift = new Shift();
        shift.setCustomerId(shiftDto.getCustomerId());
        shift.setStartTime(shiftDto.getStartTime());
        shift.setEndTime(shiftDto.getEndTime());
        if (shiftDto.getAssignedEmployeeId() != null) {
            User user = userRepository.findById(Long.valueOf(shiftDto.getAssignedEmployeeId())).orElseThrow(() -> new RuntimeException("User not found"));
            shift.setAssignedEmployee(user);
        }
        shift = shiftRepository.save(shift);
        return convertToDTO(shift);
    }

    @Override
    public ShiftDto updateShift(Integer id, ShiftDto shiftDto) {
        Shift shift = shiftRepository.findById(id).orElseThrow(() -> new RuntimeException("Shift not found"));
        shift.setCustomerId(shiftDto.getCustomerId());
        shift.setStartTime(shiftDto.getStartTime());
        shift.setEndTime(shiftDto.getEndTime());
        if (shiftDto.getAssignedEmployeeId() != null) {
            User user = userRepository.findById(Long.valueOf(shiftDto.getAssignedEmployeeId())).orElseThrow(() -> new RuntimeException("User not found"));
            shift.setAssignedEmployee(user);
        }
        shift = shiftRepository.save(shift);
        return convertToDTO(shift);
    }

    @Override
    public void deleteShift(Integer id) {
        shiftRepository.deleteById(id);
    }

    @Override
    public ShiftDto getShiftById(Integer id) {
        Shift shift = shiftRepository.findById(id).orElseThrow(() -> new RuntimeException("Shift not found"));
        return convertToDTO(shift);
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        return shiftRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ShiftDto> getShiftsByCustomerId(Integer customerId) {
        return shiftRepository.findByAssignedEmployeeId(customerId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private ShiftDto convertToDTO(Shift shift) {
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setShiftId(shift.getShiftId());
        shiftDto.setCustomerId(shift.getCustomerId());
        shiftDto.setStartTime(shift.getStartTime());
        shiftDto.setEndTime(shift.getEndTime());
        shiftDto.setAssignedEmployeeId(shift.getAssignedEmployee().getUserId());
        return shiftDto;
    }
}
*/
