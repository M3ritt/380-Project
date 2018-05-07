package App;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Register {

	private Inventory inventory, startingInventory;
	private MemberList mList;
	private String retVal = "";
	private String weeklyReport;
	private double dailySalesTotal = 0;
	private double weeklySalesTotal = 0;
	private double saleTotal, amountGiven, changeDue, taxRate, newPrice;
	private Scanner sc = new Scanner(System.in);
	public DecimalFormat df = new DecimalFormat("#.##");
	public enum Day { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
		public Day getNext() {
			return values()[(ordinal()+1) % values().length];

		}
		String report;
	}
	Day currentDay;

	public Register(Inventory inventory, MemberList mList) {
		this.inventory = inventory;
		this.mList = mList;
		this.taxRate = 1.08;
		this.currentDay = Day.SUNDAY;
	}

	public void sale(User u) {
		String enteredName;
		String brandName;
		int saleCount = 0;
		ArrayList<Item> removedItems = new ArrayList<Item>();
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
				u.userSale(inventory.findItemByName(enteredName, brandName).getPrice());
				removedItems.add(inventory.findItemByName(enteredName, brandName));
				inventory.removeItemByName(enteredName, brandName);
				if (checkLowInventory(enteredName, brandName) == true)
					System.out.println("Warning: Running low on " + enteredName + " only two or less left.");
				saleCount++;
			} else if(!(enteredName.equals(""))){
				System.out.println(enteredName + " from "+ brandName+ " is not a valid item.");
			}
			System.out.println();
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
				//mList.writeToXML();
			} else {
				System.out.println("That number is not in our system.");
				System.out.println("No purchase was made.");
				readdItems(removedItems, u);
			}
		}
	}
	
	//If the user says they are a member it would get rid of the item without them purchasing it
	public void readdItems(ArrayList<Item> items, User u) {
		for(Item i : items) {
			inventory.addItem(i);
			dailySalesTotal -= i.getPrice();
			u.userSale(i.getPrice() * -1);
			saleTotal = amountGiven = changeDue = 0.0;
		}
	}

	public void doSale(int saleCount) {
		if(saleCount > 0) {
			saleTotal = saleTotal * taxRate;
			boolean qualified = false;
			while(qualified == false) {
				System.out.println("Amount Due: " + df.format(saleTotal) + ".");
				System.out.println("Amount taken: ");
				while(!sc.hasNextDouble()) {
					System.out.println("Invalid input. Enter the amount taken.");
					sc.nextLine();
				}
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
			System.out.println("We do not have: "+enteredName+" from: "+enteredBrand+ ".");
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

		ArrayList<Item> startingList = startingInventory.getInventory();
		ArrayList<Item> current = inventory.getInventory();
		int tempCount;

		for (Item i : startingList) {

			for (Item j : current) {

				if (i.getName().equals(j.getName())){
					tempCount = i.getAmount() - j.getAmount();
					retVal += "Sold " + tempCount + " " + i.getName() + ". \n";	
				}

			}

		}

		return retVal;

	}

	public String weeklyInventory() {

		return weeklyReport;

	}

	public String weeklySalesTotals() {

		return "" + weeklySalesTotal;

	}

	public String dailyReport() {

		retVal = "";
		retVal += currentDay.toString() + " " + "Daily Sale Total: " + getDailySalesString() + ". \n";
		retVal += currentDay.toString() + " " + "Daily Inventory: \n";
		retVal += dailyInventory();

		return retVal;

	}

	public String weeklyReport() {

		retVal = "";
		retVal += "Weekly Sale Total: " + weeklySalesTotal + ". \n";
		retVal += weeklyReport;

		return retVal;

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

	public String getCurrentDay() {
		return currentDay.toString();
	}

	public boolean checkLowInventory(String itemName, String brandName) {

		Item tempItem = inventory.findItemByName(itemName, brandName);

		if (tempItem.getAmount() <= 2)
			return true;
		else
			return false;

	}

	public void openRegister() {

		ArrayList<Item> startingList = new ArrayList<Item>();
		ArrayList<Item> tempList = inventory.getInventory();
		for (Item i : tempList) {
			Item tempItem = new Item(i.getName(), i.getPrice(), i.getBrand());
			tempItem.setAmount(i.getAmount());
			startingList.add(tempItem);
		}
		startingInventory = new Inventory(startingList);

	}

	public void writeToXML() {
		UserXMLWriter uxmlw = new UserXMLWriter();
		uxmlw.writeForInventory(inventory.getInventory());
	}

	public void endDay() {
		
		System.out.println(dailyReport());
		currentDay.report = dailyReport();

		if (currentDay.equals(Day.SATURDAY)) {
			weeklySalesTotal = 0;
			weeklyReport = ""; 
			}
		else 
			weeklySalesTotal += dailySalesTotal;
		dailySalesTotal = 0;
		weeklyReport += currentDay.report;
		currentDay = currentDay.getNext();
		openRegister();

	}
}