package com.telusko.demo.model;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	  @Column(nullable = false, unique = true)
	   private String username;
	
//	@NotNull
//	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	private String email;
//	@NotNull
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message ="must contain 1 digit 1 uppsercase and 1 special symbol")
	private String password;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_role")
	private UserRole userRole;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_owned")
	private RestaurantDetails restaurant;
	
	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<Reservation> reservationId;
	
	
	
	public RestaurantDetails getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(RestaurantDetails restaurant) {
		this.restaurant = restaurant;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", userRole=" + userRole + ", restaurant=" + restaurant + "]";
	}
	
	
}
