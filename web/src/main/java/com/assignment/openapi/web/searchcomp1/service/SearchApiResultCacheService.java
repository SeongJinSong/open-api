package com.assignment.openapi.web.searchcomp1.service;

import com.assignment.openapi.core.redis.service.ApiResultCacheService;
import com.assignment.openapi.web.searchcomp1.repository.SearchResponseDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchApiResultCacheService implements ApiResultCacheService {
    private final SearchResponseDao searchResponseDao;
    public Object getApiResultCache(String key) {
        return searchResponseDao.findById(key);
    }

    @Override
    public void saveApiResultCache(String key, Object response) {
        searchResponseDao.addItem(response, key);
    }
}
