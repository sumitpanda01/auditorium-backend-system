package com.telusko.My_auditorium_booking_backend.controller;

import com.telusko.My_auditorium_booking_backend.dto.request.HallRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.HallResponseDto;
import com.telusko.My_auditorium_booking_backend.service.HallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallResponseDto> createHall(
            @Valid @RequestBody HallRequestDto request,
            Authentication authentication
    ){
        String email = authentication.getName();
        return new ResponseEntity<>(hallService.createHall(request,email), HttpStatus.CREATED);
    }
}
