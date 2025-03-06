package com.milkevich.configuration;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "com.milkevich.repository",
	entityManagerFactoryRef = "postgresEntityManagerFactory",
	transactionManagerRef = "postgresTransactionManager"
)
public class PostgresConfig {

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource.postgres")
  public DataSourceProperties postgresDataSourceProperties() {
	return new DataSourceProperties();
  }

  @Bean(name = "postgresDataSource")
  @Primary
  public DataSource postgresDataSource() {
	return postgresDataSourceProperties()
		.initializeDataSourceBuilder()
		.build();
  }

  @Bean(name = "postgresEntityManagerFactory")
  @Primary
  public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
	  @Qualifier("postgresDataSource") DataSource dataSource,
	  EntityManagerFactoryBuilder builder) {
	return builder
		.dataSource(dataSource)
		.packages("com.milkevich.entity")
		.persistenceUnit("postgres")
		.build();
  }

  @Bean(name = "postgresTransactionManager")
  @Primary
  public PlatformTransactionManager postgresTransactionManager(
	  @Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
	return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
  }

  @Primary
  @Bean(name = "postgresJdbcTemplate")
  public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
	return new JdbcTemplate(dataSource);
  }

}
