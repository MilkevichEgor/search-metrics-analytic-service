package com.geosearch.service;

import com.geosearch.dto.SearchAnalyticDto;
import com.geosearch.dto.request.AnalyticRequest;
import com.geosearch.entity.SearchAnalytic;
import com.geosearch.mapper.SearchAnalyticMapper;
import com.geosearch.repository.SearchAnalyticRepository;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticService {

  private final SearchAnalyticRepository searchAnalyticRepository;
  private final SearchAnalyticMapper searchAnalyticMapper;
  @Autowired
  @Qualifier("clickhouseJdbcTemplate")
  private final JdbcTemplate clickhouseJdbcTemplate;

  public List<SearchAnalyticDto> getAll() {
	return searchAnalyticRepository.findAll()
		.stream()
		.map(searchAnalyticMapper::toSearchAnalyticDto)
		.toList();
  }

  public List<SearchAnalytic> getAllForInsertClickhouse() {
	return searchAnalyticRepository.findAll();
  }

  @Transactional
  public SearchAnalyticDto save(AnalyticRequest analyticRequest) {

	SearchAnalytic searchAnalytic = new SearchAnalytic(UUID.randomUUID(),
		analyticRequest.createdBy(),
		analyticRequest.updatedBy(),
		analyticRequest.type());

	return searchAnalyticMapper.toSearchAnalyticDto(searchAnalyticRepository.save(searchAnalytic));
  }

  @Transactional
  public void batchInsertSearchAnalytic(List<SearchAnalytic> analyticList) {
	String sql = """
		INSERT INTO search_analytic (id, search_type, created_at, updated_at)
		        VALUES (?, ?, ?, ?);
		""";
	clickhouseJdbcTemplate.batchUpdate(sql, analyticList, 100, (PreparedStatement ps, SearchAnalytic analytic) -> {
	  ps.setString(1, String.valueOf(analytic.getId()));
	  ps.setString(2, String.valueOf(analytic.getSearchType()));
	  ps.setTimestamp(3, Timestamp.valueOf(analytic.getCreatedAt()));
	  ps.setTimestamp(4, Timestamp.valueOf(analytic.getUpdatedAt()));
	});
	log.info("Данные успешно выгружены в Clickhouse");
  }
}
