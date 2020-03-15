package com.example.form;

public class RegisterUserForm {

	/** 名前 */
	private String name;
	/** メール */
	private String email;
	/** パスワード */
	private String password;
	/** 確認パスワード */
	private String confirmationPassword;
	/** 郵便番号 */
	private String zipcode;
	/** 住所 */
	private String address;
	/** 電話番号 */
	private String telephone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmationPassword() {
		return confirmationPassword;
	}
	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", password=" + password
				+ ", confirmationPassword=" + confirmationPassword + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + "]";
	}	
}
