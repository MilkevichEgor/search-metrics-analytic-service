package com.geosearch.controller;

import com.geosearch.dto.SearchAnalyticDto;
import com.geosearch.service.AnalyticService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/analytic")
public class AnalyticRestController {

  private final AnalyticService analyticService;
  @Autowired
  @Qualifier("postgresJdbcTemplate")
  private final JdbcTemplate postgresJdbcTemplate;

  @GetMapping
  public List<SearchAnalyticDto> getAll() {
    return analyticService.getAll();
  }

//  @GetMapping
//  public List<SearchAnalyticDto> getAll2() {
//    return postgresJdbcTemplate.query("""
//        SELECT * FROM search-analytic
//        """);
//  }
}
