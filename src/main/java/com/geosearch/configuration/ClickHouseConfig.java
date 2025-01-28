package com.geosearch.configuration;

import com.geosearch.entity.clickhouse.SearchAnalyticClickhouse;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@EnableJpaRepositories(
	entityManagerFactoryRef = "clickhouseEM",
	basePackageClasses = SearchAnalyticClickhouse.class)
@Configuration(proxyBeanMethods = false)
public class ClickHouseConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.clickhouse")
  public DataSourceProperties clickhouseDbProperties() {
	return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.clickhouse.hikari")
  public HikariDataSource clickhouseDataSource(
	  DataSourceProperties clickhouseDbProperties) {
	return clickhouseDbProperties
		.initializeDataSourceBuilder()
		.type(HikariDataSource.class)
		.build();
  }

  @Bean(name = "clickhouseEM")
  public LocalContainerEntityManagerFactoryBean clickhouseEntityManagerFactory(
	  EntityManagerFactoryBuilder builder, DataSource clickhouseDataSource) {
	HashMap<String, Object> properties = new HashMap<>();
	return builder
		.dataSource(clickhouseDataSource)
		.properties(properties)
		.packages(SearchAnalyticClickhouse.class)
		.build();
  }

  @Bean(name = "clickhouseJdbcTemplate")
  public JdbcTemplate clickhouseJdbcTemplate(@Qualifier("clickhouseDataSource") DataSource dataSource) {
	return new JdbcTemplate(dataSource);
  }
}
