package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.request.CreateUserRequest;
import com.zorvyn.finance.dto.request.UpdateUserRequest;
import com.zorvyn.finance.model.User;
import com.zorvyn.finance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequest request){
        service.addUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        service.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateRequest,@PathVariable("id") Long userId) {
        service.updateUser(updateRequest, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        service.getUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
