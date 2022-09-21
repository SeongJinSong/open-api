package com.assignment.openapi.web.searchcomp2.presentation.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchResponse<T> implements Serializable {
    String lastBuildDate;
    long total;
    int start;
    int display;
    List<T> items;
}
