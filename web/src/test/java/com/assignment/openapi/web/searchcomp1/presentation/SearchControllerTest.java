package com.assignment.openapi.web.searchcomp1.presentation;

import com.assignment.openapi.core.error.ErrorCode;
import com.assignment.openapi.core.error.exception.NetworkException;
import com.assignment.openapi.web.apiutil.SearchResponse;
import com.assignment.openapi.web.searchcomp1.api.Comp1WebClientRequester;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTest {
    @Autowired
    //IntelliJ 버전 이슈로 IDE 버그 있음(https://stackoverflow.com/questions/73085049/cant-autowired-mockmvc-using-webmvctest)
    private MockMvc mockMvc;

//    @MockBean
//    Comp1WebClientRequester comp1WebClientRequester;

    @Order(1)
    @DisplayName("1 API 조회 IntegrationTest")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @Nested
    class ApiTest1{
        @DisplayName("1-1. 블로그 리스트 조회 V1")
        @Test
        void getBlogListV1() throws Exception {
            mockMvc.perform(get("/api/v1/search/blog?query=https://brunch.co.kr/@tourism 여행&page=1&size=10&sort=accuracy")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @DisplayName("2-1. 블로그 리스트 조회 V2")
        @Test
        void getBlogListV2() throws Exception {
            mockMvc.perform(get("/api/v2/search/blog?query=https://brunch.co.kr/@tourism 여행&page=1&size=10&sort=recency")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }
    }

    @Order(2)
    @DisplayName("2 API 랭킹 IntegrationTest")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @Nested
    class ApiTest2{
        @DisplayName("2-1. 랭킹조회 V1")
        @Test
        void getRankV1() throws Exception {
            mockMvc.perform(get("/api/v1/search/rank?page=0&size=10")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }
        @DisplayName("2-2. 랭킹 조회 V2")
        @Test
        void getRankV2() throws Exception {
            mockMvc.perform(get("/api/v2/search/rank")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }
    }
    @Order(3)
    @DisplayName("3. API 재처리 IntegrationTest")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @Nested
    class V3ApiTest{
        @DisplayName("1. 블로그 리스트 조회 첫번째 정상")
        @Test
        void getRetry1() throws Exception {
            mockMvc.perform(get("/api/v3/search/blog?query=여행&page=1&size=10&sort=accuracy")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }
        @DisplayName("2. 블로그 리스트 조회 첫번째 에러로 두번째로 재시도 테스트 코드 작성중 재출")
        @Test
        void getRetry2() throws Exception {
            WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
//            doThrow(RuntimeException.class).when(comp1WebClientRequester).getWebClient(any());
//            when(comp1WebClientRequester.getWebClient(any())).thenReturn(responseSpec);
            MvcResult result = mockMvc.perform(get("/api/v3/search/blog?query=여행&page=1&size=10&sort=recency")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            log.info(result.getResponse().getContentAsString());
        }
    }
}