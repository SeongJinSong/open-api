package com.assignment.openapi.web.searchcomp1.repository;

import com.assignment.openapi.core.search.comp1.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>, SearchHistoryRepositoryCustom {
}
