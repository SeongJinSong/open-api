package com.assignment.openapi.web.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.ResourceUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.redis.database", value = "host")
@EnableCaching
@RequiredArgsConstructor
public class RedissonSpringDataConfig {
    @Value("${spring.redis.database.host}")
    private String redisHost;
    @Value("${spring.redis.database.port}")
    private int port;
    @Value("${spring.redis.files}")
    private String redisConfigUrl;

    @Value("${spring.redis.cache.files:cache-config.yml}")
    private String cacheConfigUrl;


    private RedissonClient client;

    @Autowired
    private RedisServer server;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        return client;
    }

    @PostConstruct
    public void initRedissonClient() throws IOException {
        File redisConfig = ResourceUtils.getFile("classpath:" + redisConfigUrl);
        Config config = Config.fromYAML(redisConfig);

        client = Redisson.create(config);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        String cacheConfig = "classpath:" + cacheConfigUrl;
        return new RedissonSpringCacheManager(redissonClient, cacheConfig);
    }
}
