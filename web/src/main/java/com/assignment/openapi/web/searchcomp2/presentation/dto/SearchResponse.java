package com.assignment.openapi.web.searchcomp2.presentation.dto;

import java.util.List;

public class SearchResponse<T> {
    String lastBuildDate;
    long total;
    int start;
    int display;
    List<T> items;
}
