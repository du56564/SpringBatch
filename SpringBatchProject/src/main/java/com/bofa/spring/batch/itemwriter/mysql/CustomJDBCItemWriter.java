package com.bofa.spring.batch.itemwriter.mysql;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;

public class CustomJDBCItemWriter {
	
	private DataSource dataSource;
	
	public CustomJDBCItemWriter(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	@Bean
	public JdbcBatchItemWriter<Customer> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
		try {
			
		writer.setDataSource(dataSource);
		//writer.setSql("INSERT INTO CUSTOMER VALUES (:id, :firstName, :lastName, :birthdate)");
		writer.setSql("INSERT INTO CUSTOMER(firstName, lastName, birthdate) VALUES (:firstName, :lastName, :birthdate)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.afterPropertiesSet();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return writer;
	}
	
}

