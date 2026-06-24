package com.telusko.My_auditorium_booking_backend.mapper;

import com.telusko.My_auditorium_booking_backend.dto.request.RegisterRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.AuthResponseDto;
import com.telusko.My_auditorium_booking_backend.dto.response.UserResponseDto;
import com.telusko.My_auditorium_booking_backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toUser(RegisterRequestDto dto){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .institute(dto.getInstitute())
                .department(dto.getDepartment())
                .enabled(false)
                .build();
    }

    public AuthResponseDto toAuthResponse(User user){
        return AuthResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .institute(user.getInstitute())
                .department(user.getDepartment())
                .enabled(user.isEnabled())
                .build();
    }

    public UserResponseDto toUserResponse(User user){
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .institute(user.getInstitute())
                .department(user.getDepartment())
                .enabled(user.isEnabled())
                .build();
    }
}
