package com.telusko.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String catagory;
	private String description;
	
	private String itemPrice;
	
	
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String itemImage;
	
	@ManyToOne
	@JoinColumn(name = "resId")
	private RestaurantDetails resDetails;
	
	
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", catagory=" + catagory + ", description=" + description
				+ ", itemImage=" + itemImage + ", resDetails=" + resDetails + "]";
	}
	public RestaurantDetails getResDetails() {
		return resDetails;
	}
	public void setResDetails(RestaurantDetails resDetails) {
		this.resDetails = resDetails;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	
	
}
