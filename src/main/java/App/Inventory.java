package App;

import java.util.ArrayList;

public class Inventory {
	//Some data structure to save the items... some type of array?
	//Generic for items
	private ArrayList<Item> iList;
	private int count;

	public Inventory(ArrayList<Item> iList) {
		this.iList = iList;
		count = iList.size();
	}

	//gets the count of items in the inventory 
	public int getCount(){
		return count;
	}

	//adds a new item to the inventory
	public void addItem(Item newItem){
		if(checkItemByName(newItem.getName(), newItem.getBrand()) && 
				(newItem.getPrice() == findItemByName(newItem.getName(), newItem.getBrand()).getPrice()))
		{
			findItemByName(newItem.getName(), newItem.getBrand()).incrementAmount();
			count++;
		}
		else if(checkItemByName(newItem.getName(), newItem.getBrand()) && 
				(newItem.getPrice() != findItemByName(newItem.getName(), newItem.getBrand()).getPrice())) 
		{
			System.out.println("That price does not match what we have in our system");
			System.out.println("We are adding the item but with the original price.");
			findItemByName(newItem.getName(), newItem.getBrand()).incrementAmount();
			count++;
		} 
		else {
			iList.add(count, newItem);
			count++;
			get(newItem).incrementAmount();
		}
	}

	//removes an item based on it's name
	public void removeItemByName(String itemName, String brandName) {
		if(checkItemByName(itemName, brandName) == true) {
			findItemByName(itemName, brandName).decreaseAmount();
			count--;
			//System.out.println(itemName + " from: "+brandName+ " was removed.");
		} else {
			System.out.println("That item is not in the inventory.");
		}
	}

	//prints out all items in stock
	public void getItems(){
		for(Item i: iList) {
			if(i.getAmount() != 0)
				System.out.println(i);
		}
	}

	//returns true if the item name is in the inventory
	public boolean checkItemByName(String name, String brandName) {
		for(Item i: iList) {
			if(i.getName().equalsIgnoreCase(name) && (i.getBrand().equalsIgnoreCase(brandName)))
				return true;
		}
		return false;
	}

	//returns the item if it's name in the inventory, null otherwise
	public Item findItemByName(String name, String brandName) {
		for(Item i: iList) {
			if(i.getName().equalsIgnoreCase(name) && (i.getBrand().equalsIgnoreCase((brandName))))
				return i;
		}
		return null;
	}

	//returns the item if it is in the inventory
	public Item get(Item item) {
		if(iList.contains(item) == true) {
			if(checkItemByName(item.getName(), item.getBrand())) {
				return item;
			}
		}
		else {
			System.out.println("Sorry the item " + item.getName() + " is not in the inventory.");
		}
		return null;
	}



	public void findItems(String itemName) {
		boolean hasItem = false;
		for(Item i : iList) {
			if(itemName.equalsIgnoreCase(i.getName()) && (i.getAmount() > 0)) {
				System.out.println(i);
				hasItem = true;
			}	
		}
		if(hasItem == false)
			System.out.println("We do not have any: "+itemName+ ".");
	}

	public ArrayList<Item> getInventory() {
		return this.iList;
	}
}