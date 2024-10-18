package com.telusko.demo.dto;

public class CalenderDto {
	
	private int year;
	private int month;
	private int day;
	private int number;
	
	private int resId;
	private int tableId;
	
	
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	@Override
	public String toString() {
		return "CalenderDto [year=" + year + ", month=" + month + ", day=" + day + ", dayNumber=" + number + "]";
	}
	
}
