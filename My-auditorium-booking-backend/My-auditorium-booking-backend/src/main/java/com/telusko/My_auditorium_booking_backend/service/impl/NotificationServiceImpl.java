package com.telusko.My_auditorium_booking_backend.service.impl;

import com.telusko.My_auditorium_booking_backend.dto.response.NotificationResponseDto;
import com.telusko.My_auditorium_booking_backend.exception.BadRequestException;
import com.telusko.My_auditorium_booking_backend.model.Notification;
import com.telusko.My_auditorium_booking_backend.model.User;
import com.telusko.My_auditorium_booking_backend.repository.NotificationRepository;
import com.telusko.My_auditorium_booking_backend.repository.UserRepository;
import com.telusko.My_auditorium_booking_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public void createNotification(User recipient, String message) {
        Notification notification = Notification.builder()
                .recipient(recipient)
                .message(message)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public Page<NotificationResponseDto> getMyNotifications(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        return notificationRepository.findByRecipient(user, pageable)
                .map(notif -> NotificationResponseDto.builder()
                        .id(notif.getId())
                        .message(notif.getMessage())
                        .isRead(notif.isRead())
                        .createdAt(notif.getCreatedAt())
                        .build());
    }

    @Override
    public void markAsRead(Long notificationId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BadRequestException("Notification not found"));

        if(!notification.getRecipient().getId().equals(user.getId())){
            throw new BadRequestException("Unauthorized to access this notification");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
