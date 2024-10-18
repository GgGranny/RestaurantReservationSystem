package com.telusko.demo.controller;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.telusko.demo.dto.NewUserDto;
import com.telusko.demo.model.User;
import com.telusko.demo.repository.UserRepository;
import com.telusko.demo.service.UserSignUpService;

@Controller
public class SignUpController {
	
	@Autowired
	private UserRepository uRepo;
	
	
	@GetMapping("/signup")
	public String getMethodName(Model m) {
		m.addAttribute(new User());
		return "signup";
	}
	
	@PostMapping("/signUpProcess")
	public String getSignUpProcess(@ModelAttribute NewUserDto u, Model m) {
//		if(result.hasErrors() || !u.getPassword().equalsIgnoreCase(u.getConfirmPassword())) {
//			System.out.println(result);
////			result.rejectValue("confirmPassword", "error.user", "Passwords do not match.");
//			return "signup";
//		}
//		uRepo.save(u);
//		m.addAttribute("userData",u);
		
		if(uRepo.existsByUsername(u.getUsername())) {
			m.addAttribute("usernameError", "usernam already taken");
			return "signup";
		}
		UserSignUpService uService = new UserSignUpService();
		User user  = uService.createUser(u);
		System.out.println(user);
		System.out.println(uRepo.save(user));
		System.out.println(user + "created seuccess fully");
		m.addAttribute("susseccMsg", "registered successfully ");
		return "login";
		

	
		
	}
	
	@GetMapping("/admin")
	public String adminChoosen() {
//		UserRole uRole = new UserRole();
//		uRole.setUserRole("ADMIN");
////		u.setUserRole(uRole);
//		roleRepo.save(uRole);
		
		return "restaurantInfoFill";
	}	
			
	
}
