package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.CartForm;
import com.example.service.CartService;
import com.example.service.OrderService;

@Controller
@RequestMapping("/cart")
public class CartContorller {
	
	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private HttpSession session;

	// ■@AuthenticationPrincipal
	// 大前提：LoginUserのデータ > Principal(メールアドレス)オブジェクトと繋がってる。
	// Principalオブジェクトと繋がったUserを引っ張ってくる。(UDSが情報をモデルに入れておいてくれてるからできる。)
	@RequestMapping("/add-cart")
	public String addCart(CartForm form, @AuthenticationPrincipal LoginUser loginUser) {
		// ■ログインしていない場合 > セッション自体のIDをuserIdにいれとく
		// ※文字列化している > hashCode()で数値化する。
//		Integer userId = (Integer)session.getId().hashCode();
		Integer userId = (Integer)session.getAttribute("userId");
		System.out.println("■ CartContorller/addCart/(Integer)session.getAttribute(\"userId\");=========================================");
		System.out.println("userId");
		System.out.println(userId);

		if(userId == null) {
			session.setAttribute("userId", session.getId().hashCode());
		}

		System.out.println("■ CartContorller/addCart/(Integer)session.getId().hashCode();---------------------------------");
		System.out.println("userId");
		System.out.println(userId);
		if (loginUser != null) {
			// ■ログインしている。 > そこからuserのIDを取ってくる。
			userId = loginUser.getUser().getId();
		}
		cartService.insert(form, userId);
		return "redirect:/cart/show-cart-list";
	}

	/**
	 * カートの中身を表示する.
	 * 
	 * @return カートの中身一覧画面
	 */
	@RequestMapping("/show-cart-list")
	public String showCartList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
//		Integer userId = (Integer)session.getId().hashCode();
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			session.setAttribute("userId", session.getId().hashCode());
			userId = (Integer)session.getAttribute("userId");
		}

		System.out.println("■session.getId().hashCode();---------------------------------");
		System.out.println("userId");
		System.out.println(userId);
		
		//■ 例外その1:ログイン「前」にカートに追加した時に生成されるorder情報を取得
		Order beforeLoginOrder = cartService.findByUserIdAndStatus(userId, 0);
		System.out.println("■cartService.findByUserIdAndStatus(userId, 0);---------------------------------");
		System.out.println("beforeLoginOrder");
		System.out.println(beforeLoginOrder);
				
		//■ ログイン「後」のUserのidを元にカートの情報を探す
		Order loginOrder = new Order();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
			loginOrder = cartService.findByUserIdAndStatus(userId, 0);
			System.out.println("■loginUser.getUser().getId();---------------------------------");
			System.out.println("userId");
			System.out.println(userId);

		}
		
		//■ 例外その2:カートの中身の情報を更新する。
		if(beforeLoginOrder != null && loginUser != null) {
			//■② もしログイン情報(LoginUser)はある。だけど未入金のカート(loginOrder)が無い場合
			if (loginOrder == null) {
				loginOrder = new Order();
				loginOrder.setUserId(userId);
				loginOrder.setStatus(0);
				loginOrder.setTotalPrice(0);
				orderService.save(loginOrder);
			}
			//■① 更新する。
			cartService.changeOrderId(beforeLoginOrder.getId(),loginOrder.getId());
			cartService.deleteByOrderId(beforeLoginOrder.getId());
		}
		
		//■ userIdから一覧を表示する。(例外の関係で、再度userIdからOrderを取ってくる。)	
		Order order = cartService.findByUserIdAndStatus(userId, 0);
		
		System.out.println("■Order order = cartService.findByUserIdAndStatus(userId, 0);---------------------------------");
		System.out.println("order");
		System.out.println(order);
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("order", order);
		return "cart_list";
	}
	
	@RequestMapping("/delte-order-item")
	public String delteOrderItem(int orderItemId) {
		cartService.delteOrderItem(orderItemId);
		return "redirect:/cart/show-cart-list";
	}
}
