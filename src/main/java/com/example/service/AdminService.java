package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
@Transactional
public class AdminService {

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 利用者全ての購買履歴を取得する.
	 * 
	 * @return 利用者全ての購買履歴情報(Orderのstatusが1or2)
	 */
	public List<Order> findAll() {
		List<Order> orderList = orderRepository.findAll();
		return orderList; 
	}

}
