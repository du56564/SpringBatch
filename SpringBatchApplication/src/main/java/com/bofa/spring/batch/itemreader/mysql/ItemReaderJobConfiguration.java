package com.bofa.spring.batch.itemreader.mysql;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
	public Step customItemReaderDBStep() {
		return stepBuilderFactory.get("customItemReaderDBStep")
				.<Customer, Customer>chunk(10)
				.reader(new CustomItemReader(dataSource).cursorItemReader())
				//.reader(new CustomPagingItemReader(dataSource).pagingItemReader())
				.writer(new CustomItemWriter().cutomItemWriter())
				.build();
	}
	@Bean
	public Job jobItemDBReader10() {
		return jobBuilderFactory.get("jobItemDBReader10")
				.start(customItemReaderDBStep())
				.build();
	}

}
