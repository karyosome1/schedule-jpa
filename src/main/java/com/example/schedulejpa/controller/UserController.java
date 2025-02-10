package com.example.schedulejpa.controller;

import com.example.schedulejpa.dto.UserRequestDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserResponseDto save(@RequestBody UserRequestDto dto) {
        return userService.save(dto);
    }

    @GetMapping("/users")
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponseDto findOne(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/users/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);

    }
}
