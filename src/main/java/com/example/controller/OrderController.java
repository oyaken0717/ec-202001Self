package com.example.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.service.CartService;
import com.example.service.OrderService;

/**
 * カートに入れた注文情報どうのこうのする.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 注文確認画面にいく.
	 * 
	 * @param loginUser ログインユーザー情報
	 * @param model モデル
	 * @return 注文確認画面へ
	 */
	@RequestMapping("/to-order-confirm")
	public String toOrderConfirm(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		//■引数の「0」はstatus		
		Order order = cartService.findByUserIdAndStatus(userId, 0);
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("order", order);
		return "order_confirm";
	}
	
	/**
	 * 注文をする.
	 * 
	 * @return toOrderFinish()メソッドへ
	 */
	@RequestMapping("/decide")
	public String decide(OrderForm form, @AuthenticationPrincipal LoginUser loginUser) {
		System.out.println("form.getDeliveryDate()");
		System.out.println(form.getDeliveryDate());
		
		System.out.println("form.getDeliveryTime()");
		System.out.println(form.getDeliveryTime());

		Integer userId = loginUser.getUser().getId();
		//■「0」はstatus
		Order order= cartService.findByUserIdAndStatus(userId, 0);
		//■配達時間 =　日時 + 時間 > deliveryTime > sql内はTimestamp型 > 1970-01-01 00:00:01の形にしないといけない。	
		Timestamp time = orderService.stringToTimestamp(form);
		
		System.out.println("Timestamp time");
		System.out.println(time);
		
		BeanUtils.copyProperties(form, order);
		
		order.setTotalPrice(order.getCalcTotalPrice());
		order.setOrderDate(new Date());
		order.setDeliveryTime(time);
		
		System.out.println("order.getDeliveryTime()");
		System.out.println(order.getDeliveryTime());
		
		order.setStatus(form.getPaymentMethod());
		orderService.save(order);
		return "redirect:/order/to-order-finish";
	}
	
	/**
	 * 注文完了画面へ.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/to-order-finish")
	public String toOrderFinish() {
		return "order_finished";
	}
	
	/**
	 * 注文履歴画面へ.
	 * 
	 * @param loginUser ログインユーザー
	 * @param model 注文が確定した情報の入ったリスト
	 * @return 注文履歴画面
	 */
	@RequestMapping("/to-order-history")
	public String toOrderHistory(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getId();
		List<Order> orderList = orderService.findByUserId(userId);
		System.out.println(orderList);
		model.addAttribute("orderList",orderList);
		return "order_history";
	}
}
