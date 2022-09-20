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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@RestController
public class SearchController<T> {
    private final SearchFacade searchFacade;
    @GetMapping("v1/search/{contentsType}")
    public ResponseEntity<ResponseWrapper<SearchResponse<T>>> getContentsListV1(
              @PathVariable String contentsType
             ,@Valid SearchRequest request){
        return ResponseWrapper.ok(searchFacade.getContentsListV1(contentsType, request), "success");
    }
    @GetMapping("v2/search/{contentsType}")
    public ResponseEntity<ResponseWrapper<SearchResponse<T>>> getContentsListV2(
            @PathVariable String contentsType
            ,@Valid SearchRequest request){
        return ResponseWrapper.ok(searchFacade.getContentsListV2(contentsType, request), "success");
    }
    /**
     * DB에 저장된 History를 검색어 기반으로 그루핑해 오더링한 결과를 리턴한다.
     *
     * @param pageable default limit = 10
     * @return responseWrapper
     */
    @GetMapping("v1/search/rank")
    public ResponseEntity<ResponseWrapper<Page<SearchRank>>> getPopularSearchWord(Pageable pageable){
        return ResponseWrapper.ok(searchFacade.getPopularSearchWord(pageable), "success");
    }

    /**
     * Redis ZSET 을 활용하여 오더링한 결과를 리턴한다.
     * @return
     */
    @GetMapping("v2/search/rank")
    public ResponseEntity<ResponseWrapper<List<SearchRank>>> searchRankList(){
        return ResponseWrapper.ok(searchFacade.searchRankList(), "success");
    }
}
