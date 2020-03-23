package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * Order(カート)の情報を取得するレポジトリ.
 * 
 * @author oyamadakenji
 *
 */
@Repository
public class OrderRepository2 {

	@Autowired
	private NamedParameterJdbcTemplate template;

	// ■自動生成されたIDを取得できるようになる。
	private SimpleJdbcInsert insert;

	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		Order order = new Order();
		List<Order> orderList = new ArrayList<>();
		List<OrderItem> orderItemList = new ArrayList<>();
		List<OrderTopping> orderToppingList = new ArrayList<>();
		List<Topping> toppingList = new ArrayList<>();
		
		int beforeOrderId = 0;
		int firstOrderItemId = 0;
		
		while (rs.next()) {
			int nowOrderId = rs.getInt("order_id");//■o.id 注文ID
			if(nowOrderId != beforeOrderId) {
				order = new Order();
				orderItemList = new ArrayList<>();
				order.setId(rs.getInt("order_id"));
			}
				
		}
		return null;
	};

	// ■init()
	// OrderRepositoryがインスタンス化 > 1度だけ実行される。
	// > idカラムが自動採番される。 > Springに教える。
	@PostConstruct
	public void init() {
		// ■JdbcTemplateで挿入をしている。(@Autowired > メソッド内で呼ばれる。 > SQLの発行)
		insert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcTemplate());
		// ■①テーブル名を設定する。 > ②自動採番されるカラム名を設定する。
		insert = insert.withTableName("orders").usingGeneratedKeyColumns("id");
	}

	/**
	 * 注文情報をカートに入れる.
	 * 
	 * @param order
	 * @return
	 */
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

	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		StringBuilder sql = new StringBuilder();
//■商品
		sql.append("SELECT i.id item_id,i.name item_name,i.description item_description,i.price_m item_price_m,");
		sql.append("i.price_l item_price_l,i.image_path item_image_path,i.deleted item_deleted,");
//■注文(長い)
		sql.append("o.id order_id,o.user_id order_user_id,o.status order_status,o.total_price order_total_price,");
		sql.append("o.order_date order_date,o.destination_name order_destination_name,o.destination_email order_destination_email,");
		sql.append("o.destination_zipcode order_destination_zipcode,o.destination_address order_destination_address,");
		sql.append("o.destination_tel order_destination_tel,o.delivery_time order_delivery_time,o.payment_method order_payment_method,");
//■注文商品
		sql.append("oi.id orderitem_id,oi.item_id orderitem_item_id,oi.order_id orderitem_order_id, oi.quantity orderitem_quantity,oi.size orderitem_size,");
		sql.append("ot.id order_topping_id,ot.topping_id topping_id,ot.order_item_id order_item_id,");		
//■注文トッピング
		sql.append("t.name topping_name,t.price_m topping_price_m,t.price_l topping_price_l ");
//■FROM ①INNER JOIN > 左と右のテーブルがあるのが前提 > 無いとエラー 
		sql.append("FROM orders o JOIN order_items oi ON o.id = oi.order_id ");		
// ②LEFT OUTER JOIN > 基本LEFT OUTER JOINでOK > 左側に右側をくっつける > 右なくてもエラーにならない。 
		sql.append("LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id ");
		sql.append("INNER JOIN items i ON oi.item_id = i.id LEFT OUTER JOIN toppings t ON ot.toppimg_id = t.id ");
//■WHERE
		sql.append("WHERE o.user_id = :user_id AND o.status = :status ORDER BY oi.id");
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_id", userId).addValue("status", status);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		if (orderList.size() > 0) {
			return orderList.get(0);
		}
		return null;
	}
}
