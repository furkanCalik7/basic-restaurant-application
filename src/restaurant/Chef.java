package restaurant;

import java.util.Date;

public class Chef extends Employee {
    public Chef(String name, String email, String phone, int employeeID, Date dateJoined, String address, Account account, Restaurant restaurant) {
        super(name, email, phone, employeeID, dateJoined, address, account, restaurant);
    }
    //public boolean takeOrder();
}
