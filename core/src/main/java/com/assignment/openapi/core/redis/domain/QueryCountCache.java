package com.assignment.openapi.core.redis.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("query-count")
@ToString
//TODO zset 기반으로 QueryCountService 에 만들어서 그걸로 순위를 결정하게 하자
public class QueryCountCache {
    @Id
    private String query;
    @Builder.Default
    private AtomicLong counter = new AtomicLong();
    public QueryCountCache increaseCount(){
        counter.incrementAndGet();
        log.info("#### count:"+counter.get());
        return this;
    }
}
