package com.milkevich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootApplication
class TestApplication {

  public static void main(String[] args) {
	SpringApplication.from(AnalyticsServiceApplication::main)
		.with(TestcontainersConfiguration.class)
		.run(args);
  }
}
