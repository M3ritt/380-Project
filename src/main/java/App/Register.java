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
	private String temp;
	public DecimalFormat df = new DecimalFormat("#.##");
	boolean isValid;
	
	public Register(Inventory inventory) {
		this.inventory = inventory;
		this.taxRate = 1.08;
	}
	
	public void sale() {
		Item itemTemp = new Item();
		System.out.print("Please enter an item to sell: ");
		String enteredName = sc.nextLine();
		itemTemp.setName(enteredName);
		itemTemp = inventory.findItemByName(itemTemp.getName());
		//if(dailySalesTotal != 0.0d)
			dailySalesTotal += itemTemp.getPrice();
		/*else
			dailySalesTotal = itemTemp.getPrice();
		*/
		
		saleTotal += itemTemp.getPrice();
		saleTotal = saleTotal * taxRate;
		//System.out.print("Would you like to add another item?");
		//temp = sc.nextLine();
		//temp.toLowerCase();
		//if(temp == "yes")
		//	sale();
		while(isValid == false) {
			System.out.println("Amount Due: " + df.format(saleTotal) + ".");
			System.out.print("Amount taken: ");
			amountGiven = Double.parseDouble(sc.nextLine());
			if(amountGiven < saleTotal) {
				System.out.println("Sorry not enough money try again.");
				isValid = false;
			}
			else
				isValid = true;
		}
		changeDue = amountGiven - saleTotal;
		System.out.println("Customer Change: " + df.format(changeDue) + ".");
		inventory.removeItemByName(itemTemp.getName());
		saleTotal = amountGiven = changeDue = 0.0;
	}
	
	public void addItem() {
		Item itemTemp = new Item();
		System.out.print("Please enter the item you would like to add: ");
		temp = sc.nextLine();
		itemTemp.setName(temp);
		System.out.print("Please enter the price of the item: ");
		Double enteredDouble = Double.parseDouble(sc.nextLine());
		itemTemp.setPrice(enteredDouble);
		inventory.addItem(itemTemp);
	}
	
	public void removeItem() {
		boolean leave = false;
		do {
			System.out.println("Please enter the item to remove: ");
			String item = sc.nextLine();
			if(item.toLowerCase().contains("exit") == true) {
				leave = true;
				System.out.println("HERE");
			}else{
				inventory.removeItemByName(item);
				if(inventory.checkItemByName(item) == true)
					System.out.println(item +" was removed from the inventory.");
			}
		}while (leave == false);
	}
	
	public void changeItemPrice() {
		Item itemTemp = new Item();
		System.out.print("Please enter the name of the item you would like to change: ");
		itemTemp.setName(sc.nextLine());
		itemTemp = inventory.findItemByName(itemTemp.getName());
		System.out.print("Please enter the new price for " + itemTemp.getName() + ": ");
		newPrice = sc.nextDouble();
		itemTemp.setPrice(newPrice);
	}
	
	public void checkInventory() {
		//for(Item i: inventory) {
			//This needs to get the index of the first instance of each item, then store those indexes somewhere, get the items and then use item.getItemCount();
		//}
		inventory.getItems();
	}
	
	public void itemReturn() {
		Item itemTemp = new Item();
		System.out.print("Please enter an item to return: ");
		itemTemp.setName(sc.nextLine());
		System.out.print("Please enter the price of what is being returned: ");
		itemTemp.setPrice(sc.nextDouble());
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