package com.telusko.My_auditorium_booking_backend.repository;

import com.telusko.My_auditorium_booking_backend.model.Hall;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {

    boolean existsByName(@NotBlank(message = "Hall name is required") String name);
}
