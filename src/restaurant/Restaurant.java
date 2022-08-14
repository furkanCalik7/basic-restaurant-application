package restaurant;

import restaurant.enums_interfaces.AccountStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Restaurant implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Employee> employees;
	private ArrayList<Table> tables;
	private ArrayList<Order> orders;
	private ArrayList<Reservation> reservations;
	private ArrayList<MenuItem> menuItems;

	private ArrayList<MenuSection> menuSections;
	private int currentEmployeeID;
	private int currentTableID;
	private int currentMenuItemID;
	private int currentReservationID;
	private int currentOrderID;
	private int currentMenuSectionID;


	public Restaurant(String name) {
		this.name = name;
		this.employees = new ArrayList<>();
		this.tables = new ArrayList<>();
		this.orders = new ArrayList<>();
		this.reservations = new ArrayList<>();
		this.menuItems = new ArrayList<>();
		this.menuSections = new ArrayList<>();
		this.currentEmployeeID = 1;

		Manager manager = new Manager("Ahmet Oktay", "ahmet_oktay@gmail.com",
				"03132431324", 0, new Date(), "İstanbul", new Account("admin",
				"admin", AccountStatus.AKTIF),this);
		this.employees.add(manager);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}

	public ArrayList<MenuSection> getMenuSections() {
		return menuSections;
	}

	public void setMenuSections(ArrayList<MenuSection> menuSections) {
		this.menuSections = menuSections;
	}

	public int getAvailableEmployeeID() {
		this.currentEmployeeID++;
		return this.currentEmployeeID - 1;
	}
	public int getAvailableCurrentTableID() {
		this.currentTableID ++;
		return this.currentTableID - 1;
	}
	public int getAvailableReservationID() {
		this.currentReservationID ++;
		return this.currentReservationID - 1;
	}

	public int getAvailableMenuItemID() {
		this.currentMenuItemID ++;
		return this.currentMenuItemID - 1;
	}

	public int getAvailableOrderID() {
		this.currentOrderID++;
		return this.currentOrderID - 1;
	}

	public int getAvailableMenuSectionID() {
		this.currentMenuSectionID++;
		return this.currentMenuSectionID - 1;
	}

	public String[] getTableLocationAsList() {
		String[] locations = new String[tables.size()];
		for(int i = 0; i < tables.size(); i++) {
			locations[i] = tables.get(i).getLocationIdentifier();
		}
		return locations;
	}

	public String[] getWaitersAsList() {
		ArrayList<Employee> waiters = new ArrayList<>();
		for(Employee employee: employees) {
			if(employee instanceof Waiter) {
				waiters.add(employee);
			}
		}
		String[] waitersArray = new String[waiters.size()];
		for(int i = 0; i < waiters.size(); i++) {
			waitersArray[i] = waiters.get(i).getName();
		}
		return waitersArray;
	}

	public Employee findEmployeeByName(String name) {
		for(Employee employee: employees) {
			if(name.equals(employee.getName())) {
				return employee;
			}
		}
		return null;
	}

	public String[] getChefAsList() {
		ArrayList<Employee> chefs = new ArrayList<>();
		for(Employee employee: employees) {
			if(employee instanceof Chef) {
				chefs.add(employee);
			}
		}
		String[] chefsArray = new String[chefs.size()];
		for(int i = 0; i < chefs.size(); i++) {
			chefsArray[i] = chefs.get(i).getName();
		}
		return chefsArray;
	}

	public String[] getMenuItemAsList() {
		String[] titles = new String[menuItems.size()];
		for(int i = 0; i < menuItems.size(); i++) {
			titles[i] = menuItems.get(i).getTitle();
		}
		return titles;
	}

	public String[] getMenuSectionAsList() {
		String[] titles = new String[menuSections.size()];
		for(int i = 0; i < menuSections.size(); i++) {
			titles[i] = menuSections.get(i).getTitle();
		}
		return titles;
	}

	public MenuSection getMenuSectionFromName(String name) {
		for(MenuSection menuSection: menuSections) {
			if(menuSection.getTitle().equals(name)) {
				return menuSection;
			}
		}
		return null;
	}

	public Table[] findTableFromLocationList(List<String> locations) {
		ArrayList<Table> temp = new ArrayList<>();
		for(String location: locations) {
			for(Table table: tables) {
				if(table.getLocationIdentifier().equals(location)) {
					temp.add(table);
				}
			}
		}
		Table[] tempArray = new Table[locations.size()];
		temp.toArray(tempArray);
		return tempArray;
	}

	public List<MenuItem> getMenuItemFromList(List<String> itemNames) {
		ArrayList<MenuItem> temp = new ArrayList<>();
		for(String meal: itemNames) {
			for(MenuItem menuItem: menuItems) {
				if(menuItem.getTitle().equals(meal)) {
					temp.add(menuItem);
				}
			}
		}
		return temp;
	}

	public Table findTableFromLocation(String location) {
		for(Table table: tables) {
			if(location.equals(table.getLocationIdentifier())) {
				return table;
			}
		}
		return null;
	}

	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public int getCurrentOrderID() {
		return currentOrderID;
	}

	public void setCurrentOrderID(int currentOrderID) {
		this.currentOrderID = currentOrderID;
	}
}
