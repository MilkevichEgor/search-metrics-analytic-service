package com.geosearch.dto;

import com.geosearch.constant.SearchType;
import com.geosearch.entity.SearchAnalytic;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link SearchAnalytic}
 */
public record SearchAnalyticDto(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, SearchType searchType) {
}