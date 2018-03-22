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
		String brandName;
		int saleCount = 0;
		do {
			System.out.print("Please enter the item to remove, or press enter to leave: ");
			enteredName = sc.nextLine();
			System.out.println("Please enter the brand of the item: ");
			brandName = sc.nextLine();
			if(inventory.checkItemByName(enteredName) && inventory.findItemByName(enteredName, brandName).getAmount() > 0){
				System.out.println(enteredName +" from " +brandName+ " was removed from the inventory.");
				dailySalesTotal += inventory.findItemByName(enteredName, brandName).getPrice();
				saleTotal += inventory.findItemByName(enteredName, brandName).getPrice();
				inventory.removeItemByName(enteredName, brandName);
				saleCount++;
			} else if(!(enteredName.equals(""))){
				System.out.println(enteredName + " from "+ brandName+ " is not a valid item.");
			}
		} while(!(enteredName.equals("")));
		if(saleCount > 0) {
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
	}

	public void addItem(String enteredName, Double enteredDouble, String brandName) {
		Item newItem = inventory.findItemByName(enteredName, brandName);
		if(newItem == null) {
			newItem = new Item(enteredName, enteredDouble, brandName);
			inventory.addItem(newItem);
		} else {
			if(newItem.getPrice() == enteredDouble) {
				newItem.incrementAmount();
			} else {
				System.out.println("The price entered was different than our price.");
				System.out.println("We added to our inventory but kept our original price.");
				newItem.incrementAmount();
			}
		}
	}

	public void removeItem(String enteredName, String enteredBrand) {
		Item newItem = inventory.findItemByName(enteredName, enteredBrand);
		if(newItem != null) {
			inventory.removeItemByName(enteredName, enteredBrand);
		} else {
			System.out.println("We do not have: "+enteredName+" from: "+enteredBrand+ " .");
		}
	}

	public void changeItemPrice(String enteredName, Double enteredDouble, String brandName) {
		Item itemTemp = inventory.findItemByName(enteredName, brandName);
		if(itemTemp != null) {
			if(itemTemp.getBrand().equals(brandName))
				itemTemp.setPrice(enteredDouble, brandName);
		} else 
			System.out.println("We do not have that item in our inventory.");
	}

	public void checkInventory() {
		inventory.getItems();
	}

	public void itemReturn(String eName, String brandName) {
		Item itemTemp = new Item(eName, 0.0d, brandName);
		itemTemp = inventory.findItemByName(itemTemp.getName(), brandName);
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

	public double checkPrice(String eName, String brandName) {
		//This method will take in an item and return the price.
		Item i = inventory.findItemByName(eName, brandName);
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