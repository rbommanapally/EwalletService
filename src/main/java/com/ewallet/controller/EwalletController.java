package com.ewallet.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ewallet.model.EwalletUserBean;
import com.ewallet.model.BlockChainResponse;
import com.ewallet.model.WalletResponse;
import com.ewallet.model.EwalletUserEntity;
import com.ewallet.model.WalletBalanceResponse;
import com.ewallet.repository.UserRepository;
import com.ewallet.service.EwalletService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/users")
@CrossOrigin
public class EwalletController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EwalletService ewalletService;

	// Get the user details of logged in userId
	@RequestMapping(value = "/getCustomerData", method = RequestMethod.POST)
	public WalletResponse getUser(@RequestBody EwalletUserBean userInput, HttpServletResponse http) {

		WalletResponse response = WalletResponse.getInstance();

		System.out.println("Data--" + ReflectionToStringBuilder.toString(userInput));

		// check if the user exists ,if exists then retrieve the user details
		EwalletUserEntity exisingUser = userRepository.findByUserId(userInput.getUserId());

		System.out.println("User from Database--" + ReflectionToStringBuilder.toString(exisingUser));

		if (exisingUser == null || StringUtils.isBlank(exisingUser.getWalletAddress())) {
			response.setMessage("User Not registered");
			response.setStatusCode(http.getStatus());
			response.setData(exisingUser);
		} else {

			System.out.println("User Exists, getting the balance");
			// get the balance
			WalletBalanceResponse balanceResponse = ewalletService.getBalance(exisingUser.getWalletAddress());

			System.out.println("wallet reposne--" + balanceResponse.toString());

			BeanUtils.copyProperties(exisingUser, userInput);
			if (!balanceResponse.getResult().isEmpty()) {

				userInput.setWalletBalance(balanceResponse.getResult().get(0).getQty());
			}

			System.out.println("wallet reposne--" + userInput.toString());

			response.setMessage("User Already Exists");
			response.setStatusCode(http.getStatus());
			response.setData(userInput);
		}
		return response;

	}

	// Register user by creating a wallet address
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public WalletResponse registerUser(@RequestBody EwalletUserBean walletUser, HttpServletResponse http) {

		WalletResponse response = WalletResponse.getInstance();
		BlockChainResponse bcResponse = new BlockChainResponse();
		EwalletUserEntity user = new EwalletUserEntity();

		System.out.println("User input :: " + ReflectionToStringBuilder.toString(walletUser));

		if (walletUser != null && !StringUtils.isBlank(walletUser.getUserId())) {

			System.out.println("Going to register  new customer");

			// get the unique address from the block chain
			bcResponse = ewalletService.getNewAddressFromBC();

			System.out.println(
					"Block chain address for the user " + walletUser.getUserId() + " is  :::" + bcResponse.getResult());

			// map the user with the unique address of the block chain
			walletUser.setWalletAddress(bcResponse.getResult());

			BeanUtils.copyProperties(walletUser, user, "receiverWalletAddress");

			System.out.println("Saving in DB :: " + ReflectionToStringBuilder.toString(user));

			// if not exists then insert the row (register)
			EwalletUserEntity registeredUser = userRepository.save(user);

			System.out.println("After saving in DB :: " + ReflectionToStringBuilder.toString(registeredUser));

			response.setMessage("User Registered Successfully");
			response.setStatusCode(http.getStatus());
			response.setData(registeredUser);

		} else {

			response.setMessage("User Data Null");
			response.setStatusCode(http.getStatus());
			response.setData(walletUser);
		}
		return response;
	}

	@RequestMapping(value = "/transferWalletToWallet", method = RequestMethod.POST)
	public WalletResponse loadBalance(@RequestBody EwalletUserBean userInput, HttpServletResponse http) {

		WalletResponse objResponse = WalletResponse.getInstance();

		System.out.println("User Input from mobile :: " + ReflectionToStringBuilder.toString(userInput));

		objResponse = ewalletService.transferWalletToWallet(userInput);

		System.out.println("Wallet Transfer Response :: " + ReflectionToStringBuilder.toString(objResponse));

		return objResponse;

	}

	@RequestMapping(value = "/getBalance", method = RequestMethod.POST)
	public WalletResponse getBalance(@RequestBody final EwalletUserBean userInput, HttpServletResponse http) {

		WalletResponse response = WalletResponse.getInstance();

		System.out.println("Data--" + userInput.getWalletAddress());

		// get the balance
		WalletBalanceResponse balanceResponse = ewalletService.getBalance(userInput.getWalletAddress());

		System.out.println("wallet reposne--" + balanceResponse.toString());

		userInput.setWalletBalance(balanceResponse.getResult().get(0).getQty());

		System.out.println("wallet reposne--" + userInput.toString());

		response.setMessage("User Balance");
		response.setStatusCode(http.getStatus());
		response.setData(userInput);

		return response;
	}

	/**
	 * Find and update a user
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/checkTransaction", method = RequestMethod.POST)
	public WalletResponse updateUser(@RequestBody final EwalletUserBean userInput, HttpServletResponse http) {
		WalletResponse response = WalletResponse.getInstance();

		System.out.println("User Input from mobile checkTransaction:: " + ReflectionToStringBuilder.toString(userInput));
		
		response = ewalletService.retreiveTransaction(userInput);

		return response;
	}

	// /**
	// * Delete a user
	// *
	// * @param userId
	// * @return
	// */
	// @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	// public ListResponse deleteUser(@PathVariable("id") String userId,
	// HttpServletResponse http) {
	// ListResponse response = new ListResponse();
	//
	// User user = userRepository.findByUserId(userId);
	//
	// if (userRepository.exists(user.getId())) {
	// userRepository.delete(user.getId());
	// response.setStatusCode(http.getStatus());
	// response.setMessage("Successfully Deleted");
	// } else {
	// response.setStatusCode(404);
	// response.setMessage("Record not found");
	// }
	// List<User> users = userRepository.findAll();
	// response.setData(users);
	// return response;
	// }
}
