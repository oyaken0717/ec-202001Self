package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Topping;
import com.example.repository.ToppingRepository;

/**
 * トッピング情報を取得するサービス.
 * 
 * @author oyamadakenji
 *
 */
@Service
@Transactional
public class ToppingService {

	@Autowired
	private ToppingRepository toppingRepository;
	
	/**
	 * 全てのトッピング情報を取得するサービス
	 * 
	 * @return 全てのトッピング情報を取得したリスト
	 */
	public List<Topping> findAll() {
		List<Topping> toppingList = toppingRepository.findAll();
		return toppingList;
	}
}
