package com.telusko.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.telusko.demo.model.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByUsernameAndPassword(String username, String password);

	boolean existsByUsername(String username);

	User findFirstByUsername(String username);
	
}
