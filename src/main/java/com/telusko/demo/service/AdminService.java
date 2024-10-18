package com.telusko.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telusko.demo.dto.CalenderDto;
import com.telusko.demo.dto.ItemDto;
import com.telusko.demo.dto.TableDto;
import com.telusko.demo.model.Item;
import com.telusko.demo.model.RestaurantClosingAndOpening;
import com.telusko.demo.model.RestaurantDetails;
import com.telusko.demo.model.Table;

import net.coobird.thumbnailator.Thumbnails;


@Service
public class AdminService {
	
	public static String convertImage(MultipartFile file) {
		String stringImage = null ;
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			Thumbnails.of(file.getInputStream())
			.size(250, 250)
			.outputQuality(0.89)
			.toOutputStream(byteArray);
			byte[] image = byteArray.toByteArray();
			stringImage = Base64.getEncoder().encodeToString(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringImage;
	}

	


public RestaurantDetails getItemsData(ItemDto itemdto, RestaurantDetails rsDetails) {
    // Debugging: Check what data is being passed in itemdto
    System.out.println("ItemDto: " + itemdto);

    // Ensure that the DTO is not null and contains data
    if (itemdto == null || itemdto.getName() == null) {
        throw new RuntimeException("ItemDto or its fields are null");
    }
    
    // Create the new Item entity from DTO
    Item item = new Item();
    item.setName(itemdto.getName());
    item.setCatagory(itemdto.getCatagory());
    item.setDescription(itemdto.getDescription());
    item.setItemImage(convertImage(itemdto.getItemImage()));  // Ensure image conversion works
    item.setResDetails(rsDetails);  // Set the relation to RestaurantDetails
    item.setItemPrice(itemdto.getItemPrice());
    // Debugging: Check the item object before adding to the restaurant
    System.out.println("Item: " + item);
    
    // Add the item to the restaurant's item list
    rsDetails.getItem().add(item);

    // Debugging: Check the restaurant details after adding the item
    System.out.println("RestaurantDetails after adding item: " + rsDetails);
    
    return rsDetails;
}




public RestaurantDetails setTableData(TableDto tableDto, RestaurantDetails rsDetails) {
	Table table = new Table();
	table.setTableNo(tableDto.getTableNo());
	table.setTableLocation(tableDto.getTableLocation());
	table.setNoOfPeople(tableDto.getNoOfPeople());
	table.setResDetailsId(rsDetails);
	
	rsDetails.getTable().add(table);
	
	return rsDetails;
}




public List<String> generateTimeIntervals() {
    List<String> intervals = new ArrayList<>();
    LocalTime startTime = LocalTime.of(0, 0);
    System.out.println(startTime);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    // Loop through 48 intervals (30 minutes each) in 24 hours
    for (int i = 0; i < 48; i++) {
        String time = startTime.format(formatter);
        intervals.add(time);
        startTime = startTime.plusMinutes(30);
    }

    return intervals;
}




public List<String> getDaysInterval(CalenderDto calender, RestaurantClosingAndOpening rsAvailable) {

	List<String> timeIntervales = new ArrayList<>();
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
	LocalTime openTimeFormat = LocalTime.parse(rsAvailable.getOpenTime(), formatter);
	LocalTime closeTimeFormat = LocalTime.parse(rsAvailable.getCloseTime(),formatter);
	
	int openTime = openTimeFormat.getHour();
	int closeTime = closeTimeFormat.getHour();
	for(int i = 0; i<= 48 ; i++ ) {
		String time = openTimeFormat.format(formatter);
		timeIntervales.add(time);
		openTimeFormat = openTimeFormat.plusMinutes(30);
		if(time.equalsIgnoreCase(closeTimeFormat.format(formatter))) {
			break;
		}
	}
	return timeIntervales;
}

}
