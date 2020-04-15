package com.example.domain;

public class CreditResult {

	/** APIからの情報1 */
	private String status;
	/** APIからの情報2*/
	private String message;
	/** APIからの情報3 */
	private String error_code;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	@Override
	public String toString() {
		return "CreditResult [status=" + status + ", message=" + message + ", error_code=" + error_code + "]";
	}
}
