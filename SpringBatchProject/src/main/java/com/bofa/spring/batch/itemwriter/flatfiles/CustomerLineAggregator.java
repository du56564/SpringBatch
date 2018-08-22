package com.bofa.spring.batch.itemwriter.flatfiles;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerLineAggregator implements LineAggregator<Customer> {

	private ObjectMapper ObjectMapper = new ObjectMapper();
	
	@Override
	public String aggregate(Customer item) {		
		try {
			//System.out.println("ITEM:"+ item.toString());
			return ObjectMapper.writeValueAsString(item);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize customer: "+e);
		}
	}

}
