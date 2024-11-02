package com.base.jwt.services;

import com.base.jwt.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    public User signUp(UserDto userDto) {
        return userService.save(userDto);
    }

    public LoginResponse signIn(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        User authenticatedUser = userService.findByEmail(userDto.getEmail());
        String jwtToken = jwtService.generateToken(new UserPrincipal(authenticatedUser));

        return new LoginResponse(jwtToken, jwtService.getExpirationTime());
    }
}