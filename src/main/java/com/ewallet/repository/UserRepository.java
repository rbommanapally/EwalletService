package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.model.EwalletUserEntity;

@Repository
public interface UserRepository extends JpaRepository<EwalletUserEntity, Integer> {

	// @Modifying
	// @Transactional
	// @Query("update User user set user.userName=:userName,
	// user.userId=:userId, user.hashCode=:hashCode where user.id=:id")
	// void updateUser(@Param("userName") String name, @Param("userId") String
	// userId,
	// @Param("hashCode") String hashCode, @Param("id") Integer id);
    
    
    EwalletUserEntity findByUserId(String userId);
}
