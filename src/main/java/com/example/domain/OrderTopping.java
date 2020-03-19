package com.example.domain;


/**
 * 商品と一緒に注文んしたトッピングのドメイン.
 * 
 * @author oyamadakenji
 *
 */
public class OrderTopping {

	
	/** カートに入れたトッピングのID */
	private Integer id;
	/** トッピング自体のID */
	private Integer toppingId;
	/** カートに入れた商品のID */
	private Integer orderItemId;	
	/** トッピング(List形式でOrderItemに入れる) */
	private Topping topping;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getToppingId() {
		return toppingId;
	}
	public void setToppingId(Integer toppingId) {
		this.toppingId = toppingId;
	}
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Topping getTopping() {
		return topping;
	}
	public void setTopping(Topping topping) {
		this.topping = topping;
	}
	@Override
	public String toString() {
		return "OrderTopping [id=" + id + ", toppingId=" + toppingId + ", orderItemId=" + orderItemId + ", topping="
				+ topping + "]";
	}
}
