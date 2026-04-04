package com.zorvyn.finance.dto.mapper;

import com.zorvyn.finance.dto.request.RegisterRequest;
import com.zorvyn.finance.dto.response.UserResponse;
import com.zorvyn.finance.model.User;

public class UserMapper {

    public static User toEntity(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .status("ACTIVE")
                .phone(request.getPhone())
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .phone(user.getPhone())
                .build();
    }
}
