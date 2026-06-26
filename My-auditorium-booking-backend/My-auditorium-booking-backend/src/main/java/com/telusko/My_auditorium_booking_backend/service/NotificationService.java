package com.telusko.My_auditorium_booking_backend.service;

import com.telusko.My_auditorium_booking_backend.dto.response.NotificationResponseDto;
import com.telusko.My_auditorium_booking_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    void createNotification(User recipient, String message);
    Page<NotificationResponseDto> getMyNotifications(String email, Pageable pageable);
    void markAsRead(Long notificationId, String email);
}
