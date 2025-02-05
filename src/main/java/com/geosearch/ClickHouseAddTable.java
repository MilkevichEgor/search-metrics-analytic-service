package com.geosearch;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ClickHouseAddTable {

  @Autowired
  @Qualifier("clickhouseJdbcTemplate")
  private final JdbcTemplate clickhouseJdbcTemplate;

  public ClickHouseAddTable(JdbcTemplate clickhouseJdbcTemplate) {
	this.clickhouseJdbcTemplate = clickhouseJdbcTemplate;
  }

  @PostConstruct
  public void createTableIfNotExists() {
	String createTableSql = """
		    CREATE TABLE IF NOT EXISTS search_analytic (
		        id UUID,
		        search_type String,
		        created_at DateTime,
		        updated_at DateTime
		    )
		    ENGINE = MergeTree()
		    PARTITION BY toYYYYMM(created_at)
		    ORDER BY (id, create_at);
		""";
	clickhouseJdbcTemplate.execute(createTableSql);
  }
}
