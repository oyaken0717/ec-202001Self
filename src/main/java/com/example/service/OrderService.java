package com.example.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	
	public void save(Order order) {
		orderRepository.save(order);
	}
}
