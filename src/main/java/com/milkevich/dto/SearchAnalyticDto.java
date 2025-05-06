package com.milkevich.dto;

import com.milkevich.constant.SearchType;
import com.milkevich.entity.SearchAnalytic;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link SearchAnalytic}
 */
public record SearchAnalyticDto(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, SearchType searchType) {
}