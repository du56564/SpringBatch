package com.bofa.spring.batch.itemwriter.flatfiles;

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
public class FlatFileItemWriterDBJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader(){
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new CustomRowMapper());
		
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		
		Map<String, Order> sortKey = new HashMap<>(1);
		sortKey.put("id",Order.ASCENDING );
		queryProvider.setSortKeys(sortKey);
		reader.setQueryProvider(queryProvider);
		
		System.out.println("Reading....."+reader.getPageSize());
		return reader;	
	}
	
	@Bean
	public Step customFlatFileItemWriterStep() throws Exception {
		return stepBuilderFactory.get("customFlatFileItemWriterStep")
				.<Customer,Customer>chunk(10)
				.reader(pagingItemReader())
				//.writer(new CustomFileFlatItemWriter().flatFileItemWriterFromDB())
				.writer(new CustomFileFlatItemWriter().flatFileItemWriterFromDBCustomAggregator())
				.build();
	}
	@Bean
	public Job jobFlatFileItemWriterFromj() throws Exception {
		return jobBuilderFactory.get("jobFlatFileItemWriterFromDB9")
				.start(customFlatFileItemWriterStep())
				.build();
	}

	
	
}
