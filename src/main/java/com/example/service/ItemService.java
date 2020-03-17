package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * ItemRepositoryからの情報を受け取る.
 * 
 * @author oyamadakenji
 *
 */
@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 全件表示をする.
	 * 
	 * @return 複数の商品情報の入ったリスト
	 */
	public List<Item> findAll() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}
	
	/**
	 * idから商品を見つける.
	 * 
	 * @param id 商品一覧から流れてくるid情報
	 * @return 商品の情報
	 */
	public Item load(Integer id) {
		Item item = itemRepository.load(id);
		return item;
	}
}
