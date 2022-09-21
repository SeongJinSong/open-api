package com.assignment.openapi.web.searchcomp2.application;

import com.assignment.openapi.core.redis.service.QueryCountService;
import com.assignment.openapi.web.searchcomp1.service.SearchHistoryService;
import com.assignment.openapi.web.searchcomp1.service.SearchRankService;
import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Request;
import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Response;
import com.assignment.openapi.web.searchcomp2.service.SearchComp2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class SearchFacade2<T> {
    private final SearchComp2Service searchService;
    private final SearchRankService searchRankService;
    private final SearchHistoryService searchHistoryService;
    private final QueryCountService queryCountService;

    public SearchComp2Response<T> getContentsListV2(String contentsType, @Valid SearchComp2Request request) {
//        searchHistoryService.saveRequest(request);

        //TODO 조회와 삽입이 분리되면 동시성 문제가 발생할 수 있어 lua-script 기반으로 돌리는 방법도 찾아보자
        searchRankService.addSearchRequest(request.getQuery());
        return searchService.getContentsList(makeURI("v1/search/",contentsType), makeQueryString(request));
    }

    private String makeQueryString(SearchComp2Request request){
        return "query="+request.getQuery()+"&sort="+request.getSort()+"&start="+(request.getStart()==null?"0":request.getStart())+"&display="+(request.getDisplay()==null?"0":request.getDisplay());
    }
    private String makeURI(String uri, String contentsType){
        return uri+contentsType;
    }
}
