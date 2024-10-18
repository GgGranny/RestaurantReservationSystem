package com.telusko.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class RestaurantDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
//	@NotNull
	private String 	restaurantName;
	
//	@NotNull
	private String phoneNo;
	
//	@NotNull
	private String location;
	
//	@NotNull
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String restaurantImage;
	
	@OneToMany(mappedBy = "resDetails", cascade =  CascadeType.ALL)
	private List<Item> item;
	
	@OneToMany(mappedBy="resDetailsId", cascade = CascadeType.ALL)
	private List<Table> table;
	
	
	 @OneToMany(mappedBy="resId", cascade = CascadeType.ALL)
	 private List<RestaurantClosingAndOpening> restaurantAvailable;
	 
	
	
	public List<RestaurantClosingAndOpening> getRestaurantAvailable() {
		return restaurantAvailable;
	}
	public void setRestaurantAvailable(List<RestaurantClosingAndOpening> restaurantAvailable) {
		this.restaurantAvailable = restaurantAvailable;
	}
	public List<Table> getTable() {
		return table;
	}
	public void setTable(List<Table> table) {
		this.table = table;
	}
	public List<Item> getItem() {
		return item;
	}
	public void setItem(List<Item> item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return "RestaurantDetails [id=" + id + ", restaurantName=" + restaurantName + ", phoneNo=" + phoneNo
				+ ", location=" + location + ", restaurantImage=" + restaurantImage + "]";
	}
	public String getRestaurantImage() {
		return restaurantImage;
	}
	public void setRestaurantImage(String restaurantImage) {
		this.restaurantImage = restaurantImage;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
