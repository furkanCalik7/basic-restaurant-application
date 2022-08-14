package restaurant;

import restaurant.enums_interfaces.ReservationStatus;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
	private int reservationID;
	private Date timeOfReservation;
	private int peopleCount;
	private ReservationStatus status;
	private String notes;
	private Customer customer;
	private Table[] tables;

	public Reservation(int reservationID, Date timeOfReservation, int peopleCount, ReservationStatus status, String notes, Customer customer, Table[] tables) {
		this.reservationID = reservationID;
		this.timeOfReservation = timeOfReservation;
		this.peopleCount = peopleCount;
		this.status = status;
		this.notes = notes;
		this.customer = customer;
		this.tables = tables;
	}

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public Date getTimeOfReservation() {
		return timeOfReservation;
	}

	public void setTimeOfReservation(Date timeOfReservation) {
		this.timeOfReservation = timeOfReservation;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Table[] getTables() {
		return tables;
	}

	public void setTables(Table[] tables) {
		this.tables = tables;
	}

	public String getTablesAsString() {
		String tableIDs = "";
		for(Table table: tables) {
			tableIDs += table.getLocationIdentifier() + ",";
		}
		if(tableIDs.length() != 0) {
			return tableIDs.substring(0, tableIDs.length() - 1);
		} else {
			return tableIDs;
		}
	}
}
