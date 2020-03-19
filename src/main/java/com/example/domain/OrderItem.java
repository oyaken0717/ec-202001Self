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
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** 一緒に注文したトッピングのID */
	private List<OrderTopping> orderToppingList;
	
	public Integer getSubTotal() {
		return 1;
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
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
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
				+ ", size=" + size + ", item=" + item + "]";
	}
}
