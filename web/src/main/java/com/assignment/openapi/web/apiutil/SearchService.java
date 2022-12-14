package com.assignment.openapi.web.apiutil;


import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Response;

/**
 * 검색과 관련된 동작을 정의한 인터페이스
 */
public interface SearchService<T> {
    SearchComp1Response<T> getContentsList(
            String uri, String queryString);
}
