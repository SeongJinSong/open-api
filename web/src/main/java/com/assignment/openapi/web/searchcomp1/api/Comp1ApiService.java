package com.assignment.openapi.web.searchcomp1.api;

import com.assignment.openapi.core.error.ErrorCode;
import com.assignment.openapi.core.error.exception.NetworkException;
import com.assignment.openapi.web.apiutil.ApiService;
import com.assignment.openapi.web.apiutil.SearchResponse;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Response;
import com.assignment.openapi.web.searchcomp2.api.Comp2WebClientRequester;
import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Comp1ApiService implements ApiService {
    private final Comp1WebClientRequester comp1WebClientRequester;
    private final Comp2WebClientRequester comp2WebClientRequester;
    public SearchComp1Response get(String host, String urlTemplate) {
        return Optional.ofNullable(comp1WebClientRequester.getWebClient(host+urlTemplate)
                        .bodyToMono(SearchComp1Response.class).block())
                .orElseThrow(()->{throw new NetworkException(ErrorCode.REQUEST_TIMEOUT_EXCEPTION);});
    }
    public SearchResponse getRetry(String host, String urlTemplate, String host2, String urlTemplate2) {
        return Optional.ofNullable(comp1WebClientRequester.getWebClient(host+urlTemplate)
                        .bodyToMono(SearchResponse.class)
                        .onErrorResume(error -> {
                            log.info("comp1ApiCall Error = {}", error);
                            return comp2WebClientRequester.getWebClient(host+urlTemplate2).bodyToMono(SearchComp2Response.class);
                        }).block()
                )
                .orElseThrow(()->{throw new NetworkException(ErrorCode.REQUEST_TIMEOUT_EXCEPTION);});
    }
}
