package com.telusko.demo.dto;

public class ReservationDto1 {
	private int year;
    private int month; // Add 1 since JS months are 0-indexed
    private int day;
    private int number;
    private int tableId;
    private String time;
    
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	@Override
	public String toString() {
		return "ReservationDto1 [year=" + year + ", month=" + month + ", day=" + day + ", number=" + number
				+ ", tableId=" + tableId + ", time=" + time + "]";
	}
	
    
}
