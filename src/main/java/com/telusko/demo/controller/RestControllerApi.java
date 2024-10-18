package com.telusko.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.telusko.demo.model.RestaurantDetails;
import com.telusko.demo.repository.RestaurantDetailsRepo;

@Controller
@RequestMapping("/api")
public class RestControllerApi {
	
	   @Autowired
	    private RestaurantDetailsRepo rdRepo;

	    @GetMapping("/restaurants")
	    @ResponseBody
	    public ResponseEntity<List<RestaurantDetails>> getAllRestaurants() {
	        List<RestaurantDetails> response = rdRepo.findAll();
	        return ResponseEntity.ok(response);  // Automatically returns as JSON
	    }
}

