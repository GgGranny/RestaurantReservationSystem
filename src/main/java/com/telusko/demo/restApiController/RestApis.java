package com.telusko.demo.restApiController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.demo.model.Item;
import com.telusko.demo.model.RestaurantDetails;
import com.telusko.demo.model.User;
import com.telusko.demo.repository.ItemRepo;
import com.telusko.demo.repository.RestaurantDetailsRepo;
import com.telusko.demo.repository.UserRepository;

@RestController
public class RestApis {
	
	@Autowired
	private RestaurantDetailsRepo rsRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private ItemRepo iRepo;
	
	@GetMapping("/api/getallrestaurant")
	public ResponseEntity<List<RestaurantDetails>> getAllRestaurant() {
	    List<RestaurantDetails> restaurants = rsRepo.findAll();
	    System.out.println("Number of restaurants: " + restaurants.size());
	    System.out.println("Restaurants data: " + restaurants); // Log the data
	    return ResponseEntity.ok(restaurants);
	}
	
	@GetMapping("api/restaurant/{id}")
	public RestaurantDetails getRestaurantById(@PathVariable int id) {
		return rsRepo.findById(id).get();
	}
	
	@GetMapping("/api/Restaurant")
	public List<RestaurantDetails> getRestaurants(@RequestParam(defaultValue = "10") int limit) {
	    return rsRepo.findAll(PageRequest.of(0, limit)).getContent();
	}

	
	
	@GetMapping("/api/user")
	public List<User> allUser () {
		return uRepo.findAll();
	}
	
	@GetMapping("/api/user/{id}")
	public Optional<User> getUserById(@PathVariable int id) {
		return uRepo.findById(id);
	}
	
	@GetMapping("/api/getRestaurantById/{id}")
	public Optional<RestaurantDetails> getRestaurant(@PathVariable int id) {
		return rsRepo.findById(id);
	}

	
	@GetMapping("/api/items")
	public List<Item> getAllItem() {
		List<Item> allItems = new ArrayList<>();
		
		return iRepo.findAll();
	}
}
