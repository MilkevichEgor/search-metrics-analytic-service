package com.geosearch.repository;

import com.geosearch.entity.SearchAnalytic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchAnalyticRepository extends JpaRepository<SearchAnalytic, Long> {
}