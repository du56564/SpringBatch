package com.bofa.spring.batch.validator.flatfileswriter;

import java.io.File;
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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;




@Configuration
public class WriterJobConfiguration {

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
		
		System.err.println("Reading....."+reader.getPageSize());
		return reader;	
	}
	
	@Bean
	public FlatFileItemWriter<Customer> flatFileItemWriterFromDB() throws Exception{
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
		writer.setLineAggregator(new PassThroughLineAggregator<>());
		String customAggregatorPath = File.createTempFile("CustomerOutput", ".out").getAbsolutePath();
		System.out.println("OUTPUT PATH : "+customAggregatorPath);
		writer.setResource(new FileSystemResource(customAggregatorPath));
		writer.afterPropertiesSet();	
		return writer;
	}	
	
	@Bean
	public ValidatingItemProcessor<Customer> itemProcessor(){
		return new ValidatingItemProcessor<>(new CustomValidator());
	}
	
	@Bean
	public Step customValidateItemWriterFromDBStep() throws Exception{
		return stepBuilderFactory.get("customValidateItemWriterFromDBStep")
				.<Customer,Customer>chunk(10)
				.reader(pagingItemReader())
				.processor(itemProcessor())
				.writer(flatFileItemWriterFromDB())
				.build();				
	}
	
	@Bean
	public Job jobItemValidateProcessorFromDB1() throws Exception{
		return jobBuilderFactory.get("jobItemValidateProcessorFromDB1")
				.start(customValidateItemWriterFromDBStep())
				.build();
	}

	
	
}
