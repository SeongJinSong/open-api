package com.assignment.openapi.web.searchcomp1.application;

import com.assignment.openapi.core.redis.service.QueryCountService;
import com.assignment.openapi.core.search.domain.SearchRank;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchRequest;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchResponse;
import com.assignment.openapi.web.searchcomp1.service.SearchHistoryService;
import com.assignment.openapi.web.searchcomp1.service.SearchRankService;
import com.assignment.openapi.web.searchcomp1.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchFacade<T> {
    private final SearchService searchService;
    private final SearchRankService searchRankService;
    private final SearchHistoryService searchHistoryService;
    private final QueryCountService queryCountService;
    public SearchResponse<T> getContentsList(HttpServletRequest httpservletRequest, @Valid SearchRequest request) {
        // TODO 카카오 api에서 실패하는 경우 네이버 api를 사용하는 것을 구현하기위해 MSA에서 fault tolerance로 많이 사용하는tolaresilience4j를 적용해보자.
        searchHistoryService.saveRequest(request);

        //레디스에 카운트 설정
        //TODO : LongAdder, Accumulator 적용해보기
        queryCountService.inCreateCount(request.getQuery());

        //TODO 조회와 삽입이 분리되면 동시성 문제가 발생할 수 있어 lua-script 기반으로 돌리는 방법도 찾아보자
        searchRankService.addSearchRequest(request.getQuery());
        return searchService.getContentsList(httpservletRequest.getRequestURI(), makeQueryString(request));
    }

    @GetMapping("/rank")
    public Page<SearchRank> getPopularSearchWord(Pageable pageable){
        return searchHistoryService.getPopularSearchWord(pageable);
    }

    @GetMapping("/rank-new")
    public List<SearchRank> searchRankList(){
        return searchRankService.searchRankList();
    }

    private String makeQueryString(SearchRequest request){
        return "query="+request.getQuery()+"&sort="+request.getSort()+"&page="+request.getPage()+"&size="+request.getSize();
    }
}
