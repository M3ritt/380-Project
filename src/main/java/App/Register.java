package App;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Register {

	//private Item itemTemp = new Item();
	private ArrayList<Item> iList;
	private Inventory inventory;
	private double dailySalesTotal = 0;
	private double saleTotal, amountGiven, changeDue, taxRate, newPrice;
	private Scanner sc = new Scanner(System.in);
	public DecimalFormat df = new DecimalFormat("#.##");
	
	public Register(Inventory inventory) {
		this.inventory = inventory;
		this.taxRate = 1.08;
	}
	
	public void sale() {
		String enteredName;
		do {
			System.out.println("Please enter the item to remove, or press enter to leave: ");
			enteredName = sc.nextLine();
			if(inventory.checkItemByName(enteredName)) {
				System.out.println(enteredName +" was removed from the inventory.");
				dailySalesTotal += inventory.findItemByName(enteredName).getPrice();
				saleTotal += inventory.findItemByName(enteredName).getPrice();
				inventory.removeItemByName(enteredName);
			} else if(!(enteredName.equals(""))){
				System.out.println(enteredName + " is not a valid item.");
			}
		} while(!(enteredName.equals("")));
		saleTotal = saleTotal * taxRate;
		boolean qualified = false;
		while(qualified == false) {
			System.out.println("Amount Due: " + df.format(saleTotal) + ".");
			System.out.print("Amount taken: ");
			amountGiven = Double.parseDouble(sc.nextLine());
			if(amountGiven < saleTotal) {
				System.out.println("Sorry not enough money try again.");
			}
			else
				qualified = true;
		}
		changeDue = amountGiven - saleTotal;
		System.out.println("Customer Change: " + df.format(changeDue) + ".");
		//inventory.removeItemByName(itemTemp.getName());
		saleTotal = amountGiven = changeDue = 0.0;
	}
	
	public void addItem() {
		/*
		Item itemTemp = new Item();
		System.out.print("Please enter the item you would like to add: ");
		String temp = sc.nextLine();
		itemTemp.setName(temp);
		System.out.print("Please enter the price of the item: ");
		Double enteredDouble = Double.parseDouble(sc.nextLine());
		itemTemp.setPrice(enteredDouble);
		*/
		System.out.print("Please enter the item you would like to add: ");
		String enteredName = sc.nextLine();
		System.out.print("Please enter the price of the item: ");
		Double enteredDouble = Double.parseDouble(sc.nextLine());
		Item newItem = new Item(enteredName, enteredDouble);
		inventory.addItem(newItem);
	}
	
	public void changeItemPrice() {
		System.out.print("Please enter the name of the item you would like to change: ");
		String searchForName = sc.nextLine();
		Item itemTemp = inventory.findItemByName(searchForName);
		System.out.print("Please enter the new price for " + itemTemp.getName() + ": ");
		Double newPrice = Double.parseDouble(sc.nextLine());
		itemTemp.setPrice(newPrice);
	}
	
	public void checkInventory() {
		inventory.getItems();
	}
	
	public void itemReturn() {
		Item itemTemp = new Item();
		System.out.print("Please enter an item to return: ");
		itemTemp.setName(sc.nextLine());
		System.out.print("Please enter the price of what is being returned: ");
		Double priceSetter = Double.parseDouble(sc.nextLine());
		itemTemp.setPrice(priceSetter);
		inventory.addItem(itemTemp);
		changeDue = itemTemp.getPrice() * taxRate;
		System.out.println("Item returned total due back to customer is: " + changeDue + ".");
		dailySalesTotal -= itemTemp.getPrice();
		changeDue = 0.0;
	}
	
	public void dailyInventory() {
		//This will go through and get the inventory for each item at the end of the day
	}
	
	public void weeklyInventory() {
		//This will check the inventory at the end of the week
	}
	
	public void dailySalesTotals() {
		System.out.println(dailySalesTotal);
	}
	
	public void weeklySalesTotals() {
		//This will get the weekly sales totals
	}
	
	public void dailyReport() {
		//This will give daily report for sales, inventory, total tax, and user metrics
	}
	
	public void weeklyReport() {
		//This will give the weekly report for sales, inventory, total tax, and user metrics.
	}

	public double getDailySalesTotal() {
		return dailySalesTotal;
	}

	public void setDailySalesTotal(double dailySalesTotal) {
		this.dailySalesTotal = dailySalesTotal;
	}

	public double getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(double saleTotal) {
		this.saleTotal = saleTotal;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}
	
}