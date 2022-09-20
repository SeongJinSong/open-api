package com.assignment.openapi.core.redis.repository;

public interface ResponseDao {
    void addItem(Object response, String key);
    Object findById(String key);
}
