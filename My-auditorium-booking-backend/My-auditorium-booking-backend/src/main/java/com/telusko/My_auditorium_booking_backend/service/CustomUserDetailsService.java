//package com.telusko.My_auditorium_booking_backend.service;
//
//import com.telusko.My_auditorium_booking_backend.repository.UserRepository;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(user -> User
//                        .withUsername(user.getEmail())
//                        .password(user.getPassword())
//                        .roles(user.getRole().name())
//                        .disabled(!user.isEnabled())
//                        .build()
//                )
//                .orElseThrow( () ->
//                        new UsernameNotFoundException("User not found"));
//    }
//}
