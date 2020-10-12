package com.interview.vendingmachine.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.interview.vendingmachine.dataobjects.PossibleCoin;
import com.interview.vendingmachine.dataobjects.PossibleOperations;
import com.interview.vendingmachine.dataobjects.VendingItem;
import com.interview.vendingmachine.dataobjects.VendingMachineInventory;
import com.interview.vendingmachine.dataobjects.VendingMachineResponse;

import static com.interview.vendingmachine.constants.VendingMachineConstants.INITIAL_ITEMS_CAPACITY;
import static com.interview.vendingmachine.constants.VendingMachineConstants.INITIAL_CURRENCY_CAPACITY;

public class VendingMachine {
	private VendingMachineInventory<PossibleCoin> cashInventory = new VendingMachineInventory<PossibleCoin>();
	private VendingMachineInventory<VendingItem> itemInventory = new VendingMachineInventory<VendingItem>();
	private long totalSales;
	private VendingItem currentItem;
	private long currentBalance;

	public VendingMachine() {
		startupVendingMachine();
	}

	public boolean isEmptyBalance() {
		return currentBalance == 0;
	}

	public void showMenu() {
		System.out.println("Pleae Select one of the following options: ");
		for (VendingItem vendItem : VendingItem.values()) {
			System.out.println(vendItem.getChoice() + "] " + vendItem.getName() + " ($" + vendItem.getPrice() + ")");
		}

		System.out.println();
		for (PossibleOperations op : PossibleOperations.values()) {
			System.out.println(op.getChoice() + "] " + op.getOpration());
		}
		System.out.println();
	}

	private void startupVendingMachine() {
		for (PossibleCoin c : PossibleCoin.values()) {
			cashInventory.put(c, INITIAL_CURRENCY_CAPACITY);
		}

		for (VendingItem i : VendingItem.values()) {
			itemInventory.put(i, INITIAL_ITEMS_CAPACITY);
		}
	}

	public boolean validIndex(int selection) {
		return getItem(selection) != null;
	}

	public long selectItemAndGetPrice(VendingItem item) {
		if (itemInventory.hasItem(item)) {
			currentItem = item;
			return currentItem.getPrice();
		}
		throw new RuntimeException("Sold Out, Please buy another item");
	}

	public VendingItem getItem(int selection) {
		for (VendingItem vendItem : VendingItem.values()) {
			if (vendItem.getChoice() == selection) {
				return vendItem;
			}
		}
		return null;
	}

	public PossibleCoin getCoin(long givenMoney) {
		for (PossibleCoin c : PossibleCoin.values()) {
			if (c.getDenomination() == givenMoney) {
				return c;
			}
		}
		return null;
	}

	public void insertCoin(PossibleCoin coin) {
		currentBalance = currentBalance + coin.getDenomination();
		cashInventory.addItemToInv(coin);
	}

	public VendingMachineResponse<VendingItem, List<PossibleCoin>> collectItemAndChange() throws Exception {
		VendingItem item = collectItem();
		totalSales = totalSales + currentItem.getPrice();

		List<PossibleCoin> change = collectChange();

		return new VendingMachineResponse<VendingItem, List<PossibleCoin>>(item, change);
	}

	private VendingItem collectItem() throws Exception {
		if (isFullPaid()) {
			if (hasSufficientChange()) {
				itemInventory.reduceInvBy1(currentItem);
				return currentItem;
			}
			throw new Exception("Not Sufficient change in Inventory");
		}
		long remainingBalance = currentItem.getPrice() - currentBalance;
		throw new Exception("Price not full paid, remaining : " + remainingBalance);
	}

	private List<PossibleCoin> collectChange() throws Exception {
		long changeAmount = currentBalance - currentItem.getPrice();
		List<PossibleCoin> change = getChange(changeAmount);
		updateCashInventory(change);
		currentBalance = 0;
		currentItem = null;
		return change;
	}

	public List<PossibleCoin> refund() throws Exception {
		List<PossibleCoin> refund = getChange(currentBalance);
		updateCashInventory(refund);
		currentBalance = 0;
		currentItem = null;
		return refund;
	}

	private boolean isFullPaid() {
		if (currentBalance >= currentItem.getPrice()) {
			return true;
		}
		return false;
	}

	private List<PossibleCoin> getChange(long amount) throws Exception {
		List<PossibleCoin> changes = Collections.EMPTY_LIST;

		if (amount > 0) {
			changes = new ArrayList<PossibleCoin>();
			long balance = amount;
			while (balance > 0) {
				if (balance >= PossibleCoin.QUARTER.getDenomination() && cashInventory.hasItem(PossibleCoin.QUARTER)) {
					changes.add(PossibleCoin.QUARTER);
					balance = balance - PossibleCoin.QUARTER.getDenomination();
					continue;
				} else if (balance >= PossibleCoin.DIME.getDenomination() && cashInventory.hasItem(PossibleCoin.DIME)) {
					changes.add(PossibleCoin.DIME);
					balance = balance - PossibleCoin.DIME.getDenomination();
					continue;
				} else if (balance >= PossibleCoin.NICKLE.getDenomination()
						&& cashInventory.hasItem(PossibleCoin.NICKLE)) {
					changes.add(PossibleCoin.NICKLE);
					balance = balance - PossibleCoin.NICKLE.getDenomination();
					continue;
				} else if (balance >= PossibleCoin.PENNY.getDenomination()
						&& cashInventory.hasItem(PossibleCoin.PENNY)) {
					changes.add(PossibleCoin.PENNY);
					balance = balance - PossibleCoin.PENNY.getDenomination();
					continue;
				} else {
					throw new Exception("NotSufficientChange, Please try another product");
				}
			}
		}

		return changes;
	}

	public void reset() {
		cashInventory.clearInv();
		itemInventory.clearInv();
		totalSales = 0;
		currentItem = null;
		currentBalance = 0;
	}

	public void printStats() {
		System.out.println("Total Sales : " + totalSales);
	}

	private boolean hasSufficientChange() {
		return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
	}

	private boolean hasSufficientChangeForAmount(long amount) {
		boolean hasChange = true;
		try {
			getChange(amount);
		} catch (Exception nsce) {
			return hasChange = false;
		}

		return hasChange;
	}

	private void updateCashInventory(List<PossibleCoin> change) {
		for (PossibleCoin c : change) {
			cashInventory.reduceInvBy1(c);
		}
	}

	public long getTotalSales() {
		return totalSales;
	}

}