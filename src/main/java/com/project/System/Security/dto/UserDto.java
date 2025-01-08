package com.project.System.Security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer userId;

    @JsonProperty("username")
    @NotNull(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @JsonProperty("password")
    @NotNull(message = "Password is required")
    private String password;

    @JsonProperty("roleId")
    @NotNull(message = "Role ID is required")
    private Integer roleId;

    @JsonProperty("branchId")
    @NotNull(message = "Branch ID is required")
    private Long branchId;


    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
