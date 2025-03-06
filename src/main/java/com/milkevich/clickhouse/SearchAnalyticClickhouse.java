package com.milkevich.clickhouse;

import java.time.LocalDateTime;
import java.util.UUID;

public record SearchAnalyticClickhouse(UUID id, String searchType, LocalDateTime createdAt, LocalDateTime updatedAt) {

  public static SearchAnalyticClickhouse create(String searchType) {
	return new SearchAnalyticClickhouse(
		UUID.randomUUID(),
		searchType,
		LocalDateTime.now(),
		LocalDateTime.now()
	);
  }
}
