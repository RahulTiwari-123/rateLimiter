package com.intuit.rateLimiter.service;

import com.google.gson.Gson;
import com.intuit.rateLimiter.dto.User;
import com.intuit.rateLimiter.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    RedisRepository redisRepository;

    private static final String hash= "users";
    private Gson gson;

    public UserService(RedisRepository redisRepository){
        this.redisRepository = redisRepository;
    }

    @PostConstruct
    public void initialize(){
        gson = new Gson();
    }

    public Map<String,String> all() {
        return redisRepository.getAllFromHash(hash);
    }

    public void createUser(User newUser) {
        redisRepository.putHash(hash,newUser.getId(),gson.toJson(newUser));
    }


    public User getOne( String id) {
        String user = redisRepository.getHash(hash,id);
        if(user !=null && !user.isEmpty())
            return gson.fromJson(user,User.class);
        else
            return  null;
    }


    public void replaceUser(  User newUser ) {

        redisRepository.putHash(hash,newUser.getId(),gson.toJson(newUser));
    }


    public void deleteUser( String id) {
        redisRepository.removeHash(hash,id);
    }




}
