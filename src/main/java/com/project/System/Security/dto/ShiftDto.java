package com.project.System.Security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {

    private Integer shiftId;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Start time is required")
    private Timestamp startTime;

    @NotNull(message = "End time is required")
    private Timestamp endTime;

    @NotNull(message = "Assigned employee ID is required")
    private Integer assignedEmployeeId;

    // Getters and Setters
    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public void setAssignedEmployeeId(Integer assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }
}
