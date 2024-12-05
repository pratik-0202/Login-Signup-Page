package com.project1.LoginPage.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project1.LoginPage.entity.Users;
import com.project1.LoginPage.service.EmailService;
import com.project1.LoginPage.service.RedisService;
import com.project1.LoginPage.service.UserService;
import com.project1.LoginPage.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping
    public ResponseEntity<?> addAdmin(@RequestBody Users user){
        Users userInDB = userService.findByUserName(user.getUserName());
        if(userInDB == null){
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("ADMIN", "USER"));
            String otp = OtpUtil.generateOPT();
            redisService.setValue(user.getEmail(), otp, 300L);
            try{
                redisService.setObject(user.getUserName(), user, 300L);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            emailService.sendEmail(user, otp);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);

    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Users> list = userService.getAll();
        if(list != null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
