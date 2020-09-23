package com.intuit.rateLimiter.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@ConfigurationProperties()
@Component
public class ConfigProperties {

    private java.util.Map<String, Integer> rate = new HashMap<>();  // it will store all properties start with app
    public Map<String, Integer> getRate() {
        return rate;
    }
    public void setRate(Map<String, Integer> rate) {
        this.rate = rate;
    }
}