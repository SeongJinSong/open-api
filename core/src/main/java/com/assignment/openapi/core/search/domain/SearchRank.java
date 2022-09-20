package com.assignment.openapi.core.search.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//TODO redis write-behind 동작을 확용하여 redis에서 DB로 동기화예정
public class SearchRank {
    String query;
    long count;
}
