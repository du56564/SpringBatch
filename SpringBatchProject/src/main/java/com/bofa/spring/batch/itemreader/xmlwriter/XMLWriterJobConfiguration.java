package com.bofa.spring.batch.itemreader.xmlwriter;

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
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.bofa.spring.batch.itemwriter.flatfiles.CustomRowMapper;
import com.bofa.spring.batch.itemwriter.flatfiles.Customer;



@Configuration
public class XMLWriterJobConfiguration {

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
	public StaxEventItemWriter<Customer> customXMLItemWriter() throws Exception{
		XStreamMarshaller marshaller = new XStreamMarshaller();
		StaxEventItemWriter<Customer> writer = new StaxEventItemWriter<Customer>();
		Map<String,Class> aliases = new HashMap<>();
		aliases.put("customer", Customer.class);
		marshaller.setAliases(aliases);
		
		writer.setRootTagName("customers");
		writer.setMarshaller(marshaller);
		String customAggregatorPath = File.createTempFile("customXMLOutput", ".xml").getAbsolutePath();
		System.out.println("OUTPUT PATH : "+customAggregatorPath);
		writer.setResource(new FileSystemResource(customAggregatorPath));
		writer.afterPropertiesSet();	
		
		return writer;
	}
	
	
	@Bean
	public Step customXMLItemWriterFromDBStep() throws Exception{
		return stepBuilderFactory.get("customXMLItemWriterFromDBStep")
				.<Customer,Customer>chunk(10)
				.reader(pagingItemReader())
				.writer(customXMLItemWriter())
				.build();
	}
	@Bean
	public Job jobXMLItemWriterFromDB3() throws Exception{
		return jobBuilderFactory.get("jobXMLItemWriterFromDB3")
				.start(customXMLItemWriterFromDBStep())
				.build();
	}

	
	
}
