package com.hemodialBackend.authentication;

import com.hemodialBackend.config.JwtService;
import com.hemodialBackend.models.Clinique;
import com.hemodialBackend.models.Role;
import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.CliniqueRepository;
import com.hemodialBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CliniqueRepository cliniqueRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


//    public ResponseEntity<String> register(RegisterRequest request) {
//        var user= User.builder()
//                .prenom(request.getPrenom())
//                .nom(request.getNom())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .build();
//
//        switch(request.getRole()){
//            case "PATIENT":
//                user.setRole(Role.PATIENT);
//            break;
//            case "DOCTEUR":
//                user.setRole(Role.DOCTEUR);
//            break;
//            case "ADMIN":
//                user.setRole(Role.Admin);
//            break;
//            default:
//                user.setRole(Role.PATIENT);
//        }
//        User savedUser=userRepository.save(user);
//        if(savedUser!=null){
//            return ResponseEntity.ok("Utilisateur enregistre avec succes!");
//        }else
//        return ResponseEntity.badRequest().body("Email exist deja");
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

    public ResponseEntity<String> registerUser(RegisterRequest request) {

        User newUser=new User();
        newUser.setNom(request.getNom());
        newUser.setPrenom(request.getPrenom());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setDateNaissance(request.getDateNaissance());
        newUser.setPhone(request.getPhone());
        newUser.setRole(Role.Doctor);

        User savedUser=userRepository.save(newUser);

        User user = userRepository.findByEmail(savedUser.getEmail()).orElse(null);
        Clinique newClinique=new Clinique();
        newClinique.setGerant(user);
        Clinique savedClinique = cliniqueRepository.save(newClinique);

        if(savedUser!=null && savedClinique!=null){
            return ResponseEntity.ok("Compte enregistre avec succes!");
        }else
            return ResponseEntity.badRequest().body("User not created");
    }

}
