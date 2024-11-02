package com.base.jwt.controllers;


import com.base.jwt.models.User;
import com.base.jwt.models.UserPrincipal;
import com.base.jwt.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<UserPrincipal> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }
}
