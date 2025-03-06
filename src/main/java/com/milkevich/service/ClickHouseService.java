package com.milkevich.service;

import com.milkevich.constant.SearchType;
import com.milkevich.dto.SearchAnalyticDto;
import com.milkevich.entity.SearchAnalytic;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ClickHouseService {

  private final JdbcTemplate clickhouseJdbcTemplate;

  public ClickHouseService(@Qualifier("clickhouseJdbcTemplate") JdbcTemplate clickhouseJdbcTemplate) {
	this.clickhouseJdbcTemplate = clickhouseJdbcTemplate;
  }

  @Transactional
  public void batchInsertSearchAnalytic(List<SearchAnalytic> analyticList) {
	String sql = """
		INSERT INTO search_analytic (id, search_type, created_at, updated_at)
		        VALUES (?, ?, ?, ?);
		""";
	clickhouseJdbcTemplate.batchUpdate(sql, analyticList, 100,
		(PreparedStatement ps, SearchAnalytic analytic) -> {
		  ps.setString(1, String.valueOf(analytic.getId()));
		  ps.setString(2, String.valueOf(analytic.getSearchType()));
		  ps.setTimestamp(3, Timestamp.valueOf(analytic.getCreatedAt()));
		  ps.setTimestamp(4, Timestamp.valueOf(analytic.getUpdatedAt()));
		});
	log.info("Данные успешно выгружены в Clickhouse");
  }

  public List<Map<String, Object>> getAllSearchAnalyticFromClickhouse(int page, int size) {
	String sql = "SELECT * FROM search_analytic LIMIT ? OFFSET ?";
	return clickhouseJdbcTemplate.queryForList(sql, page, size);
  }

  public List<SearchAnalyticDto> getAnalyticDataBySearchType(SearchType searchType, int page, int size) {
	String sql = """
		SELECT id, search_type, created_at, updated_at FROM search_analytic
		        WHERE search_type = ?
		        LIMIT ? OFFSET ?
		""";
	return clickhouseJdbcTemplate.query(sql, (rs, rowNum) -> new SearchAnalyticDto(
		rs.getObject("id", UUID.class),
		SearchType.fromString(rs.getString("search_type")),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("updated_at").toLocalDateTime()
	), searchType.name(), page, size);
  }

  public List<SearchAnalyticDto> getAnalyticDataByCreatedDate(String startDate, String endDate, int page, int size) {
	String sql = """
		    SELECT id, search_type, created_at, updated_at FROM search_analytic
		    WHERE created_at BETWEEN ? AND ?
		    ORDER BY created_at DESC
		    LIMIT ? OFFSET ?
		""";
	return clickhouseJdbcTemplate.query(sql, (rs, rowNum) -> new SearchAnalyticDto(
		rs.getObject("id", UUID.class),
		SearchType.fromString(rs.getString("search_type")),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("updated_at").toLocalDateTime()
	), startDate, endDate, page, size);
  }

  public Integer getTotalRequestForLastHours(int hours) {
	String sql = """
		SELECT count(*)
		FROM search_analytic
		WHERE created_at > (now() - toIntervalHour(?))
		""";
	return clickhouseJdbcTemplate.queryForObject(sql, Integer.class, hours);
  }
}
