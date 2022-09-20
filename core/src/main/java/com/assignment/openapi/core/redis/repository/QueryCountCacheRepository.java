package com.assignment.openapi.core.redis.repository;

import com.assignment.openapi.core.redis.domain.QueryCountCache;
import org.springframework.data.repository.CrudRepository;

public interface QueryCountCacheRepository extends CrudRepository<QueryCountCache, String> {
}
