package com.example.domain;

import java.util.List;

/**
 * 注文した(カートに入れた)商品の情報を入れるドメイン.
 * 
 * @author oyamadakenji
 *
 */
public class OrderItem {

	/** カートに入れた商品のID */
	private Integer id;
	/** 商品のID */
	private Integer itemId;
	/** Orderドメイン(カート)のID */
	private Long orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** 一緒に注文したトッピングのID */
	private List<OrderTopping> orderToppingList;

	/**
	 * 注文した商品(らーめん)ごとの合計金額(subTotal)
	 * 
	 * @return 注文した商品ごとの合計金額
	 */
	public Integer getSubTotal() {
		Integer orderItemPrice = 0;
		Integer orderToppingPrice = 0;
		Integer toppingPriceM = 200;
		Integer toppingPriceL = 300;
		Integer subTotal =0;

		if(this.size == 'M') {
			orderItemPrice = item.getPriceM();
			orderToppingPrice = orderToppingList.size() * toppingPriceM;
		}else {
			orderItemPrice = item.getPriceL();
			orderToppingPrice = orderToppingList.size() * toppingPriceL;			
		}
		subTotal = (orderItemPrice + orderToppingPrice) * quantity;
		return subTotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}
}
