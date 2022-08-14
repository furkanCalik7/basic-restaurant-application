package restaurant;

import java.io.Serializable;
import java.util.List;

public class Meal implements Serializable {
	  private List<MenuItem> menuItems;
	  private double quantity;

	public Meal(List<MenuItem> menuItems) {
		this.menuItems = menuItems;

		int total = 0;
		for(MenuItem menuItem: menuItems) {
			total += menuItem.getPrice();
		}
		this.quantity = total;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public double getQuantity() {
		return quantity;
	}
}
