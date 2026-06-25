package com.telusko.My_auditorium_booking_backend.service;

import com.telusko.My_auditorium_booking_backend.dto.request.HallRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.HallResponseDto;
import jakarta.validation.Valid;

public interface HallService {
    HallResponseDto createHall(@Valid HallRequestDto request, String email);
}
