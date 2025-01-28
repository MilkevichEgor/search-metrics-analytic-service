package com.geosearch.dto;

import com.geosearch.constant.SearchType;
import com.geosearch.entity.SearchAnalytic;
import java.time.LocalDateTime;

/**
 * DTO for {@link SearchAnalytic}
 */
public record SearchAnalyticDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, SearchType searchType) {
}