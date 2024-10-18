package com.telusko.demo.dto;

public class DayHoursDto {
	private int day;
	private String openingHrs;
	private String closingHrs;
	
	public DayHoursDto(int day, String openingHrs, String closingHrs) {
		this.day = day;
		this.openingHrs = openingHrs;
		this.closingHrs = closingHrs;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getOpeningHrs() {
		return openingHrs;
	}
	public void setOpeningHrs(String openingHrs) {
		this.openingHrs = openingHrs;
	}
	public String getClosingHrs() {
		return closingHrs;
	}
	public void setClosingHrs(String closingHrs) {
		this.closingHrs = closingHrs;
	}
	@Override
	public String toString() {
		return "DayHoursDto [day=" + day + ", openingHrs=" + openingHrs + ", closingHrs=" + closingHrs + "]";
	}
	

}
