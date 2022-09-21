package com.assignment.openapi.web.searchcomp1.service;


import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchResponse;

/**
 * 검색과 관련된 동작을 정의한 인터페이스
 */
public interface SearchService<T> {
    SearchResponse<T> getContentsList(
            String uri, String queryString);
}
