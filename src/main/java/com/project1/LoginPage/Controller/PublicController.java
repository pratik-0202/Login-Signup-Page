package com.project1.LoginPage.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project1.LoginPage.entity.Users;
import com.project1.LoginPage.service.*;
import com.project1.LoginPage.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Objects;

@RestController
public class    PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Users user){
        Users userInDB = userService.findByUserName(user.getUserName());
        if(userInDB == null){
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            String otp = OtpUtil.generateOPT();
            redisService.setValue(user.getEmail(), otp, 300L);
            try {
                redisService.setObject(user.getUserName(), user, 300L);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            emailService.sendEmail(user, otp);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }

    @PostMapping("/verify")
    public String verifyOTP(@RequestParam String email, @RequestParam String userName, @RequestParam String otp){
        String storedOtp = redisService.getValue(email);
        Users user = null;
        try {
            user = redisService.getObject(userName, Users.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(storedOtp != null && storedOtp.equals(otp)){
            userService.saveEntry(user);
            return "OTP verified!!";
        }
        return "Invalid OTP!!";
    }
}
