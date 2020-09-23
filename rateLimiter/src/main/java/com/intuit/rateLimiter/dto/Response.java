package com.intuit.rateLimiter.dto;

public class Response {

    private final long count;
    private final boolean isOk;

    public Response(long count, boolean isOk) {
        this.count = count;
        this.isOk = isOk;
    }

    public long getCount() {
        return count;
    }

    public boolean getisOk() {
        return isOk;
    }
}