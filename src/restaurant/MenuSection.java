package restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuSection implements Serializable {
    private int menuSectionID;
    private String title;
    private List<MenuItem> menuItems;

    public MenuSection(int menuSectionID, String title) {
        this.menuSectionID = menuSectionID;
        this.title = title;
        this.menuItems = new ArrayList<>();
    }

    public int getMenuSectionID() {
        return menuSectionID;
    }

    public void setMenuSectionID(int menuSectionID) {
        this.menuSectionID = menuSectionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
}

