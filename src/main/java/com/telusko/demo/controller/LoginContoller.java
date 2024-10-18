package com.telusko.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.telusko.demo.dto.NewUserDto;
import com.telusko.demo.model.Item;
import com.telusko.demo.model.RestaurantDetails;
import com.telusko.demo.model.User;
import com.telusko.demo.model.UserRole;
import com.telusko.demo.repository.ItemRepo;
import com.telusko.demo.repository.RestaurantDetailsRepo;
import com.telusko.demo.repository.UserRepository;
import com.telusko.demo.repository.UserRoleRepo;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class LoginContoller {
	
	@Autowired
	private RestaurantDetailsRepo resRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired 
	private UserRoleRepo roleRepo;
	
	@Autowired
	private ItemRepo iRepo;
	

	  @GetMapping("/") 
	  public String getLandingPage(Model m) { 
		  List<Item> i = new ArrayList<>(); List<Item> items = iRepo.findAll();
	  
	  if(items.isEmpty()) { System.out.println("empty"); }
	  System.out.println(items); 
	  int j = 1; 
	  for(Item item: items) {
		  if(j>= 8)break;
	  
	  i.add(item); 
	  j++; } 
		  m.addAttribute("items", i); 
	  return "customerLandingPage";
	  }
	 
	
	
	
	@GetMapping("/login") 
	public String getLoginPage(){
		return"login";
	}
	
	@PostMapping("/login")
	public String getLogin(@ModelAttribute User u, HttpServletRequest req, Model m) {
		
	    if(uRepo.existsByUsernameAndPassword(u.getUsername(), u.getPassword())) {
	        User userData = uRepo.findFirstByUsername(u.getUsername());
	        if(userData == null) {
	            return "login"; // User not found, return login
	        }

	        // Get or create the session
	        
	        
	        // Fetch the UserRole based on its ID
	        UserRole userRole = roleRepo.findById(userData.getUserRole().getId()).orElse(null);
	        if(userRole == null) {
	            return "login"; // Role not found, return login
	        }
	        
	       

		        // Handle session attributes based on the role
		        if(userRole.getUserRole().equalsIgnoreCase("admin")) {
		            // Set admin-specific session attributes
		        	HttpSession s = req.getSession(false);
		        	if(s != null) {
		        		s.invalidate();
		        	}
		        	
		        	HttpSession session = req.getSession();
		            session.setAttribute("adminUsername", u.getUsername());
		            session.setAttribute("adminId", userData.getId());
		            session.setAttribute("isAdmin", true);  // Set a flag for admin role
		            session.setAttribute("role", "admin");
	
		            return "redirect:/admin/dashboard"; // Redirect to admin dashboard
		        } else if(userRole.getUserRole().equalsIgnoreCase("user")) {
		        	HttpSession s = req.getSession(false);
		        	if(s != null) {
		        		s.invalidate();
		        	}
		            // Set user-specific session attributes
		        	HttpSession session = req.getSession();
		            session.setAttribute("userUsername", u.getUsername());
		            session.setAttribute("userId", userData.getId());
		            session.setAttribute("isAdmin", false);  // Set a flag for user role
		            session.setAttribute("role", "user");
	
		            return "redirect:/user/home"; // Redirect to user dashboard
		        }
	        }
	    
	    
	    return "login"; // Invalid credentials, return login
	}

	
	@GetMapping("/logout")
	public String userLogout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/restaurantDetails/{id}") 
	public String getRestaurantDetails(@PathVariable("id") int id, Model m){
		Optional<RestaurantDetails> rsDetailsOpt = resRepo.findById(id);
		RestaurantDetails rsDetails = rsDetailsOpt.get();
		
		List<Item> items = iRepo.findAllByResDetails_Id(id);
		
		if(items.isEmpty()) {
			System.out.println("items is empty");
		}else {
			System.out.println(items.toString());
		}
		
		m.addAttribute("rsDetails", rsDetails); 
		m.addAttribute("allItems", items); 
		return "restaurantDetailsAndMenu";
	}
	
}
