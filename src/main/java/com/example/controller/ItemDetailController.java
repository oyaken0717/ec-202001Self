package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.service.ItemService;
import com.example.service.ToppingService;

/**
 * 商品詳細画面に関するコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/item-detail")
public class ItemDetailController {

	@Autowired
	public ItemService itemService;
	
	@Autowired
	public ToppingService toppingService;
	
	/**
	 * 商品詳細画面にいく.
	 * 
	 * @param id 商品一覧から流れてくるid
	 * @param model モデル
	 * @return 商品詳細画面へ
	 */
	@RequestMapping("/show-detail")
	public String showDetail(Integer id, Model model) {
		Item item = itemService.load(id);
		List<Topping> toppingList = toppingService.findAll();
		item.setToppingList(toppingList);
		model.addAttribute("item", item);
		return "item_detail";
	}	
}
