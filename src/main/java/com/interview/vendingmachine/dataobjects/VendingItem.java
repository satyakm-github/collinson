package com.interview.vendingmachine.dataobjects;

public enum VendingItem {
	COKE(1, "Coke", 25), PEPSI(2, "Pepsi", 35), SODA(3, "Soda", 45);

	private int choice;
	private String itemName;
	private int itemPrice;

	private VendingItem(int choice, String name, int price) {
		this.choice = choice;
		this.itemName = name;
		this.itemPrice = price;
	}

	public int getChoice() {
		return choice;
	}
	
	public String getName() {
		return itemName;
	}

	public long getPrice() {
		return itemPrice;
	}
}