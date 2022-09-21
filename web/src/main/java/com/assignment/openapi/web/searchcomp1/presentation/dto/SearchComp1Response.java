package com.assignment.openapi.web.searchcomp1.presentation.dto;

import com.assignment.openapi.web.apiutil.SearchResponse;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//TODO 각각의 검색 결과를 캐싱할 필요가 있을까? 다른 알고리즘 찾아보자
public class SearchComp1Response<T> implements Serializable {
    private Meta meta;
    private List<T> documents;
}
