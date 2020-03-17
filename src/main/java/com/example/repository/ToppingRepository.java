package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	public static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs,i) ->{
		Topping topping = new Topping();
		return topping;
	};
}
