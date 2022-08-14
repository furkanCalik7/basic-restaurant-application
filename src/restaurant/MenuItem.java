package restaurant;

import java.io.Serializable;

public class MenuItem implements Serializable {
	private int menuItemID;
	private String title;
	private double price;

	private MenuSection menuSection;

	public MenuItem(int menuItemID, String title, double price, MenuSection menuSection) {
		this.menuItemID = menuItemID;
		this.title = title;
		this.price = price;
		this.menuSection = menuSection;
	}

	public int getMenuItemID() {
		return menuItemID;
	}

	public void setMenuItemID(int menuItemID) {
		this.menuItemID = menuItemID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public MenuSection getMenuSection() {
		return menuSection;
	}

	public void setMenuSection(MenuSection menuSection) {
		this.menuSection = menuSection;
	}
}
