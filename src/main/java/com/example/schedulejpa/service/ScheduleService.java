package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.ScheduleRequestDto;
import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto save(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getUsername(), dto.getTitle(), dto.getContent());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getUsername(), dto.getTitle(), savedSchedule.getContent());

    }
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponseDto> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleResponseDto dto = new ScheduleResponseDto(schedule.getId(), schedule.getUsername(), schedule.getTitle(), schedule.getContent());
            dtos.add(dto);
        }
        return dtos;

    }
    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음")
        );
        return new ScheduleResponseDto(schedule.getId(), schedule.getUsername(), schedule.getTitle(), schedule.getContent());
    }

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음")
        );

        schedule.update(dto.getUsername(), dto.getTitle(), dto.getContent());
        return new ScheduleResponseDto(schedule.getId(), schedule.getUsername(), schedule.getTitle(), schedule.getContent());

    }

    @Transactional
    public void deleteById(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("없음");
        }
        scheduleRepository.deleteById(id);
    }
}
