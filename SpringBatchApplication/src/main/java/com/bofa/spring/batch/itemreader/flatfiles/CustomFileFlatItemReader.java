package com.bofa.spring.batch.itemreader.flatfiles;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

public class CustomFileFlatItemReader {

	public CustomFileFlatItemReader() {
	}
	
	@Bean
	public FlatFileItemReader<Customer> flatFileItemReader(){
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("/customer.csv"));
		
		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "firstName", "lastName", "birthDate");
		
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomeFieldSetMapper());
		customerLineMapper.afterPropertiesSet();
		
		reader.setLineMapper(customerLineMapper);
		
		return reader;
	}
	
}
