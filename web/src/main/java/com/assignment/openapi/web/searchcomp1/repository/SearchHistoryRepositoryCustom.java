package com.assignment.openapi.web.searchcomp1.repository;

import com.assignment.openapi.core.search.domain.SearchRank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchHistoryRepositoryCustom {
    Page<SearchRank> findTopSearchHistory(Pageable pageable);
}
