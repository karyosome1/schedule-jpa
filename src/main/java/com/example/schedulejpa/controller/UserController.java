package com.example.schedulejpa.controller;

import com.example.schedulejpa.dto.UserRequestDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.signUp(dto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto dto, HttpServletRequest request) {
        return userService.login(dto.getEmail(), dto.getPassword(), request);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    // 사용자 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 사용자 1명 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // 사용자 수정
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    // 사용자 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

