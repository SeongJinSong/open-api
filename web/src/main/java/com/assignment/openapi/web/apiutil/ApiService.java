package com.assignment.openapi.web.apiutil;

import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchResponse;

public interface ApiService {
    SearchResponse get(String host, String urlTemplate);
}
