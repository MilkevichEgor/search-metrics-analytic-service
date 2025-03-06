package com.milkevich.service;

import com.milkevich.entity.SearchAnalytic;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledService {

  private final ClickHouseService clickHouseService;
  private final AnalyticService analyticService;

  @Scheduled(fixedRate = 50000)
  public void clickHouseInsert() {
	List<SearchAnalytic> analyticList = analyticService.getAllForInsertClickhouse();
	log.info("Данные выгружены из Базы Данных");
	clickHouseService.batchInsertSearchAnalytic(analyticList);
  }
}
