package com.telusko.My_auditorium_booking_backend.repository;

import com.telusko.My_auditorium_booking_backend.model.Role;
import com.telusko.My_auditorium_booking_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAll();

    User findByName(String name);

    Page<User> findByRoleIn(List<Role> roles, Pageable pageable);

    List<User> findByRole(Role role);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
