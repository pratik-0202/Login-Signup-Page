package com.project1.LoginPage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.LoginPage.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public String getValue(String key){
        try{
            return (String) redisTemplate.opsForValue().get(key);
        }
        catch (Exception e){
            log.error("Exception", e);
            return null;
        }
    }

    public void setValue(String key, String otp, Long ttl){
        try{
            redisTemplate.opsForValue().set(key, otp, ttl, TimeUnit.SECONDS);
        }
        catch (Exception e){
            log.error("Exception", e);
        }
    }

    public <T> void setObject(String key, T obj, long ttl) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(obj);
        redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
    }

    public <T> T getObject(String key, Class<T> Users) throws JsonProcessingException{
        Object json = redisTemplate.opsForValue().get(key);
        if(json == null){
            return null;
        }
        return objectMapper.readValue(json.toString(), Users);
    }
}
