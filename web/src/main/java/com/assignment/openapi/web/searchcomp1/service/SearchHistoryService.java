package com.assignment.openapi.web.searchcomp1.service;

import com.assignment.openapi.core.search.domain.SearchRank;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 검색 히스토리와 관련된 동작을 수행
 * 예) 검색 request 저장, 인기있는 검색어 등
 */
public interface SearchHistoryService {
    void saveRequest(SearchRequest request);
    Page<SearchRank> getPopularSearchWord(Pageable pageable);
}
