package com.geosearch.configuration;

import com.geosearch.dto.request.AnalyticRequest;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
class KafkaConfiguration {

  @Bean
  public ConsumerFactory<String, AnalyticRequest> analyticRequestConsumerFactory(KafkaProperties kafkaProperties) {
	Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.geosearch.dto.request");
	return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public KafkaListenerContainerFactory<?> analyticRequestListenerFactory(ConsumerFactory<String, AnalyticRequest> analyticRequestConsumerFactory) {
	ConcurrentKafkaListenerContainerFactory<String, AnalyticRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(analyticRequestConsumerFactory);
	factory.setBatchListener(false);
	return factory;
  }
}