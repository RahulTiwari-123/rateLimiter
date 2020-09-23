package com.intuit.rateLimiter.controller;

import com.google.gson.Gson;
import com.intuit.rateLimiter.dto.User;
import com.intuit.rateLimiter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class UserController {

    UserService userService;
    private Gson gson;

    public void initialize(){
        gson = new Gson();
    }
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String all() {
        Map<String,String> allUsers = userService.all();
        if(allUsers !=null && !allUsers.isEmpty())
         return gson.toJson(userService.all());
        else
            return "{}";
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<String> newUser(@RequestBody User newUser,@PathVariable String id) {
        userService.createUser(newUser);
         return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity one(@PathVariable String id) {

        User user = userService.getOne(id);
        if(user !=null)
        return   new ResponseEntity (userService.getOne(id), HttpStatus.OK);
        else
            return  new ResponseEntity ("{}", HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
   public ResponseEntity<String> replaceUser(@RequestBody User newUser, @PathVariable String id) {

         userService.replaceUser(newUser);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public  ResponseEntity<String> deleteUser(@PathVariable String id) {
     userService.deleteUser(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }
}
