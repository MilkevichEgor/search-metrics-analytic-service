package com.milkevich.service;

import com.milkevich.dto.SearchAnalyticDto;
import com.milkevich.dto.request.AnalyticRequest;
import com.milkevich.entity.SearchAnalytic;
import com.milkevich.mapper.SearchAnalyticMapper;
import com.milkevich.repository.SearchAnalyticRepository;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AnalyticService {

  private final SearchAnalyticRepository searchAnalyticRepository;
  private final SearchAnalyticMapper searchAnalyticMapper;

  public AnalyticService(SearchAnalyticRepository searchAnalyticRepository,
						 SearchAnalyticMapper searchAnalyticMapper) {
	this.searchAnalyticRepository = searchAnalyticRepository;
	this.searchAnalyticMapper = searchAnalyticMapper;
  }

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
}
