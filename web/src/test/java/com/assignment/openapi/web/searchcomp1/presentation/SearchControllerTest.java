package com.assignment.openapi.web.searchcomp1.presentation;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTest {
    @Autowired
    //IntelliJ 버전 이슈로 IDE 버그 있음(https://stackoverflow.com/questions/73085049/cant-autowired-mockmvc-using-webmvctest)
    private MockMvc mockMvc;

    @Order(1)
    @DisplayName("V1 API IntegrationTest")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @Nested
    class V1ApiTest{
        @DisplayName("1. 블로그 리스트 조회")
        @Test
        void getBlogListV1() throws Exception {
            mockMvc.perform(get("/api/v1/search/blog?query=https://brunch.co.kr/@tourism 여행&page=1&size=10&sort=1")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @DisplayName("2. 랭킹 조회")
        @Test
        void getBlogListV2() throws Exception {
            mockMvc.perform(get("/api/v2/search/blog?query=https://brunch.co.kr/@tourism 여행&page=1&size=10&sort=1")
                            .header("host", "localhost:8080")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }
    }

    @Order(2)
    @DisplayName("V2 API IntegrationTest")
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @Nested
    class V2ApiTest{
        @DisplayName("1. 블로그 리스트 조회")
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
        @DisplayName("2. 랭킹 조회")
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
}