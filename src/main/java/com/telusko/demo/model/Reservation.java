package com.telusko.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String date;
	private String time;
	
	@ManyToOne
    @JoinColumn(name = "table_id") // foreign key column
    private Table tableId;

    // Many reservations can be made by the same user
    @ManyToOne
    @JoinColumn(name = "user_id") // foreign key column
    private User userId;
	
	private String specialRequest;
	
	private String staus;
	
	private int partySize;
	
	
	public int getPartySize() {
		return partySize;
	}
	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Table getTableId() {
		return tableId;
	}
	public void setTableId(Table tableId) {
		this.tableId = tableId;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public String getSpecialRequest() {
		return specialRequest;
	}
	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}
	public String getStaus() {
		return staus;
	}
	public void setStaus(String staus) {
		this.staus = staus;
	}
	
	

}
