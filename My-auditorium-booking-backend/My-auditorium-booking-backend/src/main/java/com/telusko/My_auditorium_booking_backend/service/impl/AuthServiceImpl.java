package com.telusko.My_auditorium_booking_backend.service.impl;

import com.telusko.My_auditorium_booking_backend.dto.request.LoginRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.request.RegisterRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.AuthResponseDto;
import com.telusko.My_auditorium_booking_backend.dto.response.UserResponseDto;
import com.telusko.My_auditorium_booking_backend.exception.BadRequestException;
import com.telusko.My_auditorium_booking_backend.mapper.AuthMapper;
import com.telusko.My_auditorium_booking_backend.model.User;
import com.telusko.My_auditorium_booking_backend.repository.UserRepository;
import com.telusko.My_auditorium_booking_backend.service.AuthService;
import com.telusko.My_auditorium_booking_backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final JwtService jwtUtil;

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new com.telusko.My_auditorium_booking_backend.exception.BadRequestException("Email already registered");
        }
        if(userRepository.existsByPhone(registerRequest.getPhone())){
            throw new BadRequestException("Phone already registered");
        }

        User user = authMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail());

        AuthResponseDto responseDto = authMapper.toAuthResponse(savedUser);

        responseDto.setToken(token);

        return responseDto;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new BadRequestException("Invalid email or password");
        }
        if(!user.isEnabled()){
            throw new BadRequestException("Account not approved yet");
        }
        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponseDto response = authMapper.toAuthResponse(user);
        response.setToken(token);
        return response;
    }

    @Override
    public UserResponseDto getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("user not found"));

        return authMapper.toUserResponse(user);
    }
}
