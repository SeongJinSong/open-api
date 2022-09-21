package com.assignment.openapi.web.searchcomp2.presentation.dto;

import com.assignment.openapi.web.apiutil.SearchResponse;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchComp2Response<T> extends SearchResponse {
    String lastBuildDate;
    long total;
    int start;
    int display;
    List<T> items;
}
