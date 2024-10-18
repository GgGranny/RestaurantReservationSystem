package com.telusko.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public class NewUserDto {
	// Step 1 data
    private String username;
    private String email;
    private String password;

    // Step 2 data
    private String role;

    // Step 3 data (for admins)
    private String restaurantName;
    private String phone;
    private String location;
    private MultipartFile restaurantImage;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public MultipartFile getRestaurantImage() {
		return restaurantImage;
	}
	public void setRestaurantImage(MultipartFile restaurantImage) {
		this.restaurantImage = restaurantImage;
	}
    

}
