package com.telusko.My_auditorium_booking_backend.controller;

import com.telusko.My_auditorium_booking_backend.dto.response.NotificationResponseDto;
import com.telusko.My_auditorium_booking_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Page<NotificationResponseDto>> getMyNotification(
            Authentication authentication,
            @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC)Pageable pageable
            ){
        return ResponseEntity.ok(notificationService.getMyNotifications(authentication.getName(),pageable));
    }

    @PutMapping("{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id, Authentication authentication){
        notificationService.markAsRead(id,authentication.getName());
        return ResponseEntity.ok().build();
    }
}
