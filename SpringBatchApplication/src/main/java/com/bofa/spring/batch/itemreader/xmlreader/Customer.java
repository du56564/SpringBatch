package com.bofa.spring.batch.itemreader.xmlreader;

import java.util.Date;

public class Customer {

	private final long id;
	
	private final String firstName;
	
	private final String lastName;
	
	private final Date birthdate;

	public Customer(long id, String firstName, String lastName, Date birthdate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate=" + birthdate
				+ "]";
	}
		
}
