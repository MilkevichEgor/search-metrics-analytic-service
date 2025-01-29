package com.geosearch.consumer;

import com.geosearch.constant.KafkaTopic;
import com.geosearch.dto.request.AnalyticRequest;
import com.geosearch.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyticConsumerService {

  private final AnalyticService analyticService;

  @KafkaListener(topics = KafkaTopic.SEND_ANALYTIC_TOPIC,
	  containerFactory = "analyticRequestListenerFactory",
	  groupId = "send-analytic-group-id")
  public void consumeAnalyticRequest(AnalyticRequest analyticRequest) {
	log.info("Сообщение получено: {} ", analyticRequest.type());
	analyticService.save(analyticRequest);
	log.info("Данные сохранены");
  }
}
