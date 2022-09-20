package com.assignment.openapi.web.searchcomp1.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SearchControllerTest {
    @Autowired
    //IntelliJ 버전 이슈로 IDE 버그 있음(https://stackoverflow.com/questions/73085049/cant-autowired-mockmvc-using-webmvctest)
    private MockMvc mockMvc;
    @Test
    void getBlogList() throws Exception {
        mockMvc.perform(get("/v2/search/blog?query=https://brunch.co.kr/@tourism 여행&page=1&size=10&sort=1")
                        .header("host", "localhost:8080")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}