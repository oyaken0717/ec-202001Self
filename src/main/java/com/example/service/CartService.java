package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.form.CartForm;
import com.example.repository.OrderRepository;

/**
 * 注文に関する情報を扱うサービス.
 * 
 * @author oyamadakenji
 *
 */
@Service
@Transactional
public class CartService {
	
	@Autowired
	private OrderRepository orderRepository;
	/**
	 * カートの情報を作るメソッド.
	 * 
	 * @param form 商品詳細画面の「カートに入れる」ボタンを押して流れてきForm
	 * @param userId ログインユーザーのID or 仮のsessionからのID
	 */
	public Order insert(CartForm form, Integer userId) {
		Order order = new Order();
		//■この3つはnot null制約		
		order.setUserId(userId);
		order.setStatus(0);
		order.setTotalPrice(0);
		order = orderRepository.save(order);
		return order;
	}
	
}
