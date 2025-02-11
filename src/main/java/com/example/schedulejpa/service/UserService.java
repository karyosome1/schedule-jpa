package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.UserRequestDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    // 사용자 회원가입
    @Transactional
    public UserResponseDto signUp(UserRequestDto dto) {
        User user = new User(dto.getUsername(), dto.getEmail(), dto.getPassword());
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), dto.getEmail(), dto.getPassword());

    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<String> login(String email, String password, HttpServletRequest request) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", user.getId());
                    return ResponseEntity.ok("로그인 성공");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    // 로그아웃
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok("로그아웃 성공");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 로그아웃된 상태입니다.");
    }


    // 사용자 전체 조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> dtos = new ArrayList<>();
        for (User user : users) {
            UserResponseDto dto = new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
            dtos.add(dto);
        }
        return dtos;

    }

    // 사용자 단건 조회
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음")
        );
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    // 사용자 수정
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음")
        );

        user.update(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());

    }

    // 사옹자 삭제
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        scheduleRepository.deleteByUserId(user.getId()); // 해당 사용자의 일정 삭제
        userRepository.deleteById(id);                   // 사용자 삭제
    }
}
