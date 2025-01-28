package com.geosearch.configuration;

import com.geosearch.clickhouse.SearchAnalyticClickhouse;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackageClasses = SearchAnalyticClickhouse.class,
	entityManagerFactoryRef = "clickhouseEntityManagerFactory",
	transactionManagerRef = "clickhouseTransactionManager"
)
public class ClickHouseConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.clickhouse")
  public DataSourceProperties clickhouseDataSourceProperties() {
	return new DataSourceProperties();
  }

  @Bean(name = "clickhouseDataSource")
  public DataSource clickhouseDataSource() {
	return clickhouseDataSourceProperties()
		.initializeDataSourceBuilder()
		.build();
  }

  @Bean(name = "clickhouseEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean clickhouseEntityManagerFactory(
	  @Qualifier("clickhouseDataSource") DataSource dataSource,
	  EntityManagerFactoryBuilder builder) {
	return builder
		.dataSource(dataSource)
		.packages("com.geosearch.clickhouse")
		.persistenceUnit("clickhouse")
		.build();
  }

  @Bean(name = "clickhouseJdbcTemplate")
  public JdbcTemplate postgresJdbcTemplate(@Qualifier("clickhouseDataSource") DataSource dataSource) {
	return new JdbcTemplate(dataSource);
  }

  @Bean(name = "clickhouseTransactionManager")
  public PlatformTransactionManager clickhouseTransactionManager(
	  @Qualifier("clickhouseEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
	return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
  }
}
