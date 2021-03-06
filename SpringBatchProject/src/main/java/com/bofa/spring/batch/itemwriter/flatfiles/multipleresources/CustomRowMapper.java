package com.bofa.spring.batch.itemwriter.flatfiles.multipleresources;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new Customer(rs.getLong("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getDate("birthdate"));
	}

}
