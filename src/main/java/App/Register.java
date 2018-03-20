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
			System.out.print("Please enter the item to remove, or press enter to leave: ");
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

	public void addItem(String enteredName, Double enteredDouble) {
		Item newItem = new Item(enteredName, enteredDouble);
		inventory.addItem(newItem);
	}

	public void changeItemPrice(String enteredName, Double enteredDouble) {
		Item itemTemp = inventory.findItemByName(enteredName);
		itemTemp.setPrice(enteredDouble);
	}

	public void checkInventory() {
		inventory.getItems();
	}

	public void itemReturn(String eName) {
		Item itemTemp = new Item(eName, 0.0d);
		itemTemp = inventory.findItemByName(itemTemp.getName());
		if (itemTemp != null) {
//			System.out.print("Please enter the price of what is being returned: ");
//			itemTemp.setPrice(sc.nextDouble());	
			inventory.addItem(itemTemp);
			changeDue = itemTemp.getPrice() * taxRate;
			changeDue = Math.round(changeDue * 100);
			changeDue = changeDue/100;
			System.out.println("Item returned total due back to customer is: " + changeDue + ".");
			dailySalesTotal -= itemTemp.getPrice();
			changeDue = 0.0;
		}else System.out.println("That item was not in the inventory.");
	}
	
	public double checkPrice(String eName) {
		//This method will take in an item and return the price.
		Item i = inventory.findItemByName(eName);
		return i.getPrice();
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