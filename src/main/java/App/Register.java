package App;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Register {

	private Inventory inventory, currentInventory;
	private MemberList mList;
	private String retVal = "";
	private double dailySalesTotal = 0;
	private double weeklySalesTotal = 0;
	private double saleTotal, amountGiven, changeDue, taxRate, newPrice;
	private Scanner sc = new Scanner(System.in);
	public DecimalFormat df = new DecimalFormat("#.##");
	ArrayList<Item> openingList;
	public enum Day { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
		public Day getNext() {
			return values()[(ordinal()+1) % values().length];
		}
	}
	Day currentDay;

	public Register(Inventory inventory, MemberList mList) {
		this.inventory = inventory;
		this.mList = mList;
		this.taxRate = 1.08;
		this.currentDay = Day.SUNDAY;
	}

	public void sale() {
		String enteredName;
		String brandName;
		int saleCount = 0;
		do {
			System.out.print("Please enter the item to remove, or press enter to leave: ");
			enteredName = sc.nextLine();
			if(enteredName.equals(""))
				break;
			System.out.println("Please enter the brand of the item: ");
			brandName = sc.nextLine();
			if(inventory.checkItemByName(enteredName, brandName) && inventory.findItemByName(enteredName, brandName).getAmount() > 0){
				System.out.println(enteredName +" from " +brandName+ " was removed from the inventory.");
				dailySalesTotal += inventory.findItemByName(enteredName, brandName).getPrice();
				saleTotal += inventory.findItemByName(enteredName, brandName).getPrice();
				inventory.removeItemByName(enteredName, brandName);
				saleCount++;
			} else if(!(enteredName.equals(""))){
				System.out.println(enteredName + " from "+ brandName+ " is not a valid item.");
			}
		} while(!(enteredName.equals("")));
		System.out.println("Are you a current member?");
		sc = new Scanner(System.in);
		String userStatus = sc.nextLine();
		if(userStatus.equalsIgnoreCase("No") || userStatus.equalsIgnoreCase("N")) {
			doSale(saleCount);
		} else if(userStatus.equalsIgnoreCase("Yes") || userStatus.equalsIgnoreCase("Y")) {
			System.out.println("What is your phone number?");
			String possibleNumber = sc.nextLine();
			Member currentBuyer = mList.findMemberByPhoneNumber(possibleNumber);
			if(currentBuyer != null) { 
				if(currentBuyer.getLevelOfMembership().equals(Member.level.BRONZE)) {
					saleTotal -= saleTotal * currentBuyer.getDiscount();
					mList.findMemberByPhoneNumber(possibleNumber).addToSales(saleTotal * taxRate);
					doSale(saleCount);
				} else if(currentBuyer.getLevelOfMembership().equals(Member.level.SILVER)) {
					saleTotal -= saleTotal * currentBuyer.getDiscount();
					mList.findMemberByPhoneNumber(possibleNumber).addToSales(saleTotal * taxRate);
					doSale(saleCount);
				} else {
					saleTotal -= saleTotal * currentBuyer.getDiscount();
					mList.findMemberByPhoneNumber(possibleNumber).addToSales(saleTotal * taxRate);
					doSale(saleCount);
				}
				mList.writeToXML();
			} else
				System.out.println("That number is not in our system.");
		}
	}

	public void doSale(int saleCount) {
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
		}
		changeDue = amountGiven - saleTotal;
		System.out.println("Customer Change: " + df.format(changeDue) + ".");
		//inventory.removeItemByName(itemTemp.getName());
		saleTotal = amountGiven = changeDue = 0.0;
	}

	public void addItem(String enteredName, Double enteredDouble, String brandName) {
		Item i = new Item (enteredName, enteredDouble, brandName);
		if(i != null)
			inventory.addItem(i);
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
			//if(itemTemp.getBrand().equalsIgnoreCase(brandName))
			itemTemp.setPrice(enteredDouble, brandName);
		} else 
			System.out.println("We do not have that item in our inventory.");
	}

	public void checkInventory() {
		inventory.getItems();
	}

	public void findItems(String name) {
		inventory.findItems(name);
	}

	public void itemReturn(String eName, String brandName) {
		Item itemTemp = new Item(eName, 0.0d, brandName);
		itemTemp = inventory.findItemByName(itemTemp.getName(), brandName);
		if (itemTemp != null) {	
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

	public String dailyInventory() {
		
		retVal = "";
		currentInventory = inventory;
		
		
		for (Item i : inventory.soldList) {
		
			retVal += "Sold " + i.getAmount() + " " + i.getName() + ". \n";	

		}
		
		return retVal;
		
	}

	public void weeklyInventory() {
		//This will check the inventory at the end of the week
	}

	public void weeklySalesTotals() {
		//This will get the weekly sales totals
	}

	public String dailyReport() {
		//This will give daily report for sales, inventory, total tax, and user metrics
		retVal = "";
		retVal += "Daily Sale Total: " + getDailySalesString() + ". \n";
		retVal += "Daily Inventory: \n";
		retVal += dailyInventory();
		
		return retVal;
		
	}

	public void weeklyReport() {
		//This will give the weekly report for sales, inventory, total tax, and user metrics.
	}

	public double getDailySalesTotal() {
		return dailySalesTotal;
	}
	
	public String getDailySalesString() {
		return Double.toString(dailySalesTotal);
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

	public void writeToXML() {
		UserXMLWriter uxmlw = new UserXMLWriter();
		uxmlw.writeForInventory(inventory.getInventory());
	}
	
	public void endDay() {
		
		weeklySalesTotal += dailySalesTotal;
		dailySalesTotal = 0;
		currentDay = currentDay.getNext();
		
	}
}