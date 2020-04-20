package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Graph;

@Repository
public class GraphRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private  static final RowMapper<Graph> GRAPH_ROW_MAPPER = (rs,i)->{
		Graph graph = new Graph();
		graph.setYear(rs.getInt("year"));
		graph.setMonth(rs.getInt("month"));
		graph.setTotalPrice(rs.getInt("total_price"));
		return graph;
	};
	
	/**
	 * 年月ごとの売り上げを取得する.
	 * 
	 * @return
	 */
	public List<Graph> findByYear(Integer year) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" date_part('year', order_date) AS year,");
		sql.append(" date_part('month', order_date) AS month,");
		sql.append(" SUM(total_price) AS total_price ");
		sql.append("FROM");
		sql.append(" orders ");
		sql.append("WHERE ");
		sql.append(" date_part('year', order_date) = :year ");
		
		sql.append("GROUP BY ");
		sql.append(" year, month ");
		sql.append("ORDER BY ");
		sql.append(" year, month ");
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("year", year);
		List<Graph> saleList = template.query(sql.toString(), param, GRAPH_ROW_MAPPER);
		return saleList;
	}
}
