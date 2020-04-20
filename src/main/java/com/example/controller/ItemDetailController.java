package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class ItemDetailController extends HttpServlet {

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
	public String showDetail(Integer id, Model model,HttpServletResponse response)  throws IOException, ServletException{
		String itemNum = String.valueOf(id);
		//■ クッキーを入れる。
		Cookie cookie = new Cookie(itemNum, "1");
		response.addCookie(cookie);
		System.out.println("cookie2ーーーーーーーーーーーーーーーー");
		System.out.println(cookie.getName());
		System.out.println(cookie.getValue());
		
		//■ 商品詳細をloadする。
		Item item = itemService.load(id);
		List<Topping> toppingList = toppingService.findAll();
		item.setToppingList(toppingList);
		model.addAttribute("item", item);
		return "item_detail";
	}	
}
