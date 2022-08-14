package restaurant;

import restaurant.enums_interfaces.OrderStatus;
import restaurant.enums_interfaces.ReservationStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public abstract class Employee extends Person {
	private int employeeID;
	private Date dateJoined;
	private String address;
	private Account account;
	protected Restaurant restaurant;

	public Employee(String name, String email, String phone, int employeeID, Date dateJoined, String address, Account account, Restaurant restaurant) {
		super(name, email, phone);
		this.employeeID = employeeID;
		this.dateJoined = dateJoined;
		this.address = address;
		this.account = account;
		this.restaurant = restaurant;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void addReservation(int peopleCount, LocalDateTime time, String customerName, String customerPhone, String note, Table[] tables) {
		Instant instant = time.toInstant(ZoneOffset.UTC);
		Date date = Date.from(instant);
		Reservation reservation = new Reservation(restaurant.getAvailableReservationID(), date, peopleCount, ReservationStatus.ISTEK_LISTESINDE, note, new Customer(customerName, "", customerPhone, new Date()), tables);
		restaurant.getReservations().add(reservation);
		RestaurantStream.save(restaurant);
	}

	public void addOrder(Meal meal, Table table, Waiter waiter) {
		restaurant.getOrders().add(new Order(restaurant.getAvailableOrderID(), OrderStatus.ALINDI, new Date(), meal, table, waiter));
	}

	public boolean changePassword(String oldPW, String newPW) {
		if(oldPW.equals(account.getPassword())) {
			account.setPassword(newPW);
			return true;
		}
		return false;
	}

	public void updateTable(Table table, int index) {
		restaurant.getTables().set(index, table);
	}
	public void updateOrder(Order order, int index) {
		restaurant.getOrders().set(index, order);
	}
	public void updateReservation(Reservation reservation, int index) {
		restaurant.getReservations().set(index, reservation);
	}
	public void updateMenuItem(MenuItem menuItem, int index, String sectionName) {
		MenuSection menuSection = restaurant.getMenuSectionFromName(sectionName);
		menuItem.setMenuSection(menuSection);
		restaurant.getMenuItems().set(index, menuItem);
	}
}
