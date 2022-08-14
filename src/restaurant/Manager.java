package restaurant;

import restaurant.enums_interfaces.AccountStatus;
import restaurant.enums_interfaces.EmployeeType;
import restaurant.enums_interfaces.TableStatus;

import java.util.Date;

public class Manager extends Employee {
    public Manager(String name, String email, String phone, int employeeID, Date dateJoined, String address, Account account, Restaurant restaurant) {
        super(name, email, phone, employeeID, dateJoined, address, account, restaurant);
    }

    public Employee addEmployee(EmployeeType type, String name, String address, String email, String phone) {
        Account account = new Account(email,  "0000", AccountStatus.AKTIF);

        Employee employee;

        if(type == EmployeeType.Chef) {
            employee = new Chef(name, email, phone, restaurant.getAvailableEmployeeID(), new Date(), address, account, restaurant);
        } else {
            employee = new Waiter(name, email, phone, restaurant.getAvailableEmployeeID(), new Date(), address, account, restaurant);
        }

        restaurant.getEmployees().add(employee);
        RestaurantStream.save(restaurant);
        return employee;
    };

    public void addTable(int capacity, String location) {
        this.restaurant.getTables().add(new Table(restaurant.getAvailableCurrentTableID(), TableStatus.MUSAIT, capacity, location));
    }

    public void addMenuItem(String name, double quantity, String menuSectionName) {
        MenuSection menuSection = restaurant.getMenuSectionFromName(menuSectionName);
        this.restaurant.getMenuItems().add(new MenuItem(restaurant.getAvailableMenuItemID(), name, quantity, menuSection));
    }

    public void addMenuSection(String name) {
        this.restaurant.getMenuSections().add(new MenuSection(restaurant.getAvailableMenuSectionID(), name));
    }

    public void updateEmployee(Employee employee, int selected) {
        restaurant.getEmployees().set(selected, employee);
    }
}
