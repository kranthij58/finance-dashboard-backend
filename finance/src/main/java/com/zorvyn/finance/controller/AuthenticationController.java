package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.request.LoginRequest;
import com.zorvyn.finance.dto.request.RegisterRequest;
import com.zorvyn.finance.dto.response.JwtResponse;
import com.zorvyn.finance.dto.response.UserResponse;
import com.zorvyn.finance.service.AuthenticationService;
import com.zorvyn.finance.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService service,  AuthenticationManager manager, JwtService jwtService) {
        this.service = service;
        this.manager = manager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse response = service.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        JwtResponse jwtResponse = jwtService.generateToken(loginRequest);
        return  new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

}
