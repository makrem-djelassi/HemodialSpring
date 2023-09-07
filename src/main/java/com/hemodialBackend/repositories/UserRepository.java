package com.hemodialBackend.repositories;

import com.hemodialBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String user);
    User findByResetPasswordToken(String token);

}
