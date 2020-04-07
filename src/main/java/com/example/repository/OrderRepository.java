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

import com.example.domain.Item;
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
public class OrderRepository {

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
		int beforeOrderItemId = 0;

		while (rs.next()) {
			int nowOrderId = rs.getInt("order_id");// ■o.id 注文ID
			if (nowOrderId != beforeOrderId) {
				order = new Order();
				orderItemList = new ArrayList<>();
				order.setId(rs.getInt("order_id"));
				order.setUserId(rs.getInt("order_user_id"));
				order.setStatus(rs.getInt("order_status"));
				order.setTotalPrice(rs.getInt("order_total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("order_destination_name"));
				order.setDestinationEmail(rs.getString("order_destination_email"));
				order.setDestinationZipcode(rs.getString("order_destination_zipcode"));
				order.setDestinationAddress(rs.getString("order_destination_address"));
				order.setDestinationTel(rs.getString("order_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("order_delivery_time"));
				order.setPaymentMethod(rs.getInt("order_payment_method"));
				order.setOrderItemList(orderItemList);
				orderList.add(order);
			}
			if (rs.getInt("orderitem_id") != beforeOrderItemId && rs.getInt("orderitem_id") != beforeOrderId) {
				OrderItem orderItem = new OrderItem();
				Item item = new Item();
				toppingList = new ArrayList<>();
				orderToppingList = new ArrayList<>();

				orderItemList.add(orderItem);
				orderItem.setId(rs.getInt("orderitem_id"));
				orderItem.setItemId(rs.getInt("orderitem_item_id"));
				orderItem.setOrderId(rs.getInt("orderitem_order_id"));
				orderItem.setQuantity(rs.getInt("orderitem_quantity"));
				orderItem.setSize(rs.getString("orderitem_size").toCharArray()[0]);
				orderItem.setItem(item);
				orderItem.setOrderToppingList(orderToppingList);

				item.setId(rs.getInt("item_id"));
				item.setName(rs.getString("item_name"));
				item.setDescription(rs.getString("item_description"));
				item.setPriceM(rs.getInt("item_price_m"));
				item.setPriceL(rs.getInt("item_price_l"));
				item.setImagePath(rs.getString("item_image_path"));
				item.setDeleted(rs.getBoolean("item_deleted"));
				item.setToppingList(toppingList);
			}
			if (rs.getInt("order_topping_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				Topping topping = new Topping();
				toppingList.add(topping);
				orderToppingList.add(orderTopping);
				orderTopping.setId(rs.getInt("order_topping_id"));
				orderTopping.setToppingId(rs.getInt("topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_item_id"));
				orderTopping.setTopping(topping);

				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
			}
			beforeOrderItemId = rs.getInt("orderitem_id");
			beforeOrderId = rs.getInt("order_id");
		}
		return orderList;
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
	 * insert:注文情報をカートに入れる.<br>
	 * update:カートに入れた商品を注文する
	 * 
	 * @param order
	 * @return
	 */
	public Order save(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		if (order.getId() == null) {
			// ■INSERT文の自動生成 > 自動採番idがNumberオブジェクトとして返る。 > key変数で受け取る。
			Number key = insert.executeAndReturnKey(param);
			// ■int型に変換 > セット
			order.setId(key.intValue());
		} else {
			StringBuilder sql=new StringBuilder();
			sql.append("UPDATE orders SET user_id=:userId,status=:status,total_price=:totalPrice,order_date=:orderDate,");
			sql.append("destination_name=:destinationName,destination_email=:destinationEmail,destination_zipcode=:destinationZipcode,");
			sql.append("destination_address=:destinationAddress,destination_tel=:destinationTel,delivery_time=:deliveryTime,");
			sql.append("payment_method=:paymentMethod WHERE id=:id");
			template.update(sql.toString(), param);
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
		sql.append(
				"o.order_date order_date,o.destination_name order_destination_name,o.destination_email order_destination_email,");
		sql.append("o.destination_zipcode order_destination_zipcode,o.destination_address order_destination_address,");
		sql.append(
				"o.destination_tel order_destination_tel,o.delivery_time order_delivery_time,o.payment_method order_payment_method,");
//■注文商品
		sql.append(
				"oi.id orderitem_id,oi.item_id orderitem_item_id,oi.order_id orderitem_order_id, oi.quantity orderitem_quantity,oi.size orderitem_size,");
//■注文トッピング
		sql.append("ot.id order_topping_id,ot.topping_id topping_id,ot.order_item_id order_item_id,");
//■トッピング
		sql.append("t.name topping_name,t.price_m topping_price_m,t.price_l topping_price_l ");
//■FROM ①INNER JOIN > 左と右のテーブルがあるのが前提 > 無いとエラー 
		sql.append("FROM orders o JOIN order_items oi ON o.id = oi.order_id ");
// ②LEFT OUTER JOIN > 基本LEFT OUTER JOINでOK > 左側に右側をくっつける > 右なくてもエラーにならない。 
		sql.append("LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id ");
		sql.append("INNER JOIN items i ON oi.item_id = i.id LEFT OUTER JOIN toppings t ON ot.topping_id = t.id ");
//■WHERE
		sql.append("WHERE o.user_id = :user_id AND o.status = :status ORDER BY oi.id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("user_id", userId).addValue("status", status);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		if (orderList.size() > 0) {
			return orderList.get(0);
		}
		return null;
	}
	
	/**
	 * ユーザーIDから注文履歴(statusが1か2)を取得する.
	 * 
	 * @param userId　ログインユーザーのID
	 * @return 購入したラーメンの情報が入ったリスト
	 */
	public List<Order> findByUserId(Integer userId) {
		StringBuilder sql = new StringBuilder();
//■ Order
		sql.append("SELECT ");
		sql.append(" o.id order_id, o.user_id order_user_id, o.status order_status, o.total_price order_total_price, o.order_date order_date, ");
		sql.append(" o.destination_name order_destination_name, o.destination_email order_destination_email, o.destination_zipcode order_destination_zipcode, ");
		sql.append(" o.destination_address order_destination_address, o.destination_tel order_destination_tel, o.delivery_time order_delivery_time, ");
		sql.append(" o.payment_method order_payment_method, ");
//■ OrderItem
		sql.append(" oi.id orderitem_id, oi.item_id orderitem_item_id, oi.order_id orderitem_order_id, ");
		sql.append(" oi.quantity orderitem_quantity, oi.size orderitem_size, ");
//■ Item
		sql.append(" i.id item_id, i.name item_name, i.description item_description, i.price_m item_price_m, ");
		sql.append(" i.price_l item_price_l, i.image_path item_image_path, i.deleted item_deleted, ");
//■ OrderTopping
		sql.append(" ot.id order_topping_id, ot.topping_id topping_id, ot.order_item_id order_item_id, ");
//■ Topping
		sql.append(" t.id topping_id, t.name topping_name, t.price_m topping_price_m, t.price_l topping_price_l ");
//■ 結合
		sql.append("FROM ");
		sql.append(" orders o ");
		sql.append(" LEFT OUTER JOIN order_items oi    ON o.id = oi.order_id ");
		sql.append(" INNER JOIN            items i     ON oi.item_id = i.id ");
		sql.append(" LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id ");
		sql.append(" INNER JOIN            toppings t  ON ot.topping_id = t.id ");
//■ WHERE
		sql.append("WHERE ");
		sql.append(" o.user_id = :user_id ");
		sql.append("AND ");
		sql.append(" o.status IN (1,2) ");
		sql.append("ORDER BY ");
		sql.append(" o.id DESC ");

		SqlParameterSource parame = new MapSqlParameterSource().addValue("user_id", userId);
		List<Order> orderList = template.query(sql.toString(), parame, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}

	public void deleteById(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		String sql = "WITH deleted AS (DELETE FROM order_items WHERE id = :id RETURNING id)"
				+ "DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted)";
		template.update(sql, param);
	}

}
