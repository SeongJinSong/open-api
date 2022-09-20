package com.assignment.openapi.web.searchcomp1.repository;

import com.assignment.openapi.core.search.domain.SearchRank;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import static com.assignment.openapi.core.search.comp1.QSearchHistory.searchHistory;


@Slf4j
public class SearchHistoryRepositoryImpl implements SearchHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SearchHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<SearchRank> findTopSearchHistory(Pageable pageable) {
        JPAQuery<SearchRank> content = queryFactory.select(
                        Projections.fields(SearchRank.class,
                                searchHistory.query,
                                searchHistory.query.count().as("count"))
                )
                .from(searchHistory)
                .groupBy(searchHistory.query)
                .orderBy(searchHistory.query.count().desc());
        long totalCnt = content.fetch().size();
        long offset = pageable.getOffset();
        long limit = pageable.getPageSize()==0?10:pageable.getPageSize();
        return PageableExecutionUtils.getPage(content.offset(offset).limit(limit).fetch(), pageable, () -> totalCnt);
    }
}
