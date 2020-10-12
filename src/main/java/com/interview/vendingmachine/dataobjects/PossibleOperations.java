package com.interview.vendingmachine.dataobjects;

public enum PossibleOperations {
	CANCEL(101, "Cancel My Request"),  RESET(102, "Reset Vending Machine"), EXIT(103, "Exit Vending Machine");

	private int choice;
	private String operation;
	
	private PossibleOperations(int choice, String operation) {
		this.choice = choice;
		this.operation = operation;
	}

	public int getChoice() {
		return choice;
	}
	public String getOpration() {
		return operation;
	}
}