package com.geosearch.controller;

import com.geosearch.dto.request.AnalyticCreateRequest;
import com.geosearch.entity.clickhouse.SearchAnalyticClickhouse;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/clickhouse")
//@RequiredArgsConstructor
public class ClickhouseRestController {

  @Autowired
  @Qualifier("clickhouseJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @PostMapping
  public void createAnalyticData(@RequestBody AnalyticCreateRequest request) {

	SearchAnalyticClickhouse analyticClickhouse = SearchAnalyticClickhouse.create(request.type());
	jdbcTemplate.update(
		"""
			INSERT INTO search_analytic (id, search_type, created_at, updated_at)
			        VALUES (?, ?, ?, ?);
			""",
		analyticClickhouse.id().toString(), // Преобразование UUID в строку
		analyticClickhouse.searchType(),
		analyticClickhouse.createdAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), // Форматирование даты
		analyticClickhouse.updatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	);
  }
}
