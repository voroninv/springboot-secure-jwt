package com.base.jwt.controllers;

import com.base.jwt.models.LoginResponse;
import com.base.jwt.models.User;
import com.base.jwt.models.UserDto;
import com.base.jwt.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        LOGGER.info("register request initiated. user: {}", userDto.getEmail());
        User registeredUser = authenticationService.signup(userDto);
        LOGGER.info("register request processed. user: {}", userDto.getEmail());

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserDto userDto) {
        LOGGER.info("login request initiated. user: {}", userDto.getEmail());
        LoginResponse loginResponse = authenticationService.authenticate(userDto);
        LOGGER.info("login request processed. user: {}", userDto.getEmail());

        return ResponseEntity.ok(loginResponse);
    }
}
