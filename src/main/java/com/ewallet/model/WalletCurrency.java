package com.ewallet.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class WalletCurrency {
	private String name;
	private String assetref;
	private String qty;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssetref() {
		return assetref;
	}
	public void setAssetref(String assetref) {
		this.assetref = assetref;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ReflectionToStringBuilder.toString(this);
	}
}
