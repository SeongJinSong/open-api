package com.assignment.openapi.core.redis.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value="api-result", timeToLive = 5L)
@ToString
public class ApiResultCache {
    @Id
    private String request;
    private Object response;
}
