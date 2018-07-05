package com.ewallet.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.model.TransactionDetails;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDetails, Serializable> {

	TransactionDetails findByTransactionUniqueID(String uniqueId);
}
