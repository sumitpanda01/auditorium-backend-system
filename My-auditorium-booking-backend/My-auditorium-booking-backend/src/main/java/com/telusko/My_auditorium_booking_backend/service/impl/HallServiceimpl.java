package com.telusko.My_auditorium_booking_backend.service.impl;

import com.telusko.My_auditorium_booking_backend.dto.request.HallRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.HallResponseDto;
import com.telusko.My_auditorium_booking_backend.exception.BadRequestException;
import com.telusko.My_auditorium_booking_backend.model.Hall;
import com.telusko.My_auditorium_booking_backend.model.User;
import com.telusko.My_auditorium_booking_backend.repository.HallRepository;
import com.telusko.My_auditorium_booking_backend.repository.UserRepository;
import com.telusko.My_auditorium_booking_backend.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HallServiceimpl  implements HallService{

    private final HallRepository hallRepository;
    private UserRepository userRepository;

    @Override
    public HallResponseDto createHall(HallRequestDto request, String email) {
        if(hallRepository.existsByName(request.getName())){
            throw new BadRequestException("Hall already exists");
        }
        User admin = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User not found"));

        Hall hall = Hall.builder()
                .name(request.getName())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .amenities(request.getAmenities())
                .description(request.getDescription())
                .createdBy(admin)
                .enabled(true)
                .build();

        return mapToResponse(hallRepository.save(hall));
    }

    private HallResponseDto mapToResponse(Hall hall){
        return HallResponseDto.builder()
                .id(hall.getId())
                .name(hall.getName())
                .location(hall.getLocation())
                .capacity(hall.getCapacity())
                .amenities(hall.getAmenities())
                .description(hall.getDescription())
                .createdByName(hall.getCreatedBy().getName())
                .enabled(hall.isEnabled())
                .createdAt(hall.getCreatedAt())
                .build();
    }
}
