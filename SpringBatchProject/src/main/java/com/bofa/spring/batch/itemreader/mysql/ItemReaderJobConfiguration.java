package com.bofa.spring.batch.itemreader.mysql;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;
	
/*	@Bean
	public JdbcCursorItemReader<Customer> cursorItemReader(){
		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
		reader.setSql("Select id, fistName, lastName, birthDate from customer order by lastname, firstName");
		reader.setDataSource(dataSource);
		reader.setRowMapper(new CustomRowMapper());
		return reader;
	}
	
	@Bean
	public ItemWriter<Customer> customItemWriter(){
		return items->{
			for (Customer customer : items) {
				System.out.println(customer.toString());
			}
		};
	}
*/
	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader(){
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new CustomRowMapper());
		
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		
		Map<String, Order> sortKey = new HashMap<>(2);
		sortKey.put("id",Order.ASCENDING );
		queryProvider.setSortKeys(sortKey);
		reader.setQueryProvider(queryProvider);
		
		return reader;	
	}

	@Bean
	public Step customItemReaderDBStep() {
		return stepBuilderFactory.get("customItemReaderDBStep")
				.<Customer, Customer>chunk(10)
				//.reader(new CustomItemReader(dataSource).cursorItemReader())
				.reader(pagingItemReader())
				.writer(new CustomItemWriter().cutomItemWriter())
				.build();
	}
	@Bean
	public Job jobItemDBReader13() {
		return jobBuilderFactory.get("jobItemDBReader13")
				.start(customItemReaderDBStep())
				.build();
	}

}
