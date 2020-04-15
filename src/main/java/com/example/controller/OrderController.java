package com.example.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Credit;
import com.example.domain.CreditResult;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.CreditForm;
import com.example.form.OrderForm;
import com.example.service.CartService;
import com.example.service.CreditService;
import com.example.service.EmailService;
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

	@Autowired
	private CreditService creditService;

	@Autowired
	private EmailService emailService;

	@Bean
	RestTemplate RestTemplate() {
		return new RestTemplate();
	}

	/**
	 * 注文確認画面にいく.
	 * 
	 * @param loginUser ログインユーザー情報
	 * @param model     モデル
	 * @return 注文確認画面へ
	 */
	@RequestMapping("/to-order-confirm")
	public String toOrderConfirm(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		// ■引数の「0」はstatus
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
	public String decide(OrderForm form, CreditForm creditForm, @AuthenticationPrincipal LoginUser loginUser,
			Model model) {

		Integer userId = loginUser.getUser().getId();
		// ■「0」はstatus
		Order order = cartService.findByUserIdAndStatus(userId, 0);

		BeanUtils.copyProperties(form, order);
		order.setTotalPrice(order.getCalcTotalPrice());
		order.setOrderDate(new Date());
		// ■配達時間 = 日時 + 時間 > deliveryTime > sql内はTimestamp型 > 1970-01-01
		// 00:00:01の形にしないといけない。
		Timestamp time = orderService.stringToTimestamp(form);
		order.setDeliveryTime(time);
		order.setStatus(form.getPaymentMethod());

		if (form.getPaymentMethod() == 1) {
			orderService.save(order);
			return "redirect:/order/to-order-finish";
		}

		// ■CreditWebAPIにつなぐ
		Credit credit = new Credit();
		// ■「card_name」のみ入る
		BeanUtils.copyProperties(creditForm, credit);

		credit.setUser_id(userId);
		credit.setOrder_number(order.getId());
		credit.setAmount(order.getTotalPrice());

		credit.setCard_number(creditForm.getIntCard_number());
		credit.setCard_exp_year(creditForm.getIntCard_exp_year());
		credit.setCard_exp_month(creditForm.getIntCard_exp_month());
		credit.setCard_cvv(creditForm.getIntCard_cvv());

		// ■クレジットカード
		CreditResult creditResult = creditService.payment(credit);
		if ("error".equals(creditResult.getStatus())) {
			System.out.println("失敗");
			System.out.println("---------------------");
			model.addAttribute("message", "クレジットカード情報が不正です。");
			return toOrderConfirm(loginUser, model);
		}
		System.out.println("成功");
		System.out.println("---------------------");
		// ■注文を確定(更新)する。
		try {
			orderService.save(order);
		} catch (Exception e) {
			System.out.println("例外が発生しました!");
			e.printStackTrace();
			// ■ WebAPIで「キャンセルしました」という情報を送る。
			creditResult = creditService.cancel(credit);
			// ■ statusをキャンセル(9)にする
			order.setStatus(9);
			orderService.update(order);
			return "redirect:/order/to-order-confirm";
		}
		// ■メールを送る。
		try {
			emailService.sendMail(order);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("例外が発生しました!");
			e.printStackTrace();
			// ■ WebAPIで「キャンセルしました」という情報を送る。
			creditResult = creditService.cancel(credit);
			// ■ statusをキャンセル(9)にする
			order.setStatus(9);
			orderService.update(order);			
			return "redirect:/order/to-order-confirm";
		}
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
	 * @param model     注文が確定した情報の入ったリスト
	 * @return 注文履歴画面
	 */
	@RequestMapping("/to-order-history")
	public String toOrderHistory(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = loginUser.getUser().getId();
		List<Order> orderList = orderService.findByUserId(userId);
		System.out.println(orderList);
		model.addAttribute("orderList", orderList);
		return "order_history";
	}
}
