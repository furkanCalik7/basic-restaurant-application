package restaurant;

import java.io.Serializable;
import java.util.Date;
import restaurant.enums_interfaces.OrderStatus;

public class Order implements Serializable {
	private int OrderID;
	private OrderStatus status;
	private Date creationTime;
	private Meal meal;
	private Table table;
	private Waiter waiter;

	public Order(int orderID, OrderStatus status, Date creationTime, Meal meal, Table table, Waiter waiter) {
		OrderID = orderID;
		this.status = status;
		this.creationTime = creationTime;
		this.meal = meal;
		this.table = table;
		this.waiter = waiter;
	}

	public void addMeal(Meal meal) {

	};

	public void removeMeal(Meal meal) {

	};

	public int getOrderID() {
		return OrderID;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public String getMealsAsString() {
		String mealNames = "";
		for(MenuItem menuItem: meal.getMenuItems()) {
			mealNames += menuItem.getTitle() + ", ";
		}
		if(mealNames.length() != 0) {
			return mealNames.substring(0, mealNames.length() - 2);
		} else {
			return mealNames;
		}
	}
}
