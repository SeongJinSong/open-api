package com.assignment.openapi.web.searchcomp1.repository;

import com.assignment.openapi.core.redis.repository.ResponseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class SearchResponseDao implements ResponseDao {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void addItem(Object response, String key) {
        redisTemplate.opsForValue().set(key, response);
        redisTemplate.expire(key, 10, TimeUnit.SECONDS);
    }

    public Object findById(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
