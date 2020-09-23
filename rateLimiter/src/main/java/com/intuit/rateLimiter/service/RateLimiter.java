package com.intuit.rateLimiter.service;

import com.intuit.rateLimiter.configurations.ConfigProperties;
import com.intuit.rateLimiter.dto.Response;
import com.intuit.rateLimiter.dto.User;
import com.intuit.rateLimiter.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.time.Instant;
import java.util.List;

@Service
public class RateLimiter {

    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private UserService userService;


    public Response checkValidity(String customerId) throws Exception{
       // System.out.println(configProperties.getC1());
        //Key = custId+Minute
        String key = customerId+":"+ Instant.now().getEpochSecond()/configProperties.getRate().get("windowInSeconds");
        String val = (String) redisRepository.getValue(key);
        Integer value=0 ;
        Jedis jedis = new Jedis("localhost");

        boolean flag=true;
        Integer limit = configProperties.getRate().get(customerId);
        User user = userService.getOne(customerId);
        if(limit == null && user != null) {
            limit = user.getLimit();
        }else{
            limit = 10;
        }
        if(val != null) {
            if(Integer.parseInt(val)<limit) {
                value = Integer.parseInt(val) + 1;
            }else{
                System.out.println("Exceeded rate limit for :"+customerId+ ": value found :"+val);
                flag = false;
            }
        }else{
            value= 1;
         }
        if(flag) {
            Transaction transaction = jedis.multi();
            transaction.set(key, value.toString());
            List<Object> list = transaction.exec();
            transaction.close();
            jedis.close();
        }
       // System.out.println(list);
        Response response = new Response(value,flag);
        return response;
    }



}
