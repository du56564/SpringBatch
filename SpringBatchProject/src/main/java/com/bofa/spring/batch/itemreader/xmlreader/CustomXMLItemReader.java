package com.bofa.spring.batch.itemreader.xmlreader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

public class CustomXMLItemReader {


	@Bean
	public StaxEventItemReader<Customer> customXMLItemReader() {
		XStreamMarshaller unMarshaller = new XStreamMarshaller();
		StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
		Map<String,Class> aliases = new HashMap<>();
		try {
		
		aliases.put("customer", Customer.class);
		unMarshaller.setAliases(aliases);
		
		reader.setResource(new ClassPathResource("/customers.xml"));
		reader.setFragmentRootElementName("customer");
		reader.setUnmarshaller(unMarshaller);
		reader.afterPropertiesSet();
		}catch (Exception e) {
			System.err.println("Error::"+e.getMessage());
		}
		return reader;
		
		
	}
	
}
