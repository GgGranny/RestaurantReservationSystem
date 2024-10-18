package com.telusko.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo.model.RestaurantDetails;

@Repository
public interface RestaurantDetailsRepo extends JpaRepository<RestaurantDetails, Integer>{



}
