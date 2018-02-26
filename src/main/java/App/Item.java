package App;

public class Item {
	private String itemName;
	private double price;
	private int amount = 0;
	//private int numSold;
	//Could convert this to a hashmap

	public Item(String itemName, double price) {
		this.itemName = itemName;
		this.price = price;
	}
	
	public Item() {
		itemName = "";
		price = 0.0;
	}

	public String getName() {
		return this.itemName;
	}
	
	public void setName(String itemName) {
		this.itemName = itemName;
	}
	
	public void incrementAmount() {
		this.amount ++;
	}
	
	public void decreaseAmount() {
		this.amount--;;
	}
	
	public int getAmount() {
		return this.amount;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double newPrice) {
		this.price = newPrice;
	}

	@Override
	public String toString() {
		//return "Amount of item: "+this.getAmount() + " Item: "+ this.itemName + " Price: " + this.price + ".";
		return this.amount +"x "+this.itemName + "(s)"+ " with a price of: "+this.price;
	}
}