package com.telusko.My_auditorium_booking_backend.service;

import com.telusko.My_auditorium_booking_backend.dto.request.HallRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.HallResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HallService {
    HallResponseDto createHall(@Valid HallRequestDto request, String email);

    Page<HallResponseDto> getAllHalls(Pageable pageable, String adminEmail);
}
