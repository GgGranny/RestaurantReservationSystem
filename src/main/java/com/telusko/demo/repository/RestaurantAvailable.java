package com.telusko.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo.model.RestaurantClosingAndOpening;

@Repository
public interface RestaurantAvailable extends JpaRepository<RestaurantClosingAndOpening, Integer>{

	boolean existsByResId_Id(int userId);

	List<RestaurantClosingAndOpening> findAllByResId_Id(int id);

	RestaurantClosingAndOpening findByDaysAndResId_Id(String day, int id);

	List<RestaurantClosingAndOpening> findByResId_Id(int resId);

}
