package com.telusko.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo.model.Reservation;
import com.telusko.demo.model.Table;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Integer>{

	boolean existsByDateAndTableId_Id(String date, int table);

	Reservation findByDateAndTableId_Id(String date, int table);

	List<Reservation> findAllByDate(String date);

	List<Reservation> findAllByUserId_Id(int id);
	
}
