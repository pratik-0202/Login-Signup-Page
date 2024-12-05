package com.project1.LoginPage.Controller;

import com.project1.LoginPage.entity.Users;
import com.project1.LoginPage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping
    public List<Users> getAll(){
        return userService.getAll();
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable String userName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users userInDB = userService.findByUserName(authentication.getName());
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(encoder.encode(user.getPassword()));
        userService.saveEntry(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users userInDB = userService.findByUserName(userName);
        if(userInDB != null){
            userService.deleteById(userInDB.getId());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
    }


}
