package com.geosearch.consumer;

import com.geosearch.constant.KafkaTopic;
import com.geosearch.dto.request.AnalyticRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticConsumerService {

  @KafkaListener(topics = KafkaTopic.SEND_ANALYTIC_TOPIC,
	  containerFactory = "analyticRequestListenerFactory",
	  groupId = "send-analytic-group-id")
  public void consumeAnalyticRequest(AnalyticRequest analyticRequest) {
	log.info("Сообщение получено: {} ", analyticRequest.type());
  }
}
