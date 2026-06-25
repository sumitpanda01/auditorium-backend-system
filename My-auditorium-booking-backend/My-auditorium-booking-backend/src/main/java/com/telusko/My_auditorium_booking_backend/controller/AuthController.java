package com.telusko.My_auditorium_booking_backend.controller;

import com.telusko.My_auditorium_booking_backend.dto.request.LoginRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.request.RegisterRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.AuthResponseDto;
import com.telusko.My_auditorium_booking_backend.dto.response.UserResponseDto;
import com.telusko.My_auditorium_booking_backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth") // align with frontend calls
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @Valid @RequestBody RegisterRequestDto request
    ){
        System.out.println("REGISTER ENDPOINT HIT");

        AuthResponseDto response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ){
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(
            Authentication authentication
    ){
        String email = authentication.getName();
        UserResponseDto response = authService.getMyProfile(email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("csrf_token")
    public CsrfToken getCsrfToken(HttpServletRequest request){

        return (CsrfToken) request.getAttribute("_csrf");
    }
}
