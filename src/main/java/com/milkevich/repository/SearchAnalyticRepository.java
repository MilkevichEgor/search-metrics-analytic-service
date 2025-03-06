package com.milkevich.repository;

import com.milkevich.entity.SearchAnalytic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchAnalyticRepository extends JpaRepository<SearchAnalytic, Long> {
}