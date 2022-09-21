package com.assignment.openapi.web.searchcomp1.presentation;

import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Request;
import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Response;
import com.assignment.openapi.web.searchcomp2.application.SearchFacade2;
import com.assignment.openapi.web.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping(path = "/api2")
@RequiredArgsConstructor
@RestController
public class SearchComp2TestController<T> {
    private final SearchFacade2 searchFacade;
    @GetMapping("v2/search/{contentsType}")
    public ResponseEntity<ResponseWrapper<SearchComp2Response<T>>> getContentsListV1(
            @PathVariable String contentsType
            ,@Valid SearchComp2Request request){
        return ResponseWrapper.ok(searchFacade.getContentsListV2(contentsType, request), "success");
    }
}
