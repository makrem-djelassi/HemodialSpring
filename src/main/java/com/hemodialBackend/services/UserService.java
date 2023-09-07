package com.hemodialBackend.services;

import com.hemodialBackend.config.JwtService;
import com.hemodialBackend.models.Role;
import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public void updateResetPasswordToken(String token,String email) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByEmail(email);
        if(user!=null) {
            user.get().setResetPasswordToken(token);
            try{
                userRepository.save(user.get());
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }else {
            throw new UsernameNotFoundException("Utilisateur non trouve");
        }
    }

    public User getUserByResetPasswordToken(String resetPasswordToken){
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword=passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        try{
            userRepository.save(user);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }



}
