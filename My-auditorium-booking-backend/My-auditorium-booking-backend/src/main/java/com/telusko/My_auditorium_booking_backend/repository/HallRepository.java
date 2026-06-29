package com.telusko.My_auditorium_booking_backend.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.telusko.My_auditorium_booking_backend.model.Hall;
import com.telusko.My_auditorium_booking_backend.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {

    boolean existsByName(@NotBlank(message = "Hall name is required") String name);

    Page<Hall> findByCreatedBy(User admin, Pageable pageable);

    Page<Hall> findByEnabledTrue(Pageable pageable);
}
