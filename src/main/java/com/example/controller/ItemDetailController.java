package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.ItemService;
import com.example.service.ToppingService;

@Controller
@RequestMapping("item-detail")
public class ItemDetailController {

	@Autowired
	public ItemService itemService;
	
	@Autowired
	public ToppingService toppingService;
	
	
}
