package com.example.form;

import java.util.List;

/**
 * カートに入れる商品情報のドメイン
 * 
 * @author oyamadakenji
 *
 */
public class CartForm {
	
	/** 商品ID(ラーメン) */
	private Integer itemId;
	
	/** サイズ */
	private String size;
	
	/** 選択したトッピング */
	private List<Integer> orderToppingList;
	
	/** 数量 */
	private Integer quantity;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<Integer> getOrderToppingList() {
		return orderToppingList;
	}
	public void setOrderToppingList(List<Integer> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CartForm [itemId=" + itemId + ", size=" + size + ", orderToppingList=" + orderToppingList
				+ ", quantity=" + quantity + "]";
	}	
}
