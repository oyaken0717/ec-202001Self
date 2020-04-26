package com.example.domain;

public class Sale {

	private Integer year;
	
	private Integer month;
	
	private Integer totalPrice;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Sale [year=" + year + ", month=" + month + ", totalPrice=" + totalPrice + "]";
	}
}
