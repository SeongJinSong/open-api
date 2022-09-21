package com.assignment.openapi.web.searchcomp2.service;

import com.assignment.openapi.web.searchcomp2.api.Comp2ApiService;
import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Response;
import com.assignment.openapi.web.searchcomp1.service.SearchApiResultCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchComp2Service {
    private final SearchApiResultCacheService searchApiResultCacheService;
    private final Comp2ApiService apiService;
    String host = "https://openapi.naver.com";

    public SearchComp2Response getContentsList(String uri, String queryString) {
        String key = getComp1ApiRedisKey(uri, queryString);
        /*
            TODO
             1. @Cacheable을 적용하여 분기 삭제
             2. API자체를 캐싱하는건 Client단에서 어울릴만한 일이다. reactQuery가 그런비슷한 역할을 하기도한다.
             서버단에서는 뭔가 서버단에서 어울릴만한 캐싱 알고리즘을 찾아보아야 할 것 같다.
         */
        SearchComp2Response response = (SearchComp2Response)searchApiResultCacheService.getApiResultCache(key);
        if(response==null){
            //api 호출
            log.info("@@@@@@@ api 직접 호출 key={}", key);
            response = apiService.get(host, getUrlTemplate(uri, queryString));
            //레디스에 api 검색결과 저장
            searchApiResultCacheService.saveApiResultCache(key, response);
        }


        return response;
    }
    private String getComp1ApiRedisKey(String uri, String queryString) {
        return host+"/"+ uri +"?" + queryString;
    }
    private String getUrlTemplate(String uri, String queryString){
        return "/"+uri+"?"+queryString;
    }
}