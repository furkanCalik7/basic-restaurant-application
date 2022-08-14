package restaurant;

import java.util.Date;

public class Customer extends Person {
	private Date lastVisited;

	public Customer(String name, String email, String phone, Date lastVisited) {
		super(name, email, phone);
		this.lastVisited = lastVisited;
	}

	public Date getLastVisited() {
		return lastVisited;
	}

	public void setLastVisited(Date lastVisited) {
		this.lastVisited = lastVisited;
	}
}
