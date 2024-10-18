package com.telusko.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.telusko.demo.dto.CalenderDto;
import com.telusko.demo.dto.ReservationDto1;
import com.telusko.demo.dto.ReservationDto2;
import com.telusko.demo.dto.ReservedTableDto;
import com.telusko.demo.model.Item;
import com.telusko.demo.model.Reservation;
import com.telusko.demo.model.RestaurantClosingAndOpening;
import com.telusko.demo.model.RestaurantDetails;
import com.telusko.demo.model.Table;
import com.telusko.demo.model.User;
import com.telusko.demo.repository.ItemRepo;
import com.telusko.demo.repository.ReservationRepo;
import com.telusko.demo.repository.RestaurantAvailable;
import com.telusko.demo.repository.RestaurantDetailsRepo;
import com.telusko.demo.repository.TableRepository;
import com.telusko.demo.repository.UserRepository;
import com.telusko.demo.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/user")
@Controller
public class CustomerController {
	
	@Autowired
	private RestaurantDetailsRepo rsRepo;
	
	@Autowired 
	private UserRepository uRepo;
	
	@Autowired
	private TableRepository tableRepo;
	
	@Autowired
	private RestaurantAvailable rsAvailableRepo;
	
	@Autowired
	private ReservationRepo reserveRepo;
	
	@Autowired
	private ItemRepo iRepo;
	
	@GetMapping("/home")
	public String getUserPage(Model m) {
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
	
	@GetMapping("/restaurant/{id}")
	public String getRestaurant(@PathVariable("id") int id, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session.getAttribute("userUsername") == null) {	
			return "redirect:/login";
		}
		
		Optional<RestaurantDetails> restaurantOpt = rsRepo.findById(id);
		if(!restaurantOpt.isPresent()) {
			System.out.println("no such restaurnt");
		}
		RestaurantDetails restaurant = restaurantOpt.get();
		System.out.println(restaurant);
		
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(restaurant.getId());
		
		System.out.println(tables);
	    System.out.println("Received Restaurant ID: " + id);
	    
	    model.addAttribute("resId", id);
	    model.addAttribute("tables", tables);
	    return "reservationCustomer";  // Make sure this view name is correct
	}
	
