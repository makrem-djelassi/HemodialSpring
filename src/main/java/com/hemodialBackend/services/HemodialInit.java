package com.hemodialBackend.services;

import com.hemodialBackend.models.Caisse;
import com.hemodialBackend.models.Role;
import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.CaisseRepository;
import com.hemodialBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HemodialInit implements CommandLineRunner {
        @Autowired
        private UserRepository userRepository;
@Autowired
private CaisseService caisseService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
            String adminUsername = "admin";
            if (!userRepository.existsByUsername(adminUsername)) {
                User adminUser = new User();
                adminUser.setUsername(adminUsername);
                adminUser.setPassword(passwordEncoder.encode("admin")); // Replace with your desired admin password
                adminUser.setRole(Role.Admin);
                adminUser.setEmail("admin@admin.com");
                userRepository.save(adminUser);
            }
            if(caisseService.getCaisseCount()==0){
                Caisse caisseNew = new Caisse();
                caisseNew.setCode("CNAM");
                caisseNew.setLabel("Caisse nationnal d'assurance maladie");
                caisseService.save(caisseNew);
            }
        }
    }


