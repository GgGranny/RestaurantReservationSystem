package com.telusko.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo.model.Item;
import com.telusko.demo.model.Table;


@Repository
public interface TableRepository extends JpaRepository<Table, Integer>{

	boolean existsByTableNo(String tableNo);

	List<Table> findAllByResDetailsId_Id(int id);


}
