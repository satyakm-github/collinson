package com.interview.vendingmachine;

import java.util.List;
import java.util.Scanner;

import com.interview.vendingmachine.dataobjects.PossibleCoin;
import com.interview.vendingmachine.dataobjects.PossibleOperations;
import com.interview.vendingmachine.dataobjects.VendingItem;
import com.interview.vendingmachine.dataobjects.VendingMachineResponse;
import com.interview.vendingmachine.implementation.VendingMachine;

public class TestMain {
	public static void main(String[] args) throws Exception {
		VendingMachine vm = new VendingMachine();
		System.out.println("Vending Machine successfully started... welcome to VendingMach 1.0");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();

		int choice = 0;
		Scanner scanner = new Scanner(System.in);

		while (choice != PossibleOperations.EXIT.getChoice()) {
			vm.showMenu();
			System.out.print("Please select an Item by pressing index of the item :: ");
			String sel = scanner.next();
			int selection = -1;
			try {
				selection = Integer.parseInt(sel);// scanner.nextInt();
			} catch (Exception e) {
				selection = -1;
			}

			if (selection == PossibleOperations.CANCEL.getChoice()) {
				if (vm.isEmptyBalance()) {
					printBannerMsg(">>>> You have not given any amount so far to refund any!!!");
				}
			} else if (selection == PossibleOperations.EXIT.getChoice()) {
				printBannerMsg(">>>> have a good day!!!!");
				break;

			} else if (selection == PossibleOperations.RESET.getChoice()) {
				vm.printStats();
				vm.reset();
				printBannerMsg(">>>> You have reset the vending machine. Please reinitialise it to make it work");
			} else if (vm.validIndex(selection)) {
				// item selected.. ask for money OR cancel operation.
				long price = vm.selectItemAndGetPrice(vm.getItem(selection));
				boolean isCancelled = false;

				System.out.println("Please enter coins for : " + price);
				long givenMoney = scanner.nextInt();

				// if given money is 'cancel' then dont proceed.
				if (givenMoney == PossibleOperations.CANCEL.getChoice()) {
					isCancelled = true;
				} else {
					long totalMoney = givenMoney;
					PossibleCoin coin = vm.getCoin(givenMoney);

					while (coin == null) {
						System.out.println("Given coin in a invalid one !!! try again");
						givenMoney = scanner.nextInt();
						if (givenMoney == PossibleOperations.CANCEL.getChoice()) {
							isCancelled = true;
							break;
						}
						coin = vm.getCoin(givenMoney);
					}

					if (!isCancelled) {
						// you got some initial valid currency
						totalMoney = givenMoney;
						vm.insertCoin(coin);

						while (totalMoney < price && !isCancelled) {
							if (totalMoney >= price) {
								break;
							} else {
								System.out.println(
										"Please enter some more coins for : " + (price - totalMoney) + " (or type : "
												+ PossibleOperations.CANCEL.getChoice() + " for cancelling this)");
								givenMoney = scanner.nextInt();
								if (givenMoney == PossibleOperations.CANCEL.getChoice()) {
									isCancelled = true;
									break;
								}
							}

							coin = vm.getCoin(givenMoney);
							while (coin == null) {
								System.out.println("this is not a valid currency!!! try again");
								givenMoney = scanner.nextInt();
								if (givenMoney == PossibleOperations.CANCEL.getChoice()) {
									isCancelled = true;
									break;
								} else {
									coin = vm.getCoin(givenMoney);
								}
							}

							vm.insertCoin(coin);
							totalMoney += givenMoney;
						}
					}
				}

				if (isCancelled) {
					if (!vm.isEmptyBalance()) {
						List<PossibleCoin> refundCoins = vm.refund();
						if (refundCoins.size() > 0) {
							System.out.println("\tPlease take your change (since you cancelled the operation): ");
							for (PossibleCoin possCoin : refundCoins) {
								System.out.println("\t\t" + possCoin + "(" + possCoin.getDenomination() + ")");
							}
						}
					} else {
						printBannerMsg(">>>> You have not given any amount so far to refund any!!!");
					}
				} else {
					// means you got sufficient money OR more .
					VendingMachineResponse<VendingItem, List<PossibleCoin>> ret = vm.collectItemAndChange();
					System.out.println("\t-----------");
					System.out.println("\tEnjoy your: " + ret.getVendingItem());
					if (ret.getCoinsChange().size() > 0) {
						System.out.println("\tPlease take your change (and enjoy your drink) : ");
						for (PossibleCoin possCoin : ret.getCoinsChange()) {
							System.out.println("\t\t" + possCoin + "(" + possCoin.getDenomination() + ")");
						}
					}
					System.out.println("\t-----------");
				}
				System.out.println();
			} else {
				printBannerMsg(">>>> given selection is invalid !!!");
			}
		}
	}

	public static void printBannerMsg(String msg) {
		System.out.println();
		System.out.println(msg);
		System.out.println();
	}
}
