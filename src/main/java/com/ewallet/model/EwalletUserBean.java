package com.ewallet.model;

import java.sql.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class EwalletUserBean {

	private String userId;
	private String userName;
	private String walletAddress;
	private String requestStatus;
	private String activeFlag;
	private String remarks;
	private Date createdDateTime;
	private Date modifiedDateTime;

	private String walletBalance;
	private String amountToTransfer;
	private String uniqueTransactionId;
	private String transactionType;
	private String receiverUserName;
	private String receiverWalletAddress;

	
	
	
	public String getAmountToTransfer() {
		return amountToTransfer;
	}

	public void setAmountToTransfer(String amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getUniqueTransactionId() {
		return uniqueTransactionId;
	}

	public String getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(String walletBalance) {
		this.walletBalance = walletBalance;
	}

	public void setUniqueTransactionId(String uniqueTransactionId) {
		this.uniqueTransactionId = uniqueTransactionId;
	}

	public String getReceiverWalletAddress() {
		return receiverWalletAddress;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}

	public void setReceiverWalletAddress(String receiverWalletAddress) {
		this.receiverWalletAddress = receiverWalletAddress;
	}

	public EwalletUserBean() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ReflectionToStringBuilder.toString(this);
	}

}
