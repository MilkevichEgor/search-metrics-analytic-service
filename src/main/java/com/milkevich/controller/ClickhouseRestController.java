package com.milkevich.controller;

import com.milkevich.clickhouse.SearchAnalyticClickhouse;
import com.milkevich.constant.SearchType;
import com.milkevich.dto.SearchAnalyticDto;
import com.milkevich.dto.request.AnalyticCreateRequest;
import com.milkevich.service.ClickHouseService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/clickhouse")
public class ClickhouseRestController {

  private final JdbcTemplate jdbcTemplate;
  private final ClickHouseService clickHouseService;

  public ClickhouseRestController(@Qualifier("clickhouseJdbcTemplate") JdbcTemplate jdbcTemplate,
								  ClickHouseService clickHouseService) {
	this.jdbcTemplate = jdbcTemplate;
	this.clickHouseService = clickHouseService;
  }

  @PostMapping
  public void createAnalyticData(@RequestBody AnalyticCreateRequest request) {

	SearchAnalyticClickhouse analyticClickhouse = SearchAnalyticClickhouse.create(request.type());
	jdbcTemplate.update(
		"""
			INSERT INTO search_analytic (id, search_type, created_at, updated_at)
			        VALUES (?, ?, ?, ?);
			""",
		analyticClickhouse.id().toString(),
		analyticClickhouse.searchType(),
		analyticClickhouse.createdAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
		analyticClickhouse.updatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	);
  }

  @GetMapping
  public List<Map<String, Object>> getAllSearchAnalyticFromClickhouse(@RequestParam(defaultValue = "100") int page,
																	  @RequestParam(defaultValue = "0") int size) {
	return clickHouseService.getAllSearchAnalyticFromClickhouse(page, size);
  }

  @GetMapping(path = {"/type"})
  public List<SearchAnalyticDto> getAnalyticDataBySearchType(@RequestParam SearchType type,
															 @RequestParam(defaultValue = "100") int page,
															 @RequestParam(defaultValue = "0") int size) {
	return clickHouseService.getAnalyticDataBySearchType(type, page, size);
  }

  @GetMapping(path = {"/date"})
  public List<SearchAnalyticDto> getAnalyticDataByCreatedDate(@RequestParam String startDate,
															  @RequestParam String endDate,
															  @RequestParam(defaultValue = "100") int page,
															  @RequestParam(defaultValue = "0") int size) {
	return clickHouseService.getAnalyticDataByCreatedDate(startDate, endDate, page, size);
  }

  @GetMapping(path = {"/last-hours"})
  public Integer getTotalRequestForLastHours(@RequestParam int hours) {
	return clickHouseService.getTotalRequestForLastHours(hours);
  }
}
