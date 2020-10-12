package com.interview.vendingmachine.dataobjects;

public class VendingMachineResponse<E1, E2> {
	private E1 vendingItem;
	private E2 listOfCoins;

	public VendingMachineResponse(E1 first, E2 second) {
		this.vendingItem = first;
		this.listOfCoins = second;
	}

	public E1 getVendingItem() {
		return vendingItem;
	}

	public E2 getCoinsChange() {
		return listOfCoins;
	}
}
