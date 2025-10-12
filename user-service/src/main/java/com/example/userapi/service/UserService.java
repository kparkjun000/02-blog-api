package com.example.userapi.service;

import com.example.userapi.entity.User;
import com.example.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ID로 사용자 조회
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 사용자명으로 조회
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 이메일로 조회
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 활성 사용자 조회
    public List<User> getActiveUsers() {
        return userRepository.findByActive(true);
    }

    // 사용자 생성
    public User createUser(User user) {
        // 중복 체크
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    // 사용자 수정
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setFullName(userDetails.getFullName());
        user.setBio(userDetails.getBio());
        user.setRole(userDetails.getRole());
        user.setActive(userDetails.getActive());

        return userRepository.save(user);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    // 사용자 비활성화
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(false);
        return userRepository.save(user);
    }
}

