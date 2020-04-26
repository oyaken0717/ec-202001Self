package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Sale;

@Repository
public class ChartRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template; 

	private static final RowMapper<Sale> SALE_ROW_MAPPER = (rs,i) ->{
		Sale sale = new Sale();
		sale.setYear(rs.getInt("year"));
		sale.setMonth(rs.getInt("month"));
		sale.setTotalPrice(rs.getInt("total_price"));
		return sale;
	};
	
	public List<Sale> fingByYear(Integer year){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" date_part('year', order_date) AS year, ");
		sql.append(" date_part('month', order_date) AS month, ");
		sql.append(" SUM(total_price) AS total_price ");
		sql.append("FROM");
		sql.append(" orders ");
		sql.append("WHERE");
		sql.append(" date_part('year', order_date) = :year ");
		sql.append("GROUP BY ");
		sql.append(" year, month ");
		sql.append("ORDER BY ");
		sql.append(" year, month");

		SqlParameterSource param = new MapSqlParameterSource().addValue("year", year);		
		List<Sale> saleList = template.query(sql.toString(), param, SALE_ROW_MAPPER);
		
		return saleList;
	}
}
