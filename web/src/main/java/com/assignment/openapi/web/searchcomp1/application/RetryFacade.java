package com.assignment.openapi.web.searchcomp1.application;

import com.assignment.openapi.web.apiutil.SearchResponse;
import com.assignment.openapi.web.searchcomp1.Comp1AppUtils;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Request;
import com.assignment.openapi.web.searchcomp1.service.SearchComp1Service;
import com.assignment.openapi.web.searchcomp1.service.SearchHistoryService;
import com.assignment.openapi.web.searchcomp1.service.SearchRankService;
import com.assignment.openapi.web.searchcomp2.Comp2AppUtils;
import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Request;
import com.assignment.openapi.web.searchcomp2.service.SearchComp2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class RetryFacade {
    private final SearchHistoryService searchHistoryService;
    private final SearchRankService searchRankService;
    private final SearchComp1Service searchComp1ServiceService;

    public SearchResponse getContentsList(String contentsType, SearchComp1Request request) {
        searchHistoryService.saveRequest(request);
        searchRankService.addSearchRequest(request.getQuery());
        return searchComp1ServiceService.getComp1IfErrorRetryComp2Service(
                Comp1AppUtils.makeURI("v2/search/",contentsType),
                Comp1AppUtils.makeQueryString(request),
                Comp2AppUtils.makeURI("v1/search/",contentsType+".json"),
                Comp2AppUtils
                        .makeQueryString(SearchComp2Request.builder().query(request.getQuery())
                                .sort(makeComp2Sort(request.getSort()))
                                .display(request.getSize())
                                .start(1+ request.getPage()* request.getSize()).build())
        );
    }
    private String makeComp2Sort(String sort){
        if(sort.equals("accuracy"))return "sim";
        else if(sort.equals("recency"))return "date";
        return "sim";
    }
}
