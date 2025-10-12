package com.example.userapi.entity;

import com.example.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank(message = "사용자 이름은 필수입니다")
    @Size(min = 2, max = 50, message = "사용자 이름은 2-50자 사이여야 합니다")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이어야 합니다")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "전체 이름은 필수입니다")
    @Size(max = 100, message = "전체 이름은 100자를 초과할 수 없습니다")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "bio")
    @Size(max = 500, message = "자기소개는 500자를 초과할 수 없습니다")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.USER;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}

