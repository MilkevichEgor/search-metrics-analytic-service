package com.geosearch.service;

import com.geosearch.dto.SearchAnalyticDto;
import com.geosearch.mapper.SearchAnalyticMapper;
import com.geosearch.repository.SearchAnalyticRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticService {

  private final SearchAnalyticRepository searchAnalyticRepository;
  private final SearchAnalyticMapper searchAnalyticMapper;

  public List<SearchAnalyticDto> getAll() {
	return searchAnalyticRepository.findAll()
		.stream()
		.map(searchAnalyticMapper::toSearchAnalyticDto)
		.toList();
  }
}
