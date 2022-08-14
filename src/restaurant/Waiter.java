package restaurant;

import java.util.Date;

public class Waiter extends Employee {
    public Waiter(String name, String email, String phone, int employeeID, Date dateJoined, String address, Account account, Restaurant restaurant) {
        super(name, email, phone, employeeID, dateJoined, address, account, restaurant);
    }
}
