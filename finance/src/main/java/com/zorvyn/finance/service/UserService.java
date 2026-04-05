package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.mapper.UserMapper;
import com.zorvyn.finance.dto.request.CreateUserRequest;
import com.zorvyn.finance.dto.request.UpdateUserRequest;
import com.zorvyn.finance.dto.response.UserResponse;
import com.zorvyn.finance.exception.BadRequestException;
import com.zorvyn.finance.exception.ResourceNotFoundException;
import com.zorvyn.finance.model.User;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public UserResponse addUser(CreateUserRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists.");
        }
        if (repo.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone number already exists.");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .status("ACTIVE")
                .build();
        repo.save(user);
        return UserMapper.toResponse(user);
    }

    public void deleteUser(Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        repo.delete(user);
    }

    public UserResponse updateUser(UpdateUserRequest request, Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getRole() != null) user.setRole(request.getRole());
        if (request.getStatus() != null) user.setStatus(request.getStatus());

        repo.save(user);
        return UserMapper.toResponse(user);
    }

    public UserResponse getUser(Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
