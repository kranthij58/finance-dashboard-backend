package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.request.CreateUserRequest;
import com.zorvyn.finance.dto.request.UpdateUserRequest;
import com.zorvyn.finance.exception.ErrorResponse;
import com.zorvyn.finance.model.User;
import com.zorvyn.finance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequest request){
        return new ResponseEntity<>(service.addUser(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        service.deleteUser(userId);
        return new ResponseEntity<>(new ErrorResponse(200, "User deleted successfully", LocalDateTime.now()), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateRequest,@PathVariable("id") Long userId) {
        return new ResponseEntity<>(service.updateUser(updateRequest, userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(service.getUser(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
}
