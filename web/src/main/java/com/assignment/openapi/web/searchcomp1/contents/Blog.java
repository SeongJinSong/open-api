package com.assignment.openapi.web.searchcomp1.contents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Blog {
    //블로그 글 제목
    private String title;
    //블로그 글 요약
    private String contents;
    //블로그 글 URL
    private String url;
    //블로그의 이름
    @JsonProperty("blogname")
    private String blogName;
    //검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
    private String thumbnail;
    //블로그 글 작성시간, ISO 8601
    //[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    @JsonProperty("datetime")
    private LocalDateTime dateTime;
}
