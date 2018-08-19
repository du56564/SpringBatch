package com.bofa.spring.batch.itemreader.mysql;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;

public class CustomItemReader {
	
	private DataSource dataSource;
	
	public CustomItemReader(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	@Bean
	public JdbcCursorItemReader<Customer> cursorItemReader(){
		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
		reader.setSql("Select id, firstName, lastName, birthdate from customer order by lastname, firstName");
		reader.setDataSource(dataSource);
		reader.setRowMapper(new CustomRowMapper());
		return reader;
	}
	
}

