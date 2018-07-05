package com.ewallet.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "eWalletTransactions")
public class TransactionDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "TransactionUniqueID")
	private String transactionUniqueID;

	@Column(name = "FromAddress")
	private String fromAddress;

	@Column(name = "ToAddress")
	private String toAddress;

	@Column(name = "TransactionAmount")
	private String transactionAmount;

	@Column(name = "TransactionType")
	private String transactionType;

	@Column(name = "WalletTransactionID")
	private String walletTransactionID;

	@Column(name = "RequestStatus")
	private String requestStatus;

	@Column(name = "Remarks")
	private String remarks;

	@Column(name = "CreatedDateTime")
	private Date createdDateTime;

	@Column(name = "ModifiedDateTime")
	private Date modifiedDateTime;

	private static TransactionDetails instance;

	public String getWalletTransactionID() {
		return walletTransactionID;
	}

	public void setWalletTransactionID(String walletTransactionID) {
		this.walletTransactionID = walletTransactionID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ReflectionToStringBuilder.toString(this);
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	private TransactionDetails() {
		// TODO Auto-generated constructor stub
	}

	static {
		instance = new TransactionDetails();
	}

	/** Static 'instance' method */
	public static TransactionDetails getInstance() {
		return instance;
	}

	public String getTransactionUniqueID() {
		return transactionUniqueID;
	}

	public void setTransactionUniqueID(String transactionUniqueID) {
		this.transactionUniqueID = transactionUniqueID;
	}

}
