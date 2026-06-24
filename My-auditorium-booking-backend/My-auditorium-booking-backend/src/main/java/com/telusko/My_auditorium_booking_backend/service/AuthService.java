package com.telusko.My_auditorium_booking_backend.service;

import com.telusko.My_auditorium_booking_backend.dto.request.LoginRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.request.RegisterRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.AuthResponseDto;
import com.telusko.My_auditorium_booking_backend.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface AuthService {
    AuthResponseDto register(RegisterRequestDto registerRequest);

    AuthResponseDto login(LoginRequestDto loginRequest);

    UserResponseDto getMyProfile(String email);
}
