package com.assignment.openapi.web.searchcomp1.service;

import com.assignment.openapi.core.search.domain.SearchRank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRankService {
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void addSearchRequest(String query){
        double score = 0.0;
        try{
            redisTemplate.opsForZSet().incrementScore("search-ranking", query, 1);
        }catch (Exception e){
            log.info(String.valueOf(e));
        }
        //score를 1씩 올려준다.
        redisTemplate.opsForZSet().incrementScore("search-ranking", query, score);
    }

    @Transactional(readOnly = true)
    public List<SearchRank> searchRankList(){
        String key = "search-ranking";
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 10개 보여줌
        return typedTuples != null ? typedTuples
                .stream()
                .map(val -> new SearchRank(val.getValue(), Objects.requireNonNull(val.getScore()).intValue()))
                .collect(Collectors.toList()) : null;
    }
}
