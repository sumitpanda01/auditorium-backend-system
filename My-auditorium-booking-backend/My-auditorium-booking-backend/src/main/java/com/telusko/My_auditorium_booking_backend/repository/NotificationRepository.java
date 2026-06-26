package com.telusko.My_auditorium_booking_backend.repository;

import com.telusko.My_auditorium_booking_backend.model.Notification;
import com.telusko.My_auditorium_booking_backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Page<Notification> findByRecipient(User recipient, Pageable pageable);
}
