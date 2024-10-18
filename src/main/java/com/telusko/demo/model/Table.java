package com.telusko.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@jakarta.persistence.Table(name = "restaurantTable")
public class Table {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tableNo;
	private String tableLocation;
	private String noOfPeople;
	
	@ManyToOne
	@JoinColumn(name="resId")
	private RestaurantDetails resDetailsId;
	
	@OneToMany(mappedBy = "tableId", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
	
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

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

	public RestaurantDetails getResDetailsId() {
		return resDetailsId;
	}

	public void setResDetailsId(RestaurantDetails resDetailsId) {
		this.resDetailsId = resDetailsId;
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", tableNo=" + tableNo + ", tableLocation=" + tableLocation + ", noOfPeople="
				+ noOfPeople + ", resDetails=" + resDetailsId + "]";
	}
	
	
}
