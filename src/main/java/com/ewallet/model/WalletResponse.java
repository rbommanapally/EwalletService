package com.ewallet.model;

public class WalletResponse {
	private int statusCode;
	private Object data;
	private String message;

	private static WalletResponse instance;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private WalletResponse() {
		// TODO Auto-generated constructor stub
	}

	static {
		instance = new WalletResponse();
	}

	/** Static 'instance' method */
	public static WalletResponse getInstance() {
		return instance;
	}
}
