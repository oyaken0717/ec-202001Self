package com.example.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.example.domain.Order;

/**
 * Order(カート)の情報を取得するレポジトリ.
 * 
 * @author oyamadakenji
 *
 */
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	// ■自動生成されたIDを取得できるようになる。
	private SimpleJdbcInsert insert;

	// ■init()メソッド
	// EmployeeRepositoryがインスタンス化 > 1度だけ実行される。
	// > idカラムが自動採番される。 > Springに教える。
	@PostConstruct
	public void init() {
		// ■JdbcTemplateで挿入をしている。(メソッド内で呼ばれる。 > SQLの発行)
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		// ■テーブル名を設定する。
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("employees");
		// ■自動採番されるカラム名を設定する。
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	public Order save(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		if (order.getId() == null) {
			// ■INSERT文の自動生成
			// > 自動採番idがNumberオブジェクトとして返る。 > key変数で受け取る。
			Number key = insert.executeAndReturnKey(param);
			// ■int型に変換 > セット
			order.setId(key.intValue());
		} else {
			// (省略)UPDATE処理に変更はありません
		}
		return order;
	}
}
