package com.telusko.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public class ItemDto {
	private int id;
	private String name;
	private String catagory;
	private String description;
	private MultipartFile itemImage;
	
	private String itemPrice;
	
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
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
	public MultipartFile getItemImage() {
		return itemImage;
	}
	public void setItemImage(MultipartFile itemImage) {
		this.itemImage = itemImage;
	}
	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", name=" + name + ", catagory=" + catagory + ", description=" + description
				+ ", itemImage=" + itemImage + "]";
	}
	

}
