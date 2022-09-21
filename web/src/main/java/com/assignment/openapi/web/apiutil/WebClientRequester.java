package com.assignment.openapi.web.apiutil;

import org.springframework.web.reactive.function.client.WebClient;


public interface WebClientRequester {
    WebClient.ResponseSpec getWebClient(String uri);
}
