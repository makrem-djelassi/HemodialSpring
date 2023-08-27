package com.hemodialBackend.authentication;

import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request){
        Optional<User> result = userRepository.findByEmail(request.getEmail());
        if (result.isEmpty()){
            return ResponseEntity.ok(authenticationService.registerUser(request));
        }
        else {
            String email = request.getEmail();
            return new ResponseEntity("Compte avec adresse " + email + " exist", HttpStatus.OK);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
