package com.hemodialBackend.services;

import com.hemodialBackend.config.JwtService;
import com.hemodialBackend.models.Role;
import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
public User getUserAuthority(String token) {
    String email = jwtService.extractUsername(token.substring(7));
    return userRepository.findByEmail(email).orElse(null);
}
}
