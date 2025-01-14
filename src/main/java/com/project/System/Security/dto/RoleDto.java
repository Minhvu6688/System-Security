package com.project.System.Security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long roleId;

    @NotNull(message = "Role name is required")
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    private String roleName;

    // Getters and Setters

    public Integer getRoleId() {
        return Math.toIntExact(roleId);
    }

    public void setRoleId(Integer roleId) {
        this.roleId = Long.valueOf(roleId);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
