package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.CartForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
//import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepositry;

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

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepositry orderToppingRepositry;

	/**
	 * カートの情報を作るメソッド.
	 * 
	 * @param form   商品詳細画面の「カートに入れる」ボタンを押して流れてきたForm
	 * @param userId ログインユーザーのID or 仮のsessionからのID
	 */
	public void insert(CartForm form, Integer userId) {
		Order preOrder = orderRepository.findByUserIdAndStatus(userId, 0);
		if (preOrder == null) {
			// ■Order(カート)を挿入
			Order order = new Order();
			// ■この3つはnot null制約
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);
			order = orderRepository.save(order);

			// ■Order(カート)に注文商品を挿入
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(form, orderItem);
			orderItem.setOrderId(order.getId());
			orderItem = orderItemRepository.save(orderItem);

			// ■Order(カート)に注文トッピングを挿入
			if (form.getOrderToppingList() != null) {
				// ■コンソール：orderToppingList=[1, 2] > トッピングのIDがListで入ってる。
//				for (Integer orderToppingId : form.getOrderToppingList()) {
//					OrderTopping orderTopping = new OrderTopping();
//					orderTopping.setToppingId(orderToppingId);
//					orderTopping.setOrderItemId(orderItem.getId());
//					orderToppingRepositry.insert(orderTopping);
//				}
				
				//■ラムダ式内でラムダ式外にある変数を書き換えることはできません。の為
				Integer orderItemId = orderItem.getId();
				form.getOrderToppingList().forEach(orderToppingId -> {
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setToppingId(orderToppingId);
					orderTopping.setOrderItemId(orderItemId);
					orderToppingRepositry.insert(orderTopping);
				});
			}
		} else if (preOrder.getStatus() == 0) {
			// ■Order(カート)に注文商品を挿入
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(form, orderItem);
			orderItem.setOrderId(preOrder.getId());
			orderItem = orderItemRepository.save(orderItem);

			// ■Order(カート)に注文トッピングを挿入
			if (form.getOrderToppingList() != null) {
				// ■コンソール：orderToppingList=[1, 2] > トッピングのIDがListで入ってる。
//				for (Integer orderToppingId : form.getOrderToppingList()) {
//					OrderTopping orderTopping = new OrderTopping();
//					orderTopping.setToppingId(orderToppingId);
//					orderTopping.setOrderItemId(orderItem.getId());
//					orderToppingRepositry.insert(orderTopping);
//				}
				
				Integer orderItemId = orderItem.getId();
				form.getOrderToppingList().stream()
					.forEach(orderToppingId -> {
						OrderTopping orderTopping = new OrderTopping();
						orderTopping.setToppingId(orderToppingId);
						orderTopping.setOrderItemId(orderItemId);
						orderToppingRepositry.insert(orderTopping);
				}); // 終端操作
			}
		}
	}

	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		Order order = orderRepository.findByUserIdAndStatus(userId, status);
		return order;
	}
	
	/**
	 * 注文したラーメン(OrderItem)の「カート(Order)」のIDを<br>
	 * 未ログインのOrderのIDから<br>
	 * ログイン済のOrderのIDへ変更する。
	 *  
	 * @param beforeLoginOrderId 未ログインの時に発行したOrderのID
	 * @param loginOrderId ログイン後に発行した未入金(status:0)のOrderのID
	 * 
	 */
	public void changeOrderId(Long beforeLoginOrderId,Long loginOrderId) {
		orderItemRepository.changeOrderId(beforeLoginOrderId, loginOrderId);
	}
	
	public void deleteByOrderId(Long orderId) {
		orderRepository.deleteByOrderId(orderId);
	}
	
	public void delteOrderItem(Integer orderItemId) {
		orderRepository.deleteById(orderItemId);
	}

}
