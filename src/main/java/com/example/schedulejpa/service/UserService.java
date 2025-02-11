package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.UserRequestDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public UserResponseDto save(UserRequestDto dto) {
        User user = new User(dto.getUsername(), dto.getEmail(), dto.getPassword());
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), dto.getEmail(), dto.getPassword());

    }
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
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음")
        );
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음")
        );

        user.update(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());

    }

    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        scheduleRepository.deleteByUserId(user.getId()); // 해당 사용자의 일정 삭제
        userRepository.deleteById(id);                   // 사용자 삭제
    }


}
