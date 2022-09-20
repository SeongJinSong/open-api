package com.assignment.openapi.core.redis.service;

import com.assignment.openapi.core.redis.domain.QueryCountCache;
import com.assignment.openapi.core.redis.repository.QueryCountCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "spring.redis.database", value = "host")
public class QueryCountService {
    private final QueryCountCacheRepository queryCountCacheRepository;

    @PostConstruct
    public void initialize(){
        /*TODO
            redis가 뜰때, 집계함수를 통해 count 집계 -> 과연 필요할까?
            레디스에 카운트 센것 주기적으로 db에 저장 혹은 정합성 검사
         */
    }

    public void inCreateCount(String query){
        queryCountCacheRepository.findById(query)
                .ifPresentOrElse(cache->queryCountCacheRepository.save(cache.increaseCount())
                , ()->queryCountCacheRepository.save(QueryCountCache.builder().query(query).build().increaseCount()));
    }
}