	@PostMapping("/saveDate")
	@ResponseBody
	public List<String> getDateAndTime(@RequestBody CalenderDto calender, Model m) {
		System.out.println(calender.getNumber());
		System.out.println(calender.getResId());
		System.out.println(calender.getDay());
		
		AdminService adminService = new AdminService();
		
		
		
		RestaurantClosingAndOpening rsAvailable =
				rsAvailableRepo.findByDaysAndResId_Id(String.valueOf(calender.getNumber()), calender.getResId());
		
		
		List<String>timeIntervales = adminService.getDaysInterval(calender, rsAvailable);
		System.out.println(timeIntervales);
		
//		Gson gson = new Gson();
//		String intervlesJson = gson.toJson(timeIntervales);
//		m.addAttribute("intervles", timeIntervales);
//		m.addAttribute("msg", "hello");
		
		return timeIntervales;
	}
	
	

	
	@PostMapping("/saveData1") 
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDatas(@RequestBody CalenderDto calender, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		AdminService adminService  = new AdminService();
		RestaurantClosingAndOpening rsAvailable =
				rsAvailableRepo.findByDaysAndResId_Id(String.valueOf(calender.getNumber()), calender.getResId());
		List<String>timeIntervales = adminService.getDaysInterval(calender, rsAvailable);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		int selectedDay = calender.getDay();
		int selectedMonth = calender.getMonth();
		int selectedYear = calender.getYear();
		
		LocalDate localDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
		String date = localDate.format(formatter);
		
		System.out.println();
		
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(calender.getResId());
		if(tables.isEmpty()) {
			System.out.println("table is empty");
		}
		List<Reservation> reservation = reserveRepo.findAllByDate(date);
		if(reservation.isEmpty()) {
			System.out.println("empty reservation");
		}
		
		if(session.getAttribute("userId") != null) {
			System.out.println("User ID:  " + session.getAttribute("userId"));
		}else {
			System.out.println("id is null");
		}
		List<ReservedTableDto> reservedTableCheck = new ArrayList<>();
		int userId  = (Integer) session.getAttribute("userId");
		for(Table table: tables) {
			boolean isReserved = false;
			ReservedTableDto reservedTable = new ReservedTableDto();
			reservedTable.setTableId(table.getId());
			reservedTable.setNoOfPeople(table.getNoOfPeople());
			reservedTable.setTableNo(table.getTableNo());
			for(Reservation reserve: reservation) {
				System.out.println(reserve.getUserId().getId());
				if(table.getId() == reserve.getTableId().getId()) {
					reservedTable.setStatus( 
							 (session.getAttribute("userId") != null && userId == reserve.getUserId().getId()) 
				                ? "user-Reserved" 
				                : "reserved"
							);
					isReserved= true;
					break;
				}
			}
			
			if(!isReserved) {
				reservedTable.setStatus(null);
			}
			reservedTableCheck.add(reservedTable);
		}
		
		if (reservedTableCheck.isEmpty()) {
			System.out.println("is empty");
		}
		System.out.println(reservedTableCheck.toString());
		
		
        
        Map<String, Object> response = new HashMap<>();
        response.put("timeIntervals", timeIntervales);
        response.put("checkedTables", reservedTableCheck);
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/reservation2")
	public String getReservationForm(
			 @RequestParam("year") int year,
		     @RequestParam("month") int month,
		     @RequestParam("day") int day,
		     @RequestParam("time") String time,
		     @RequestParam("number") int number,
		     @RequestParam("tableId") int tableId,
		     Model model,
		     HttpServletRequest req
		     
			) {
		
		Optional<Table> tableOpt = tableRepo.findById(tableId);
		Table table = tableOpt.get();
		
		HttpSession session = req.getSession();
		Optional<User> u  = uRepo.findById((Integer) session.getAttribute("userId"));
		
		User user = u.get();
		
			
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		int selectedDay = day;
		int selectedMonth = month;
		int selectedYear = year;
		
		LocalDate localDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
		String date = localDate.format(formatter);
		
		
		
				
		model.addAttribute("date", date);
		model.addAttribute("time", time);
		model.addAttribute("tableNo", table.getTableNo());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("tableId", tableId);
		model.addAttribute("partySize", table.getNoOfPeople());
		return "reservationForm";
	}
	
	
	@PostMapping("/reservation1")
	@ResponseBody
	public String getReservation1(@RequestBody ReservationDto1 reservationDto1, HttpServletRequest req) {
		System.out.println(reservationDto1.toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		int day = reservationDto1.getDay();
		int month = reservationDto1.getMonth();
		int year = reservationDto1.getYear();
		
		LocalDate localDate = LocalDate.of(year, month, day);
		String date = localDate.format(formatter);
		
		
		
		Optional<Table> tableOpt = tableRepo.findById(reservationDto1.getTableId());
		Table table = tableOpt.get();
		
		HttpSession session = req.getSession();
		session.setAttribute("reservedTableID", table.getId());
		session.setAttribute("reservationDate", date);
		
		if(reserveRepo.existsByDateAndTableId_Id(date, reservationDto1.getTableId())) {
			Reservation reservation = reserveRepo.findByDateAndTableId_Id(date, reservationDto1.getTableId());
			reservation.setTime(reservationDto1.getTime());
			reserveRepo.save(reservation);
			
		}else {
			Reservation reservation = new Reservation();
			reservation.setDate(date);
			reservation.setTableId(table); // Set the entire Table object, not just the ID
			reservation.setTime(reservationDto1.getTime());
			reserveRepo.save(reservation);

			} 
		return"reservationCustomer";
	}
	
	@PostMapping("/saveReservation")
	public String saveReservation(@ModelAttribute ReservationDto2 reservationDto2 , HttpServletRequest req, Model m) {
		HttpSession session = req.getSession();
		Optional<User> u  = uRepo.findById((Integer) session.getAttribute("userId"));
		User user = u.get();
		Reservation reservation = reserveRepo.findByDateAndTableId_Id(reservationDto2.getDate(), reservationDto2.getTableId());
		reservation.setSpecialRequest(reservationDto2.getSpecialRequest());
		reservation.setStaus("CONFIRMED");
		reservation.setUserId(user);
		reservation.setPartySize(Integer.parseInt( reservationDto2.getPartySize()));
		
		reserveRepo.save(reservation);
		
		List<Reservation> revData = reserveRepo.findAllByUserId_Id(user.getId());
		List<Integer> ids = new ArrayList<>();
		
		Reservation latestReservation = null;
		if(!revData.isEmpty()) {
			for(Reservation r: revData) {
				ids.add(r.getId());
			}
			
			Collections.sort(ids);
			int highestId = ids.get(ids.size() - 1);
		    
		    // Find the reservation with the highest ID and store it in latestReservation
		    for (Reservation r : revData) {
		        if (r.getId() == highestId) {
		            latestReservation = r;
		            break;  // Exit the loop once the match is found
		        }
		    }
		}
		m.addAttribute("latestReservation", latestReservation);
		
		return "reservedDetails";
	}
	
	
	/*
	 * @PostMapping("/deleteStoredReservation")
	 * 
	 * @ResponseBody public ResponseEntity<String>
	 * deleteStoredReservation(HttpServletRequest req) { HttpSession session =
	 * req.getSession(); Integer userId = (Integer) session.getAttribute("userId");
	 * 
	 * if (userId != null) { // Fetch and delete the reservation for this user
	 * Optional<Reservation> reservationOpt =
	 * Optional.ofNullable(reserveRepo.findByDateAndTableId_Id((String)session.
	 * getAttribute("reservationDate"),
	 * (Integer)session.getAttribute("reservedTableID")));
	 * 
	 * if (reservationOpt.isPresent()) { reserveRepo.delete(reservationOpt.get());
	 * return ResponseEntity.ok("Reservation deleted successfully"); } else { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found"); }
	 * } return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in"); }
	 */
	
	
	@GetMapping("/discoverRestaurants")
	public String getRestaurants(Model m) {
		List<RestaurantDetails> restaurants = rsRepo.findAll();
		m.addAttribute("restaurnats", restaurants);
		return "restaurants";
	}
	
	@GetMapping("/myReservations")
	public String getReservationsUserMade(Model m, HttpServletRequest req) {
		HttpSession session = req.getSession();
		int userId = (Integer) session.getAttribute("userId");
		List<Reservation> reservations = reserveRepo.findAllByUserId_Id(userId);
		if(reservations.isEmpty()) {
			System.out.println(" is empty list");
		}
		 m.addAttribute("userReservations", reservations); 
		 return "myReservations"; 
		
	}
	@PostMapping("/cancel-reservation")
	public String calcelReservation(@RequestParam("reservationId") int id, Model m , HttpServletRequest req) {
		HttpSession session = req.getSession();
		int userId = (Integer) session.getAttribute("userId");
		List<Reservation> reservations = reserveRepo.findAllByUserId_Id(userId);
		if(reservations.isEmpty()) {
			System.out.println(" is empty list");
		}
		 m.addAttribute("userReservations", reservations); 
		if(id != 0) {
			reserveRepo.deleteById(id);
			m.addAttribute("message", "reservation canceled successfully");
		}else {
			System.out.println("id is null");
			m.addAttribute("message", "failed to cancel reservation");
		}
		
		return "myReservations";
		
	}
	
	@GetMapping("/reservation")
	public String getReservationPage(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session.getAttribute("userUsername") == null) {
			return "redirect:/login";
		}
		
		return "redirect:/user/discoverRestaurants"; 
	}
	
	
	
	
}
