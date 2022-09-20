package com.assignment.openapi.web.searchcomp1.presentation;

import com.assignment.openapi.core.search.domain.SearchRank;
import com.assignment.openapi.web.searchcomp1.application.SearchFacade;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchRequest;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchResponse;
import com.assignment.openapi.web.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping(path = "/v2/search/blog")
@RequiredArgsConstructor
@RestController
public class SearchController<T> {
    private final SearchFacade searchFacade;
    @GetMapping
    public ResponseEntity<ResponseWrapper<SearchResponse<T>>> getContentsList(
            HttpServletRequest httpservletRequest, @Valid SearchRequest request){
        return ResponseWrapper.ok(searchFacade.getContentsList(httpservletRequest, request), "success");
    }
    /**
     * DB에 저장된 History를 검색어 기반으로 그루핑해 오더링한 결과를 리턴한다.
     *
     * @param pageable default limit = 10
     * @return responseWrapper
     */
    @GetMapping("/rank")
    public ResponseEntity<ResponseWrapper<Page<SearchRank>>> getPopularSearchWord(Pageable pageable){
        return ResponseWrapper.ok(searchFacade.getPopularSearchWord(pageable), "success");
    }

    /**
     * Redis ZSET 을 활용하여 오더링한 결과를 리턴한다.
     * @return
     */
    @GetMapping("/rank-new")
    public ResponseEntity<ResponseWrapper<List<SearchRank>>> searchRankList(){
        return ResponseWrapper.ok(searchFacade.searchRankList(), "success");
    }
}
