package com.telusko.demo.dto;

public class TableDto {
	private int id;
	private String tableNo;
	private String tableLocation;
	private String noOfPeople;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableNo() {
		return tableNo;
	}
	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}
	public String getTableLocation() {
		return tableLocation;
	}
	public void setTableLocation(String tableLocation) {
		this.tableLocation = tableLocation;
	}
	public String getNoOfPeople() {
		return noOfPeople;
	}
	public void setNoOfPeople(String noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	@Override
	public String toString() {
		return "TableDto [id=" + id + ", tableNo=" + tableNo + ", tableLocation=" + tableLocation + ", noOfPeople="
				+ noOfPeople + "]";
	}
	
}
