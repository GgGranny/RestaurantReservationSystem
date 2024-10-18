package com.telusko.demo.service;

import java.io.IOException;
import java.util.Base64;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.demo.dto.NewUserDto;
import com.telusko.demo.model.RestaurantDetails;
import com.telusko.demo.model.User;
import com.telusko.demo.model.UserRole;
import com.telusko.demo.repository.RestaurantDetailsRepo;
import com.telusko.demo.repository.UserRepository;

@Service
public class UserSignUpService {
	
	

	 
	//save the user data
	public User createUser(NewUserDto userDto) {
		
		User u = new User();
		u.setUsername(userDto.getUsername());
		u.setEmail(userDto.getEmail());
		u.setPassword(userDto.getPassword());
		
		UserRole role = new UserRole();
        role.setUserRole(userDto.getRole());
        u.setUserRole(role);
        
        
        if(userDto.getRole().equalsIgnoreCase("admin")) {
        	RestaurantDetails restaurant = new RestaurantDetails();
            restaurant.setRestaurantName(userDto.getRestaurantName());
            restaurant.setPhoneNo(userDto.getPhone());
            restaurant.setLocation(userDto.getLocation());

            // Handle restaurant image upload
            if (userDto.getRestaurantImage() != null && !userDto.getRestaurantImage().isEmpty()) {
                try {
					restaurant.setRestaurantImage(saveRestaurantImage(userDto.getRestaurantImage()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            u.setRestaurant(restaurant);
            
            System.out.println(u.toString());
            return u;
        }
        
        
		return u;
	}
	

	 private String saveRestaurantImage(MultipartFile multipartFile) throws IOException {
		 
		 // Define where to save the image			 
		 byte[] imageByte = multipartFile.getBytes();
		 String image = Base64.getEncoder().encodeToString(imageByte);
		 return image;
	    }
	

}
