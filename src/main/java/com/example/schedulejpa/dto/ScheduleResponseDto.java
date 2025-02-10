package com.example.schedulejpa.dto;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String content;

    public ScheduleResponseDto(Long id, String username, String title, String content) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
    }
}
