package com.assignment.openapi.web.apiutil;

import com.assignment.openapi.web.searchcomp1.presentation.dto.Meta;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
//TODO 필드가 없는 SearchResponse에 api 결과를 리턴 받게하면 redis 매핑이 안되어서 임시로 모든필드 넣어놂음
// 이런방법말고 깔끔하게 처리가능한 방법찾기
public class SearchResponse<T> implements Serializable {
    String lastBuildDate;
    long total;
    int start;
    int display;
    List<T> items;
    private Meta meta;
    private List<T> documents;
}
