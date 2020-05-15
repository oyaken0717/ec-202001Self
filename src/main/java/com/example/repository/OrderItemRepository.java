package com.example.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * 注文した商品の情報を取得するドメイン.
 * 
 * @author oyamadakenji
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	public OrderItem save(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		if (orderItem.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			orderItem.setId(key.intValue());
		} else {
			// ■UPDATE処理
		}
		return orderItem;
	}
	
	/**
	 * 注文したラーメン(OrderItem)の「カート(Order)」のIDを<br>
	 * 未ログインのOrderのIDから<br>
	 * ログイン済のOrderのIDへ変更する。 
	 * 
	 * @param beforeLoginOrderId 未ログインの時に発行したOrderのID
	 * @param loginOrderId ログイン後に発行した未入金(status:0)のOrderのID
	 */
	public void changeOrderId(Long beforeLoginOrderId,Long loginOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(" order_items ");
		sql.append("SET ");
		sql.append(" order_id = :login_order_id ");
		sql.append("WHERE ");
		sql.append(" order_id = :before_login_order_id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("before_login_order_id", beforeLoginOrderId).addValue("login_order_id", loginOrderId);
		template.update(sql.toString(), param);
	}
}
