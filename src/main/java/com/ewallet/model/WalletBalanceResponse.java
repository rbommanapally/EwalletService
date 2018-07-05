package com.ewallet.model;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class WalletBalanceResponse {
	private List<WalletCurrency> result;
	private String error;
	private String id;

	public List<WalletCurrency> getResult() {
		return result;
	}

	public void setResult(List<WalletCurrency> result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ReflectionToStringBuilder.toString(this);
	}

}
