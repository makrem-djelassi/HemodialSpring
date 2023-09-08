package com.hemodialBackend.controllers;

import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.UserRepository;
import com.hemodialBackend.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/forgetPassword/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ForgetPasswordController {

    @Value("${spring.mail.username}")
    private String email;
    private final UserService userService;

    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository usersRepository;

    @PostMapping(value = "")
    public ResponseEntity<String> resetPassword(HttpServletRequest request){
        String email=request.getParameter("email");

        User user = this.usersRepository.findByEmail(email).orElse(null);

        if(user == null)
        {
            JSONObject entity = new JSONObject();
            entity.appendField("message", "User not found");
            return new ResponseEntity<>(entity.toJSONString(), HttpStatus.NOT_FOUND);
        }

        RandomString randomString=new RandomString(45);
        String generatedToken=randomString.nextString();
        userService.updateResetPasswordToken(generatedToken,email);


        //send email
        try {
            sendEMail(user);
        } catch ( MessagingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        JSONObject entity = new JSONObject();
        entity.appendField("message", "check your email to reset your password");
        return new ResponseEntity<>(entity.toJSONString(), HttpStatus.OK);
    }

    private void sendEMail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = email;
        String senderName = "Hemodial";



        String subject = "Changement de mot de passe";
        String content = "Cher [[name]],<br>"

                + "Veuillez cliquer sur le lien ci-dessous pour votre mot de passe:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">CHANGER</a></h3>"
                + "Merci,<br>"
                + "ProShift.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getUsername());
        String changePasswordURL = "http://localhost:4200/authentication/locked/" + user.getResetPasswordToken();
        content = content.replace("[[URL]]", changePasswordURL);
        helper.setText(content, true);
        mailSender.send(message);

    }

    @PostMapping("resetPassword")
    public ResponseEntity<String> resetNewPassword(@Param(value="token") String token,
                                                   @Param(value="newPassword") String newPassword){
        User user=userService.getUserByResetPasswordToken(token);
        if(user==null){
            return new ResponseEntity<>("Token invalid", HttpStatus.BAD_REQUEST);
        } else{
            userService.updatePassword(user,newPassword);
            JSONObject entity = new JSONObject();
            entity.appendField("message", "Password updated");
            return new ResponseEntity<>(entity.toJSONString(), HttpStatus.OK);
        }
    }
}
