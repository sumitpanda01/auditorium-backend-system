package com.telusko.My_auditorium_booking_backend.service;

import com.telusko.My_auditorium_booking_backend.model.User;
import com.telusko.My_auditorium_booking_backend.model.UserPrincipal;
import com.telusko.My_auditorium_booking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repo.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));

        return new UserPrincipal(user);
    }
}
