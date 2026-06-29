package com.telusko.My_auditorium_booking_backend.service.impl;

import com.telusko.My_auditorium_booking_backend.dto.request.HallRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.HallResponseDto;
import com.telusko.My_auditorium_booking_backend.exception.BadRequestException;
import com.telusko.My_auditorium_booking_backend.model.Hall;
import com.telusko.My_auditorium_booking_backend.model.User;
import com.telusko.My_auditorium_booking_backend.repository.HallRepository;
import com.telusko.My_auditorium_booking_backend.repository.UserRepository;
import com.telusko.My_auditorium_booking_backend.service.HallService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceimpl  implements HallService{

    private final HallRepository hallRepository;
    private final UserRepository userRepository;

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

    @Override
    public Page<HallResponseDto> getAllHalls(Pageable pageable, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new BadRequestException("User not found"));

        return hallRepository.findByCreatedBy(admin,pageable)
                .map(this :: mapToResponse);
    }

    @Override
    public HallResponseDto updateHall(Long id, HallRequestDto request, String adminEmail) {

        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Hall not found"));

        if(!hall.getCreatedBy().getEmail().equals(adminEmail)){
            throw new BadRequestException("Unauthorized to update this hall");
        }

        hall.setName(request.getName());
        hall.setLocation(request.getLocation());
        hall.setCapacity(request.getCapacity());
        hall.setAmenities(request.getAmenities());
        hall.setDescription(request.getDescription());

        return mapToResponse(hallRepository.save(hall));
    }

    @Override
    @Transactional
    public void deleteHall(Long id, String email) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Hall not found"));

        if(!hall.getCreatedBy().getEmail().equals(email)){
            throw new BadRequestException("Unauthorized to delete this hall");
        }

        //cascade soft-delete only future bookings for this hall( preserve history)
//        LocalDateTime now = LocalDateTime.now();
//        List<Booking> bookings = bookingRepository.findByHall(hall);
//        List<Booking> futureBookings = bookings.stream()
//                .filter(b -> b.getEndTime().isAfter(now))
//                .toList();
//
//        if (!futureBookings.isEmpty()) {
//            bookingRepository.deleteAll(futureBookings);
//        }

        hallRepository.delete(hall);

    }

    @Override
    public Page<HallResponseDto> getAllEnabledHalls(Pageable pageable) {
        return hallRepository.findByEnabledTrue(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public HallResponseDto getHallById(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Hall not found"));

        return mapToResponse(hall);
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
