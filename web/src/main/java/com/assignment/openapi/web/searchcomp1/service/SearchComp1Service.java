package com.assignment.openapi.web.searchcomp1.service;

import com.assignment.openapi.web.apiutil.SearchResponse;
import com.assignment.openapi.web.apiutil.SearchService;
import com.assignment.openapi.web.searchcomp1.Comp1AppUtils;
import com.assignment.openapi.web.searchcomp1.api.Comp1ApiService;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Response;
import com.assignment.openapi.web.searchcomp2.Comp2AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchComp1Service implements SearchService {
    private final SearchApiResultCacheService searchApiResultCacheService;
    private final Comp1ApiService apiService;

    @Override
    public SearchComp1Response getContentsList(String uri, String queryString) {
        String key = Comp1AppUtils.getComp1ApiRedisKey(uri, queryString);
        /*
            TODO
             1. @Cacheable을 적용하여 분기 삭제
             2. API자체를 캐싱하는건 Client단에서 어울릴만한 일이다. reactQuery가 그런비슷한 역할을 하기도한다.
             서버단에서는 뭔가 서버단에서 어울릴만한 캐싱 알고리즘을 찾아보아야 할 것 같다.
         */
        SearchComp1Response response = (SearchComp1Response)searchApiResultCacheService.getApiResultCache(key);
        if(response==null){
            //api 호출
            log.info("@@@@@@@ api call directly key={}", key);
            response = apiService.get(Comp1AppUtils.host, Comp1AppUtils.getUrlTemplate(uri, queryString));
            //레디스에 api 검색결과 저장
            searchApiResultCacheService.saveApiResultCache(key, response);
        }


        return response;
    }
    public SearchResponse getComp1IfErrorRetryComp2Service(String uri1, String queryString1, String url2, String queryString2) {
        String key = Comp1AppUtils.getComp1ApiRedisKey(uri1, queryString1);

        SearchResponse response = (SearchResponse)searchApiResultCacheService.getApiResultCache(key);
        if(response==null){
            //api 호출
            log.info("@@@@@@@ api call directly key={}", key);
            response = apiService.getRetry(Comp1AppUtils.host, Comp1AppUtils.getUrlTemplate(uri1, queryString1),
                    Comp2AppUtils.host, Comp2AppUtils.getUrlTemplate(url2, queryString2));
            //레디스에 api 검색결과 저장
            searchApiResultCacheService.saveApiResultCache(key, response);
        }
        return response;
    }
}
