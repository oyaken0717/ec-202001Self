package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemService;

@Controller
@RequestMapping("")
public class ItemListController {

	@Autowired
	private ItemService itemService; 
	
	/**
	 * 商品一覧画面へ.
	 * 
	 * @param model 商品一覧情報が入った「itemList」が入る予定
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String showItemList(Model model) {
		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList", itemList);
		return "item_list_noodle";
	}
}
