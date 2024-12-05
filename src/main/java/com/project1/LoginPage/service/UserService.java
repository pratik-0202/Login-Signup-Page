package com.project1.LoginPage.service;

import com.mongodb.DuplicateKeyException;
import com.project1.LoginPage.entity.Users;
import com.project1.LoginPage.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public void saveEntry(Users userName){
        try{
            userRepo.save(userName);
        }
        catch (DuplicateKeyException e){
            log.error("Email already exists!");
        }
    }

    public List<Users> getAll(){
        return userRepo.findAll();
    }

    public Optional<Users> findById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public Users findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    public String verify(Users user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUserName());
        }
        return "fail";
    }
}
