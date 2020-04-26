package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Graph;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.repository.GraphRepository;
import com.example.service.AdminService;
import com.example.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminContorller {

	@Autowired
	private AdminService adminService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private GraphRepository graphRepository;
	
	/**
	 * 管理者機能のまとめ画面へ
	 * 
	 * @return 管理者機能のまとめ画面
	 */
	@RequestMapping("/to-admin-option")
	public String toAdminOption() {
		return "admin_option";
	}
	/**
	 * 全ユーザーの注文一覧画面
	 * 
	 * @param model　全ユーザーの注文(Orderのstatusが1or2)情報が入ったList
	 * @return 注文一覧画面
	 */
	@RequestMapping("/to-order-list")
	public String toOrderList(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		List<Order> orderList = adminService.findAll();
		//■idJoinをfalseにしてOrderのみをROW_MAPPERでとる。
		//Integer userId = loginUser.getUser().getId();
		//boolean isJoin = false;
		//List<Order> orderList = orderService.findByUserId(userId, isJoin);
		model.addAttribute("orderList",orderList);
		return "order_list";
	}
	
    /**
     * 売り上げグラフ画面へ
     * 
     * @param model タテとヨコ軸の値を取得する.
     * @param year セレクトボックスで選ばれた「年」
     * @return 売り上げグラフ画面
     */
    @RequestMapping("/graph")
    public String graph(Model model) {
//    public String graph(Model model,Integer year) {
//    	if (year == null) {
//    		year = 2020;			
//		}
//    	
//    	List<Graph> saleList = graphRepository.findByYear(year);
//        List<Integer> tates = new ArrayList<>();
//        List<Integer> yokos = new ArrayList<>();
//        for (Graph sale : saleList) {
//        	//■縦軸 入力した値から空気を読んで縦軸を作ってくれる。
//			tates.add(sale.getTotalPrice());
//			//■横軸 1〜12月までを格納する。
//			yokos.add(sale.getMonth());			
//		}
//        
//        //■ ビュー側でグラフ用の配列を受け取れるようにする。
//        model.addAttribute("yokos",yokos);
//        model.addAttribute("tates",tates);
//        model.addAttribute("DAIMEI","売上");

        return "graph";
    }

}
