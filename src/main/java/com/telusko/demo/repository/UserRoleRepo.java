package com.telusko.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo.model.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

	UserRole findById(UserRole userRole);

}
