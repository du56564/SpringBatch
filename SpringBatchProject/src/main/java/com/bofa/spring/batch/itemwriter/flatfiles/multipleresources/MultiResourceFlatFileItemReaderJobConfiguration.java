package com.bofa.spring.batch.itemwriter.flatfiles.multipleresources;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;



@Configuration
public class MultiResourceFlatFileItemReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Value("classpath*:/data/customer*.csv")
	private Resource[] inputFiles;
	
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
	public StaxEventItemWriter<Customer> xmlItemWriter() throws Exception{
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
	public FlatFileItemWriter<Customer> jsonItemWriter() throws Exception{
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
		writer.setLineAggregator(new CustomerLineAggregator());
		String customAggregatorPath = File.createTempFile("customCustomerAggregateOutput", ".json").getAbsolutePath();
		System.out.println("OUTPUT PATH : "+customAggregatorPath);
		writer.setResource(new FileSystemResource(customAggregatorPath));
		writer.afterPropertiesSet();	
		return writer;
	}
	
	
	@Bean
	public CompositeItemWriter<Customer> itemWriter() throws Exception{
		List<ItemWriter<? super Customer>> itemWriter = new ArrayList<>();
		
		itemWriter.add(jsonItemWriter());
		itemWriter.add(xmlItemWriter());
		
		CompositeItemWriter<Customer> cWriter = new CompositeItemWriter<>();
		
		cWriter.setDelegates(itemWriter);
		cWriter.afterPropertiesSet();
		
		return cWriter;
	}
	

	@Bean
	public Step customMultiResourceFlatFileItemWriterStep() throws Exception {
		return stepBuilderFactory.get("customMultiResourceFlatFileItemWriterStep")
				.<Customer, Customer>chunk(10)
				.reader(pagingItemReader())
				.writer(itemWriter())
				.build();
	}
	@Bean
	public Job jobMultiResourceFlatFileItemWriter2() throws Exception {
		return jobBuilderFactory.get("jobMultiResourceFlatFileItemWriter1")
				.start(customMultiResourceFlatFileItemWriterStep())
				.build();
	}

	
	
}
