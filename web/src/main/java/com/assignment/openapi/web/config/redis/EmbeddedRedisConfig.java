package com.assignment.openapi.web.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Configuration
@Slf4j
public class EmbeddedRedisConfig {
    @Value("${spring.redis.database.port}")
    private int redisPort;
    @Value("${spring.redis.embedded.maxmemory}")
    private String maxMemory;

    private RedisServer redisServer;

    public EmbeddedRedisConfig() {
        log.warn("Embedded Redis Constructor");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(new StringRedisSerializer());// value 깨지면 이걸로 바꿔보자
        return redisTemplate;
    }

    @Bean
    public RedisServer redisServer() {
        return redisServer;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(BytesToAtomicIntegerConverter converter){
        return new RedisCustomConversions(List.of(converter));
    }

    @PostConstruct
    public void startServer() {
        redisServer = RedisServer.builder()
                .port(redisPort)
                .setting("maxmemory " + maxMemory)
                .build();
        try {
            redisServer.start();
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
    }
}
