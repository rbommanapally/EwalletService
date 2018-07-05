package com.ewallet.constants;

public class EwalletConstants {

	public static final String BCNAME = "multichainrpc";
	public static final String BCPW = "BAPPCSXNPKP4cpjfCjwr2n37yxrWpjpDMKVW9nFopryx";
	public static final String BCURL = "http://rak-bank.centralindia.cloudapp.azure.com:15590";
	public static final String TREASURY_ACCOUNT = "15XN1qqoYYKoz2yDyiwTYsDwKXCc5NUGD4Ky5m";
	public static final String BLOCKCHAIN_NAME = "demochain";
	public static final String WALLET_CURRENCY = "RAK-AED";
	

	public enum Permission {
		CONNECT, SEND, RECEIVE;
	}

	public enum PaymentType{
		LOAD,PAY;
	} 
}
