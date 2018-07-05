package com.ewallet.service;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ewallet.constants.EwalletConstants;
import com.ewallet.model.BlockChainResponse;
import com.ewallet.model.EwalletUserBean;
import com.ewallet.model.TransactionDetails;
import com.ewallet.model.WalletBalanceResponse;
import com.ewallet.model.WalletCurrency;
import com.ewallet.model.WalletResponse;
import com.ewallet.repository.TransactionRepository;

@Service
public class EwalletService {

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * generate a new unique address in block chain and grant permissions
	 * 
	 * @return
	 */
	public BlockChainResponse getNewAddressFromBC() {

		String createAddress = "{ \"method\": \"getnewaddress\", \"params\": [], \"id\": 1, \"chain_name\": \""
				+ EwalletConstants.BLOCKCHAIN_NAME + "\" }";

		// create a unique address in block chain
		BlockChainResponse response = execute(createAddress);

		System.out.println("Response after register ::" + response.getResult());

		// grant permission for the address created
		String grantPermission = "{\"method\":\"grant\",\"params\":[\"" + response.getResult()
				+ "\",\"connect,send,receive\"],\"id\":1,\"chain_name\":\"" + EwalletConstants.BLOCKCHAIN_NAME + "\"}";

		execute(grantPermission);

		return response;
	}

	public WalletBalanceResponse getBalance(String walletAddress) {

		List<WalletCurrency> resultSet = new ArrayList<WalletCurrency>();

		String getBalance = "{\"method\":\"getaddressbalances\",\"params\":[\"" + walletAddress
				+ "\"],\"id\":1,\"chain_name\":\"" + EwalletConstants.BLOCKCHAIN_NAME + "\"}";

		// create a unique address in block chain
		WalletBalanceResponse response = executeBalance(getBalance);

		for (WalletCurrency currencyType : response.getResult()) {
			if (StringUtils.equals(currencyType.getName(), EwalletConstants.WALLET_CURRENCY)) {
				resultSet.add(currencyType);
			}
		}
		// set a new result set with the new list of wallet currency
		response.setResult(resultSet);

		return response;
	}

	public WalletResponse transferWalletToWallet(EwalletUserBean userInput) {

		if (StringUtils.equals(userInput.getTransactionType(), EwalletConstants.PaymentType.LOAD.toString())) {
			userInput.setRemarks("Admin Account");
			userInput.setTransactionType("LOAD");
			userInput.setWalletAddress(EwalletConstants.TREASURY_ACCOUNT);
			userInput.setUserName("Admin");

		} else if (StringUtils.equals(userInput.getTransactionType(), EwalletConstants.PaymentType.PAY.toString())) {
			userInput.setRemarks("Wallet to Wallet");
			userInput.setTransactionType("PAY");
			userInput.setWalletAddress(userInput.getWalletAddress());
		}

		String transferCoins = "{\"method\":\"sendassetfrom\",\"params\":[\"" + userInput.getWalletAddress() + "\",\""
				+ userInput.getReceiverWalletAddress() + "\",\"RAK-AED\"," + userInput.getAmountToTransfer()
				+ ",0,\"payment\",\"" + userInput.getUniqueTransactionId() + "\"],\"id\":1,\"chain_name\":\""
				+ EwalletConstants.BLOCKCHAIN_NAME + "\"}";

		// create a unique address in block chain
		BlockChainResponse response = execute(transferCoins);

		// save every transaction after load, transfer
		WalletResponse objResponse = saveTransaction(userInput, response);

		return objResponse;
	}

	public WalletResponse saveTransaction(EwalletUserBean userInput, BlockChainResponse transactionResponse) {

		TransactionDetails transactionData = TransactionDetails.getInstance();

		WalletResponse response = WalletResponse.getInstance();

		transactionData = prepareTransactionData(userInput, transactionData, userInput.getWalletAddress(),
				transactionResponse);

		System.out.println("Saving transaction details ::" + transactionData.toString());

		transactionData = transactionRepository.save(transactionData);
		response.setMessage("Transaction Successfully saved");
		response.setData(transactionData);

		return response;
	}

