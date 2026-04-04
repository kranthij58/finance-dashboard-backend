package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.mapper.UserMapper;
import com.zorvyn.finance.dto.request.RegisterRequest;
import com.zorvyn.finance.dto.response.UserResponse;
import com.zorvyn.finance.exception.BadRequestException;
import com.zorvyn.finance.model.User;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthenticationService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public UserResponse register(RegisterRequest registerRequest) {
        if(repo.existsByEmail(registerRequest.getEmail())){
            throw new BadRequestException("Email already exists please check once.");
        }
        if(repo.existsByPhone(registerRequest.getPhone())){
            throw new BadRequestException("Phone already exists please check once");
        }
        User user = UserMapper.toEntity(registerRequest);
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole("VIEWER");
        repo.save(user);
        return UserMapper.toResponse(user);


    }

}
