package com.telusko.My_auditorium_booking_backend.dto.response;


import com.telusko.My_auditorium_booking_backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String name;
    private String email;
    private String phone;
    private Role role;
    private String institute;
    private String department;
    private boolean enabled;
}
