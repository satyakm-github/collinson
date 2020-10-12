package com.interview.vendingmachine.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineInventory<T> {
	private Map<T, Integer> localInv = new HashMap<T, Integer>();

	public int getQuantity(T item) {
		Integer value = localInv.get(item);
		if( value == null) {
			return 0;
		} else {
			return value;
		}
	}

	public void addItemToInv(T item) {
		int count = localInv.get(item);
		localInv.put(item, count + 1);
	}

	public void reduceInvBy1(T item) {
		if (hasItem(item)) {
			int count = localInv.get(item);
			localInv.put(item, count - 1);
		}
	}

	public boolean hasItem(T item) {
		return getQuantity(item) > 0;
	}

	public void clearInv() {
		localInv.clear();
	}

	public void put(T item, int quantity) {
		localInv.put(item, quantity);
	}
}
