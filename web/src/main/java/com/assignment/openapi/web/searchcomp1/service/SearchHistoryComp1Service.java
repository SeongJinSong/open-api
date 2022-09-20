package com.assignment.openapi.web.searchcomp1.service;

import com.assignment.openapi.core.search.domain.SearchRank;
import com.assignment.openapi.core.search.comp1.SearchHistory;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchRequest;
import com.assignment.openapi.web.searchcomp1.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Host : dapi.kakao.com
 * Authorization: KakaoAK ${REST_API_KEY:9ccac738a217f2aa9d006c01900809cc}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchHistoryComp1Service implements SearchHistoryService {
    private final SearchHistoryRepository searchRepository;
    private final Executor threadPoolTaskExecutor;
    @Override
    public void saveRequest(SearchRequest request) {
        CompletableFuture.runAsync(() -> searchRepository.save(SearchHistory.builder()
                .query(request.getQuery())
                .page(request.getPage())
                .size(request.getSize())
                .sort(request.getSort())
                .build()), threadPoolTaskExecutor);
    }

    @Override
    public Page<SearchRank> getPopularSearchWord(Pageable pageable) {
        return searchRepository.findTopSearchHistory(pageable);
    }
}
