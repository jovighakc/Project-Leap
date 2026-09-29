package com.Scholarship.Tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner fixDatabaseSchema(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				String query = "SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE " +
						"FROM INFORMATION_SCHEMA.COLUMNS " +
						"WHERE TABLE_SCHEMA = DATABASE() " +
						"AND IS_NULLABLE = 'NO' " +
						"AND COLUMN_KEY != 'PRI' " +
						"AND EXTRA NOT LIKE '%auto_increment%'";

				List<Map<String, Object>> columns = jdbcTemplate.queryForList(query);
				for (Map<String, Object> col : columns) {
					String tableName = (String) col.get("TABLE_NAME");
					String columnName = (String) col.get("COLUMN_NAME");
					String columnType = (String) col.get("COLUMN_TYPE");

					try {
						String alterSql = String.format("ALTER TABLE `%s` MODIFY COLUMN `%s` %s NULL DEFAULT NULL",
								tableName, columnName, columnType);
						jdbcTemplate.execute(alterSql);
					} catch (Exception ignored) {
					}
				}
			} catch (Exception ignored) {
			}
		};
	}

}


