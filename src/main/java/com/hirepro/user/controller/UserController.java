package com.hirepro.user.controller;

import com.hirepro.user.entity.UserProfile;
import com.hirepro.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // -------- Create or update user --------
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')") // Only Admins or Superadmins can create/update users
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile user) {
        UserProfile savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // -------- Get all users --------
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'TEAMLEAD')") // Admins, Superadmins, or Teamleads
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        List<UserProfile> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // -------- Get user by ID --------
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'TEAMLEAD') or #userId == authentication.principal.userId")
    // Admins, Superadmins, Teamleads, or the user themselves
    public ResponseEntity<UserProfile> getUserById(@PathVariable String userId) {
        Optional<UserProfile> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // -------- Delete user by ID --------
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')") // Only Admins or Superadmins
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
