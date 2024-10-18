package com.telusko.demo.dto;

public class ReservedTableDto {
	private int tableId;
	private String status;
	private String tableNo;
	
	private String noOfPeople;
	
	public String getNoOfPeople() {
		return noOfPeople;
	}
	public void setNoOfPeople(String noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTableNo() {
		return tableNo;
	}
	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}
	@Override
	public String toString() {
		return "ReservedTableDto [tableId=" + tableId + ", status=" + status + ", tableNo=" + tableNo + ", noOfPeople="
				+ noOfPeople + "]";
	}
	
	
}	
