package com.assignment.openapi.web.searchcomp2.api;

import com.assignment.openapi.web.apiutil.WebClientRequester;
import com.assignment.openapi.web.searchcomp2.AppInfo;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientRequest;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class Comp2WebClientRequester implements WebClientRequester {
    @Autowired
    HttpServletRequest request;
    private final HttpClient httpClient = HttpClient
            .create()
            .wiretap(HttpClient.class.getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.SIMPLE, StandardCharsets.UTF_8);
    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    public WebClient.ResponseSpec getWebClient(String uri){
        return webClient.get()
                .uri(uri)
                .httpRequest(clientHttpRequest -> {
                    HttpClientRequest httpRequest = clientHttpRequest.getNativeRequest();
                    httpRequest.responseTimeout(Duration.ofSeconds(5));
                })
                .header("X-Naver-Client-Id", AppInfo.clientId)
                .header( "X-Naver-Client-Secret", AppInfo.clientSecret)
                .retrieve();
    }
}
