package com.project.System.Security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.security.Timestamp;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class BranchDto {
    private Long id;

    @NotNull(message = "Branch name is required")
    @Size(max = 100, message = "Branch name must not exceed 100 characters")
    private String name;


    @NotNull(message = "Parent ID cannot be null")
    private Long parentId;

    private Instant createdAt;
    private Instant updatedAt;

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
