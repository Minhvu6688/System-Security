package com.project.System.Security.service;

import com.project.System.Security.dto.ShiftDto;

import java.util.List;

public interface IShiftService {
    ShiftDto createShift(ShiftDto shiftDto);
    ShiftDto updateShift(Integer id, ShiftDto shiftDto);
    void deleteShift(Integer id);
    ShiftDto getShiftById(Integer id);
    List<ShiftDto> getAllShifts();
    List<ShiftDto> getShiftsByCustomerId(Integer customerId);
}
