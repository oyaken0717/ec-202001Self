package com.example.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Timestamp stringToTimestamp(OrderForm form) {
		try {
			Timestamp time = new Timestamp(new SimpleDateFormat("yyyy-MM-dd,h").parse(form.getDeliveryDate()+","+form.getDeliveryTime()).getTime());
			return time;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 注文情報を記録する.
	 * 
	 * @param order 注文情報が入ったオブジェクト
	 */
	public void save(Order order) {
		orderRepository.save(order);
	}
	
	/**
	 * 注文履歴の情報を取得する.
	 * 
	 * @param userId ログインユーザーのID
	 * @return 注文が確定したOrderのオブジェクト
	 */
	public List<Order> findByUserId(Integer userId, boolean isJoin) {
		List<Order> orderList = orderRepository.findByUserId(userId, isJoin);
		return orderList ;
	}
	
	/**
	 * 何らかの理由により「キャンセル」になった場合の処理.<br>
	 * ①管理者の権限 ②メールアプリ側の問題 ③SQL側の問題
	 * 
	 * @param order カートに既に入っていて、これから注文を確定させようといていたOrder(注文)
	 */
	public void update(Order order) {
		orderRepository.update(order);
	}
	
}
