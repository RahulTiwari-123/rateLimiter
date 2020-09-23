package com.intuit.rateLimiter.dto;

public class User {

    private String id;
    private int limit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", limit=" + limit +
                '}';
    }
}
