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
		if(checkItemByName(newItem.getName()) == true) { 
			findItemByName(newItem.getName()).incrementAmount();
			count++;
		} else {
			iList.add(count, newItem);
			count++;
			get(newItem).incrementAmount();
		}
	}
	
	//removes an item based on it's name
	public void removeItemByName(String itemName) {
		if(checkItemByName(itemName) == true) {
			findItemByName(itemName).decreaseAmount();
			count--;
		}else {
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
	public Item findItemByName(String name) {
		for(Item i: iList) {
			if(i.getName().toLowerCase().equals(name.toLowerCase()))
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
	
}