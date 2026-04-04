package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.request.RegisterRequest;
import com.zorvyn.finance.dto.response.UserResponse;
import com.zorvyn.finance.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse response = service.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
