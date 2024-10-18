package com.telusko.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo.model.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer>{

	List<Item> findAllByResDetails_Id(int id);
}
