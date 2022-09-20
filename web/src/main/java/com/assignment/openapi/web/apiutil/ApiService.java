package com.assignment.openapi.web.apiutil;

import com.assignment.openapi.core.error.ErrorCode;
import com.assignment.openapi.core.exception.NetworkException;
import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {
    private final WebClientRequester webClientRequester;
    //TODO searchResponse에 의존하는것 수정 필요
    public SearchResponse get(String host, String urlTemplate) {
        return Optional.ofNullable(webClientRequester.getWebClient(host+urlTemplate)
                        .bodyToMono(SearchResponse.class).block())
                .orElseThrow(()->{throw new NetworkException(ErrorCode.REQUEST_TIMEOUT_EXCEPTION);});
    }
}
