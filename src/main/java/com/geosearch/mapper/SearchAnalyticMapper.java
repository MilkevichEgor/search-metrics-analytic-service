package com.geosearch.mapper;

import com.geosearch.dto.SearchAnalyticDto;
import com.geosearch.entity.SearchAnalytic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SearchAnalyticMapper {
  SearchAnalytic toEntity(SearchAnalyticDto searchAnalyticDto);

  SearchAnalyticDto toSearchAnalyticDto(SearchAnalytic searchAnalytic);
}