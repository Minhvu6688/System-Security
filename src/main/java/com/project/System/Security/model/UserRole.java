//package com.project.System.Security.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "user_roles")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
////@IdClass(UserRoleId.class)
//public class UserRole {
//    @Id
//    @Column(name = "user_id")
//    private Long userId;
//
//    @Id
//    @Column(name = "role_id")
//    private Long roleId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", insertable = false, updatable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id", insertable = false, updatable = false)
//    private Role role;
//
//
//    // getter, setter
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Long getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Long roleId) {
//        this.roleId = roleId;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//}
