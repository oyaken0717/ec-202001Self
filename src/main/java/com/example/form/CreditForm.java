package com.example.form;

public class CreditForm {
	
	/** カード番号 */
	private String card_number;
	/** カード有効期限(年) */
	private String card_exp_year;
	/** カード有効期限(月) */
	private String card_exp_month;
	/** カード名義人 */
	private String card_name;
	/** セキュリティコード */
	private String card_cvv;

//■ ------------------------------------------------------
	public Integer getIntCard_number() {
		return Integer.parseInt(card_number);
	}	
	public Integer getIntCard_exp_year() {
		return Integer.parseInt(card_exp_year);
	}	
	public Integer getIntCard_exp_month() {
		return Integer.parseInt(card_exp_month);
	}	
	public Integer getIntCard_cvv() {
		return Integer.parseInt(card_cvv);
	}	
//■ ------------------------------------------------------
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCard_exp_year() {
		return card_exp_year;
	}
	public void setCard_exp_year(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}
	public String getCard_exp_month() {
		return card_exp_month;
	}
	public void setCard_exp_month(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_cvv() {
		return card_cvv;
	}
	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}
	@Override
	public String toString() {
		return "CreditForm [card_number=" + card_number + ", card_exp_year=" + card_exp_year + ", card_exp_month="
				+ card_exp_month + ", card_name=" + card_name + ", card_cvv=" + card_cvv + "]";
	}
}
