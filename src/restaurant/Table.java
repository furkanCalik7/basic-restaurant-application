package restaurant;

import restaurant.enums_interfaces.TableStatus;

import java.io.Serializable;

public class Table implements Serializable {
	private int tableID;
	private TableStatus status;
	private int maxCapacity;
	private String locationIdentifier;

	public Table(int tableID, TableStatus status, int maxCapacity, String locationIdentifier) {
		this.tableID = tableID;
		this.status = status;
		this.maxCapacity = maxCapacity;
		this.locationIdentifier = locationIdentifier;
	}

	public int getTableID() {
		return tableID;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getLocationIdentifier() {
		return locationIdentifier;
	}

	public void setLocationIdentifier(String locationIdentifier) {
		this.locationIdentifier = locationIdentifier;
	}
}
