package com.hirepro.user.service;

import com.hirepro.user.entity.UserProfile;
import com.hirepro.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserProfileRepository userRepository;

    public UserService(UserProfileRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create or Update User
    public UserProfile saveUser(UserProfile user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<UserProfile> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    // Delete user by ID
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}