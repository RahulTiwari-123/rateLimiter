package com.intuit.rateLimiter.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {

    final Logger logger = LoggerFactory.getLogger(RedisRepository.class);
    private HashOperations hashOperations;
    @Value("${ttl}")
    private int ttl;
    @Autowired
    private RedisTemplate< String, Object > redisTemplate;

    @PostConstruct
    public void initialize(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save( final String key, final Integer value ) {
        redisTemplate.opsForValue().set( key, value ,ttl, TimeUnit.MINUTES );
    }

    public String getHash( final String hash,String key ) {
        return (String)hashOperations.get(hash,key);
    }

    public Long removeHash( final String hash,String key ) {
        return  hashOperations.delete(hash,key);
    }

    public void putHash( final String hash,final String key, final String value ) {
        hashOperations.putIfAbsent(hash, key, value );
    }

    public Map<String,String> getAllFromHash(final String hash) {
        return hashOperations.entries(hash);
    }


    public Object getValue( final String key ) {
        return redisTemplate.opsForValue().get( key );
    }

    public void setValue( final String key, final Integer value ) {
        redisTemplate.opsForValue().set( key, value );
    }
}
