package com.telusko.demo.controller;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.telusko.demo.dto.DayHoursDto;
import com.telusko.demo.dto.ItemDto;
import com.telusko.demo.dto.TableDto;
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


@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired 
	private ItemRepo iRepo;
	
	@Autowired 
	private RestaurantDetailsRepo rsRepo;
	
	@Autowired
	private TableRepository tableRepo;
	
	@Autowired
	private RestaurantAvailable rsAvailableRepo;
	
	@Autowired
	private ReservationRepo reserveRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	@GetMapping("/dashboard")
	public String getDashboard(HttpServletRequest req, Model m){
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		Optional<User> userOpt = uRepo.findById((Integer) session.getAttribute("adminId"));
		User user = userOpt.get();
		
		Optional<RestaurantDetails> resDetailsOpt = rsRepo.findById(user.getRestaurant().getId());
		RestaurantDetails resDetails = resDetailsOpt.get();
		
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(resDetails.getId());
		List<Item> items = iRepo.findAllByResDetails_Id(resDetails.getId());
		
		List<Reservation> reservations = reserveRepo.findAll();
		
		List<Reservation> allReservations = new ArrayList<>();
		
		for(Reservation reservation: reservations) {
			for(Table table: tables) {
				if(reservation.getTableId().getId() == table.getId()) {
					allReservations.add(reservation);
					break;
				}
			}
		}
		
		m.addAttribute("allTables", tables);
		m.addAttribute("itemsSize", items.size());
		m.addAttribute("noOfReservations", allReservations.size());
		return "index";
	}
	
	@GetMapping("/brand") 
	public String getDashboardFromLogo() {
		return "redirect:dashboard";
	}
		
	@GetMapping("/viewMenu")
	public String getViewMenuPage(Model m, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		int id = (Integer) session.getAttribute("adminId");
		List<Item> items = iRepo.findAllByResDetails_Id(id);
		m.addAttribute("allItems", items);
		return "viewMenu";
	}
	@GetMapping("/addItem")
	public String getAddItemPage() {
		return "addItem";
	}
	
	@GetMapping("/editItem")
	public String getEditItemPage(Model m , HttpServletRequest req ) {
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		int id = (Integer) session.getAttribute("adminId");
		List<Item> items = iRepo.findAllByResDetails_Id(id);
		m.addAttribute("allItems", items);
		return "editItem";
	}
	
	@GetMapping("/viewTable")
	public String getViewTablePage(HttpServletRequest req, Model m) {
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		int id = (Integer) session.getAttribute("adminId");
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(id);
		System.out.println(tables);
		m.addAttribute("allTable", tables);
		return "viewTable";
	}
	
	@GetMapping("/addTable")
	public String getAddTablePage(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		return "addTable";
	}
	@GetMapping("/reservationRequest")
	public String getReservationRequest(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		return "reservationRequest";
	}
	
	@GetMapping("/availability")
	public String getAvailabilityPage(HttpServletRequest req, Model m) {
		HttpSession session = req.getSession();
		AdminService adminService = new AdminService();
		String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday","Thursday", "Friday", "Saturday"};
		int userId = (Integer) session.getAttribute("adminId");
		
		Optional<User> userOpt = uRepo.findById(userId);
		User user = userOpt.get();
		
		System.out.println(user.getEmail());
		
		Optional<RestaurantDetails> rsOpt = rsRepo.findById(user.getRestaurant().getId());
		RestaurantDetails rsDetails = rsOpt.get();
		System.out.println(rsDetails.getId());
		
		List<RestaurantClosingAndOpening> hours = new ArrayList<>();
		if(rsAvailableRepo.existsByResId_Id(userId)) {
			List<RestaurantClosingAndOpening> rsAvailableHrs = rsAvailableRepo.findAllByResId_Id(rsDetails.getId());
			m.addAttribute("intervals", adminService.generateTimeIntervals());
			m.addAttribute("allHours", rsAvailableHrs);
			return "availableTime";
		}else {
			for(int i = 0; i< 7; i++) {
				RestaurantClosingAndOpening rsAvailable = new RestaurantClosingAndOpening();
				rsAvailable.setDays(String.valueOf(i));
				rsAvailable.setWeeks(weeks[i]);
				rsAvailable.setOpenTime("00:00");
				rsAvailable.setCloseTime("12:00");
				rsAvailable.setResId(rsDetails);
				
				hours.add(rsAvailable);
			}
			for(RestaurantClosingAndOpening hour: hours) {
				rsDetails.setRestaurantAvailable(hours);
				rsRepo.save(rsDetails);
			}
			
		}
		
		
		
		m.addAttribute("intervals", adminService.generateTimeIntervals());
		m.addAttribute("allHours", hours);
		return "availableTime";
	}
	
	@PostMapping("/addItem")
	public String addingItem(@ModelAttribute ItemDto itemdto, HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    int id = (Integer) session.getAttribute("adminId");
	    
	    
	    
	    // Fetch RestaurantDetails based on the session userId
	    RestaurantDetails rsDetails = rsRepo.findById(id)
	        .orElseThrow(() -> new RuntimeException("Restaurant with ID " + id + " not found"));
	    
	    System.out.println(rsDetails);
	    
	    // Add the new item to the restaurant
	    AdminService adminService = new AdminService();
	    RestaurantDetails updatedDetails = adminService.getItemsData(itemdto, rsDetails);
	    
	    // Save the updated RestaurantDetails with the new item
	    rsRepo.save(updatedDetails);
	    
	    return "addItem";
	}
	
	@PostMapping("/updateMenu")
	public String updateItemData(@ModelAttribute ItemDto item, @RequestParam("itemId") int itemId, HttpServletRequest req, Model m) {
		System.out.println(itemId);
		System.out.println(item.getItemPrice());
		AdminService adService = new AdminService();
		Item i = iRepo.findById(itemId).orElseThrow();
		i.setName(item.getName());
		i.setCatagory(item.getCatagory());
		i.setDescription(item.getDescription());
		i.setItemPrice(item.getItemPrice());
		if(item.getItemImage() != null) {
			i.setItemImage(adService.convertImage(item.getItemImage()));
		}else {
			i.setItemImage(i.getItemImage());
		}
		iRepo.save(i);
		
		
		return "redirect:/admin/editItem";
	}
	
	@PostMapping("/deleteItem")
	public String deleteItem(@RequestParam("deleteId") int id,Model m) {
		iRepo.deleteById(id);
		m.addAttribute("deleteMsg", "deleted successfully");
		return "redirect:/admin/editItem";
	}

	@PostMapping("/addTable")
	public String addTable(@ModelAttribute TableDto tableDto, HttpServletRequest req, Model m) {
		if(tableDto == null || tableRepo.existsByTableNo(tableDto.getTableNo())) {
			m.addAttribute("tableErrorMsg", "unable to add Table or Table already taken");
			return "addTable";
		}
		HttpSession session = req.getSession();
		int id = (Integer) session.getAttribute("adminId");
		    
		    // Fetch RestaurantDetails based on the session userId
		RestaurantDetails rsDetails = rsRepo.findById(id)
		        .orElseThrow(() -> new RuntimeException("Restaurant with ID " + id + " not found"));
		    
		AdminService adminService = new AdminService();
		RestaurantDetails rsData = adminService.setTableData(tableDto, rsDetails);
		System.out.println(rsData);
		rsRepo.save(rsData);
		
		m.addAttribute("tableSuccessMsg", "Table added successfully");
		return "addTable";
	}
	
	@PostMapping("/updateTable")
	public String updateTable(
			@RequestParam("tableIdUpdate") int id, 
			@RequestParam("tableNoEdit") String no, 
			@RequestParam("tableLocationEdit") String location,
			@RequestParam("noOfPeopleEdit") String people, 
			Model m, 
			HttpServletRequest req) {
		System.out.println(id);
		Optional<Table> optionalTable = tableRepo.findById(id);
		Table table = optionalTable.orElse(null);
		table.setNoOfPeople(people);
		table.setTableLocation(location);
		table.setTableNo(no);
		
		tableRepo.save(table);
		
		HttpSession session = req.getSession();
		int userId = (Integer) session.getAttribute("adminId");
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(userId);
		System.out.println(tables);
		m.addAttribute("allTable", tables);
		
		m.addAttribute("tableMsg", "Table update Successfully");
		return "viewTable";
	}
	
	@PostMapping("/deleteTable")
	public String deleteTable(@RequestParam("tableId") int id , Model m, HttpServletRequest req) {
		tableRepo.deleteById(id);
		HttpSession session = req.getSession();
		int userId = (Integer) session.getAttribute("adminId");
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(userId);
		System.out.println(tables);
		m.addAttribute("allTable", tables);
		m.addAttribute("tableMsg", "Table Deleted Successfully");
		return "viewTable";
		
	}
	
	@PostMapping("/updateTime")
	public String saveOrUpdateTime(
			@RequestParam("Sunday_open") String sundayOpen,
			@RequestParam("Sunday_close") String sundayClose,
			@RequestParam("Monday_open") String mondayOpen,
			@RequestParam("Monday_close") String mondayClose,
			@RequestParam("Tuesday_open") String tuesdayOpen,
			@RequestParam("Tuesday_close") String tuesdayClose,
			@RequestParam("Wednesday_open") String wednesdayOpen,
			@RequestParam("Wednesday_close") String wednesdayClose,
			@RequestParam("Thursday_open") String thursdayOpen,
			@RequestParam("Thursday_close") String thursdayClose,
			@RequestParam("Friday_open") String fridayOpen,
			@RequestParam("Friday_close") String fridayClose,
			@RequestParam("Saturday_open") String saturdayOpen,
			@RequestParam("Saturday_close") String saturdayClose,
			HttpServletRequest req,
			Model m) {
		
		AdminService adminService = new AdminService();
		HttpSession session = req.getSession();
		int userId = (Integer) session.getAttribute("adminId");
		Optional<RestaurantDetails> rsOpt = rsRepo.findById(userId);
		RestaurantDetails rsDetails = rsOpt.get();
	
		
		List<DayHoursDto> hrsDto = new ArrayList<>();
		
		hrsDto.add(new DayHoursDto(0, sundayOpen, sundayClose));
		hrsDto.add(new DayHoursDto(1, mondayOpen, mondayClose));
		hrsDto.add(new DayHoursDto(2, tuesdayOpen, tuesdayClose));
		hrsDto.add(new DayHoursDto(3, wednesdayOpen, wednesdayClose));
		hrsDto.add(new DayHoursDto(4, thursdayOpen, thursdayClose));
		hrsDto.add(new DayHoursDto(5, fridayOpen, fridayClose));
		hrsDto.add(new DayHoursDto(6, saturdayOpen, saturdayClose));
		
		System.out.println(hrsDto.toString());
		for(DayHoursDto hr: hrsDto) {
			 RestaurantClosingAndOpening rsCloseAndOpenOpt = rsAvailableRepo.findByDaysAndResId_Id(String.valueOf(hr.getDay()), rsDetails.getId());
			 	
			 System.out.println(rsCloseAndOpenOpt);
		        // Update openTime and closeTime correctly
			 	rsCloseAndOpenOpt.setCloseTime(hr.getClosingHrs());
		        rsCloseAndOpenOpt.setOpenTime(hr.getOpeningHrs());

		        rsAvailableRepo.save(rsCloseAndOpenOpt);
		}
 		
		List<RestaurantClosingAndOpening> resAvailable = rsAvailableRepo.findAllByResId_Id(rsDetails.getId());
		System.out.println(resAvailable.toString());
		/* List<RestaurantClosingAndOpening> */
		
		m.addAttribute("intervals", adminService.generateTimeIntervals());
		m.addAttribute("allHours", resAvailable);
		return "availableTime";
	}	
	
	
	@GetMapping("/reservationsAll")
	public String getAllReservation(Model m, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.getAttribute("adminUsername") == null) {
			return "redirect:/login";
		}
		
		Optional<User> userOpt = uRepo.findById((Integer) session.getAttribute("adminId"));
		User user = userOpt.get();
		
		Optional<RestaurantDetails> resDetailsOpt = rsRepo.findById(user.getRestaurant().getId());
		RestaurantDetails resDetails = resDetailsOpt.get();
		
		List<Table> tables = tableRepo.findAllByResDetailsId_Id(resDetails.getId());
		
		List<Reservation> reservations = reserveRepo.findAll();
		
		List<Reservation> allReservations = new ArrayList<>();
		
		for(Reservation reservation: reservations) {
			for(Table table: tables) {
				if(reservation.getTableId().getId() == table.getId()) {
					allReservations.add(reservation);
					break;
				}
			}
		}
 		m.addAttribute("allReservations", allReservations);
		return"adminReservations";
	}
	

}
