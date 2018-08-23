package com.bofa.spring.batch.validator.flatfileswriter;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class CustomValidator implements Validator<Customer>{



	@Override
	public void validate(Customer value) throws ValidationException {
		if (value.getFirstName().startsWith("A")) {
			throw new ValidationException("First Name starts with \"A\" is invalid."); 	
		}
		
	}
}
