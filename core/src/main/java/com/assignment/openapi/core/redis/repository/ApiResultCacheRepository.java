package com.assignment.openapi.core.redis.repository;

import com.assignment.openapi.core.redis.domain.ApiResultCache;
import org.springframework.data.repository.CrudRepository;

public interface ApiResultCacheRepository extends CrudRepository<ApiResultCache, String> {
}
