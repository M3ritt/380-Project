package App;

public class Member {
	private String name, address;
	private level membership;
	private String phoneNumber;
	private double totalAmountSpent = 0;
	
	public Member(String name,String address, String phoneNumber, level membership) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.membership = membership;
	}
	
	public enum level {
		BRONZE, SILVER, GOLD 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber.length() == 10) {
			this.phoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3,6)+"-"+phoneNumber.substring(6,10);
		} else 
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setState(level membership) {
		this.membership = membership;
	}
	
	public level getLevelOfMembership() {
		return this.membership;
	}
	
	public void addToSales(double spent) {
		this.totalAmountSpent += spent;
	}
	
	public void checkIfChangeOfMembership() {
		if(totalAmountSpent > 100)
			setState(level.SILVER);
		else if (totalAmountSpent > 1000)
			setState(level.GOLD);			
	}
	
	@Override
	public String toString() {
		return getName() + " who lives at: "+ getAddress()+ " with the phone number: "+getPhoneNumber()+ " is a: " +getLevelOfMembership()+ " level member.";
	}
}