	private TransactionDetails prepareTransactionData(EwalletUserBean userInput, TransactionDetails details,
			String walletAddress, BlockChainResponse transactionResponse) {
		details.setFromAddress(userInput.getUserName());
		details.setToAddress(userInput.getReceiverUserName());
		details.setTransactionType(userInput.getTransactionType());
		details.setTransactionUniqueID(userInput.getUniqueTransactionId());
		details.setTransactionAmount(userInput.getAmountToTransfer());
		if (StringUtils.isNotBlank(transactionResponse.getResult())) {
			details.setRequestStatus("COMPLETED");
		}
		details.setRemarks(userInput.getRemarks());
		details.setModifiedDateTime(userInput.getModifiedDateTime());
		details.setWalletTransactionID(transactionResponse.getResult());
		return details;
	}

	public WalletResponse retreiveTransaction(EwalletUserBean user) {

		WalletResponse response = WalletResponse.getInstance();

		TransactionDetails details = TransactionDetails.getInstance();

		// details.setToAddress(user.getReceiverUserName());
		if (StringUtils.isNotBlank(user.getUniqueTransactionId())) {
			System.out.println("calling the transaction---");
			details = transactionRepository.findByTransactionUniqueID(user.getUniqueTransactionId());
		}
		System.out.println("details---"+ReflectionToStringBuilder.toString(details));
		if (details != null) {
			System.out.println("detailsResponse checkTransaction:: " + details.toString());
		}
		response.setData(details);
		response.setMessage("TransactionFound");
		return response;
	}

	public WalletBalanceResponse executeBalance(String payload) {

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", createHeaders(EwalletConstants.BCNAME, EwalletConstants.BCPW));

		HttpEntity<?> request = new HttpEntity<>(payload, headers);
		String url = EwalletConstants.BCURL;

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the String message converter
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		WalletBalanceResponse response = restTemplate
				.exchange(url, HttpMethod.POST, request, WalletBalanceResponse.class).getBody();

		return response;
	}

	// public BCResponse execute(String payload) {
	//
	// HttpHeaders headers = new HttpHeaders();
	//
	// // headers.setContentType(MediaType.APPLICATION_JSON);
	// headers.add("Content-Type", "application/json");
	// headers.add("Authorization", createHeaders(EwalletConstants.BCNAME,
	// EwalletConstants.BCPW));
	//
	// HttpEntity<?> request = new HttpEntity<>(payload, headers);
	// String url = EwalletConstants.BCURL;
	//
	// // Create a new RestTemplate instance
	// RestTemplate restTemplate = new RestTemplate();
	//
	// System.out.println("url---" + url);
	// System.out.println("request---" + request);
	//
	// BCResponse response = restTemplate.exchange(url, HttpMethod.POST,
	// request, BCResponse.class).getBody();
	//
	// return response;
	// }

	/**
	 * Execute the http url with block chain
	 * 
	 * @param payload
	 * @return
	 */
	public BlockChainResponse execute(String payload) {

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json");
		headers.add("Authorization", createHeaders(EwalletConstants.BCNAME, EwalletConstants.BCPW));

		HttpEntity<?> request = new HttpEntity<>(payload, headers);
		String url = EwalletConstants.BCURL;

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		System.out.println("url---" + url);
		System.out.println("request---" + request);

		BlockChainResponse response = restTemplate.exchange(url, HttpMethod.POST, request, BlockChainResponse.class)
				.getBody();

		System.out.println("Response from block chain----" + ReflectionToStringBuilder.toString(response));

		return response;
	}

	private String createHeaders(String username, String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		System.out.println("header---" + authHeader);
		return authHeader;
	}

}
