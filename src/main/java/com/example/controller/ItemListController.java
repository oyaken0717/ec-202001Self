package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	public String showItemList(Model model, HttpServletRequest request) throws IOException, ServletException{
		//■ クッキーを取り出す。
		Cookie cookies[] = request.getCookies();
		//■ 商品一覧を準備する。
		List<Item> itemList = itemService.findAll();

		String cookieNo = null;
		String itemNo = null;
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {	
				System.out.println("cookie1-----------------");
				System.out.println(cookie.getName());
				System.out.println(cookie.getValue());
				for (Item item : itemList) {
					cookieNo = cookie.getName();
					itemNo = String.valueOf(item.getId());
					if (cookieNo.equals(itemNo)) {
						item.setCookie(1);
					}					
				}
			}
		}
		
		model.addAttribute("itemList", itemList);
		System.out.println("itemListーーーーーーーーーーーーーーー");
		System.out.println(itemList.toString());
		return "item_list_noodle";
	}
}
