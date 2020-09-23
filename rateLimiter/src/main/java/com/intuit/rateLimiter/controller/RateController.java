package com.intuit.rateLimiter.controller;

import com.intuit.rateLimiter.dto.Response;
import com.intuit.rateLimiter.service.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RateController {

    @Autowired
    RateLimiter rateLimiter;


    @GetMapping("/limit")
    public ResponseEntity<Boolean> limit(@RequestParam(value = "id", defaultValue = "c1") String id) {
        ResponseEntity<Boolean> responseEntity;
        Response response=null;
        try {
            // throw new Exception();
            response = rateLimiter.checkValidity(id);
        }catch(Exception e){
           // return new ResponseEntity<>(response.getisOk(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Please retry after some time..");
        }
       responseEntity= new ResponseEntity<>(response.getisOk(), HttpStatus.OK);
        return  responseEntity;
    }
}
