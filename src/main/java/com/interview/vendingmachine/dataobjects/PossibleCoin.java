package com.interview.vendingmachine.dataobjects;

public enum PossibleCoin {

	PENNY(1), NICKLE(5), DIME(10), QUARTER(25);

	private int denom;

	private PossibleCoin(int denomination) {
		this.denom = denomination;
	}

	public int getDenomination() {
		return denom;
	}
}
