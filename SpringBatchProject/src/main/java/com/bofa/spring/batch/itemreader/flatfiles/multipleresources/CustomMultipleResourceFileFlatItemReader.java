package com.bofa.spring.batch.itemreader.flatfiles.multipleresources;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class CustomMultipleResourceFileFlatItemReader {

	private Resource[] inputfiles;
	
	public CustomMultipleResourceFileFlatItemReader(Resource[] inputfiles) {
		this.inputfiles=inputfiles;
	}
	
	@Bean
	public MultiResourceItemReader<Customer> multiResourceItemReader(){
		MultiResourceItemReader<Customer> reader = new MultiResourceItemReader<>();
		reader.setDelegate(flatFileItemReader());
		reader.setResources(inputfiles);
		
		return reader;
	}
	
	
	
	@Bean
	public FlatFileItemReader<Customer> flatFileItemReader(){
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		
		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"id", "firstName", "lastName", "birthdate"});
		
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();
		
		reader.setLineMapper(customerLineMapper);
		
		return reader;
	}
	
}
