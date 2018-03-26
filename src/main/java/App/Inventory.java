package App;


import java.util.ArrayList;
public class Inventory {
	//Some data structure to save the items... some type of array?
	//Generic for items
	private ArrayList<Item> iList;
	private int count;
	private Item temp;
	
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
		if(checkItemByName(newItem.getName()) == true && (haveBrand(newItem.getName(), newItem.getBrand()))){
				findItemByName(newItem.getName(), newItem.getBrand()).incrementAmount();
				count++;
		} else {
			iList.add(count, newItem);
			count++;
			get(newItem).incrementAmount();
		}
	}
	
	//removes an item based on it's name
	public void removeItemByName(String itemName, String brandName) {
		if(checkItemByName(itemName) == true) {
			findItemByName(itemName, brandName).decreaseAmount();
			count--;
			System.out.println(itemName + " from: "+brandName+ " was removed.");
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
	public boolean checkItemByName(String name) {
		for(Item i: iList) {
			if(i.getName().toLowerCase().equals(name.toLowerCase()))
				return true;
		}
		return false;
	}
	
	//returns the item if it's name in the inventory, null otherwise
	public Item findItemByName(String name, String brandName) {
		for(Item i: iList) {
			if(i.getName().toLowerCase().equals(name.toLowerCase()) && (i.getBrand().toLowerCase().equals(brandName.toLowerCase())))
				return i;
		}
		return null;
	}
	
	//returns the item if it is in the inventory
	public Item get(Item item) {
		if(iList.contains(item) == true) {
			for(int i = 0; i <= iList.size(); i++) {
				temp = iList.get(i);
				if(temp.getName().equals(item.getName())) {
					return temp;
				}
			}
		}
		else {
			System.out.println("Sorry the item " + item.getName() + " is not in the inventory.");
		}
		return null;
	}
	
	public boolean haveBrand(String itemName, String brandName) {
		for(Item i : iList) {
			if(i.getBrand().toLowerCase().equals(brandName.toLowerCase()) && (i.getName().toLowerCase() == itemName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Item> getInventory() {
		return this.iList;
	}
}