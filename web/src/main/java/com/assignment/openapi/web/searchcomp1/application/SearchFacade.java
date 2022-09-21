package com.assignment.openapi.web.searchcomp1.application;

import com.assignment.openapi.core.redis.service.QueryCountService;
import com.assignment.openapi.core.search.domain.SearchRank;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Request;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Response;
import com.assignment.openapi.web.searchcomp1.service.SearchComp1Service;
import com.assignment.openapi.web.searchcomp1.service.SearchHistoryService;
import com.assignment.openapi.web.searchcomp1.service.SearchRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchFacade<T> {
    private final SearchComp1Service searchService;
    private final SearchRankService searchRankService;
    private final SearchHistoryService searchHistoryService;
    private final QueryCountService queryCountService;
    public SearchComp1Response<T> getContentsListV1(String contentsType, @Valid SearchComp1Request request) {
        // TODO 카카오 api에서 실패하는 경우 네이버 api를 사용하는 것을 구현하기위해 MSA에서 fault tolerance로 많이 사용하는tolaresilience4j를 적용해보자.
        searchHistoryService.saveRequest(request);

        //레디스에 카운트 설정
        //TODO : LongAdder, Accumulator 적용해보기
        queryCountService.inCreateCount(request.getQuery());

        return searchService.getContentsList(makeURI("v2/search/",contentsType), makeQueryString(request));
    }

    public SearchComp1Response<T> getContentsListV2(String contentsType, @Valid SearchComp1Request request) {
        searchHistoryService.saveRequest(request);

        //TODO 조회와 삽입이 분리되면 동시성 문제가 발생할 수 있어 lua-script 기반으로 돌리는 방법도 찾아보자
        searchRankService.addSearchRequest(request.getQuery());
        return searchService.getContentsList(makeURI("v2/search/",contentsType), makeQueryString(request));
    }

    public Page<SearchRank> getPopularSearchWord(Pageable pageable){
        return searchHistoryService.getPopularSearchWord(pageable);
    }

    public List<SearchRank> searchRankList(){
        return searchRankService.searchRankList();
    }

    private String makeQueryString(SearchComp1Request request){
        return "query="+request.getQuery()+"&sort="+request.getSort()+"&page="+request.getPage()+"&size="+request.getSize();
    }
    private String makeURI(String uri, String contentsType){
        return uri+contentsType;
    }
}
