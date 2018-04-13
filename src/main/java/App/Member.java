package App;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Member {
	private String name, address;
	private level membership;
	private String phoneNumber;
	private double totalAmountSpent;
	public DecimalFormat df = new DecimalFormat("#.##");
	
	public Member(String name,String address, String phoneNumber, level membership, double totalAmountSpent) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.membership = membership;
		this.totalAmountSpent = totalAmountSpent;
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
	
	public double getAmountSpent() {
		this.totalAmountSpent = round(this.totalAmountSpent);
		return this.totalAmountSpent;
	}
	
	public void addToSales(double spent) {
		this.totalAmountSpent = this.totalAmountSpent + round(spent);
		checkIfChangeOfMembership();
	}
	
	public void checkIfChangeOfMembership() {
		if(totalAmountSpent > 100 && totalAmountSpent < 1000) {
			setState(level.SILVER);
		} else if (totalAmountSpent > 1000) {
			setState(level.GOLD);
		}
	}
	
	//rounds it to two decimal places
	public double round(double value) {
	    BigDecimal rounded = new BigDecimal(value);
	    rounded = rounded.setScale(2, RoundingMode.HALF_UP);
	    return rounded.doubleValue();
	}
	
	//members can see how close to moving up
	public void checkAmountSpent() {
		System.out.println(df.format(this.totalAmountSpent));
	}
	
	//Can easily change amount of discount for different levels
	public double getDiscount() {
		if(getLevelOfMembership().equals(Member.level.BRONZE)) {
			return .01;
		} else if(getLevelOfMembership().equals(Member.level.SILVER)) {
			return .02;
		} else 
			return .03;
	}
	
	@Override
	public String toString() {
		return getName() + " who lives at: "+ getAddress()+ " with the phone number: "+getPhoneNumber()+ " is a: " +getLevelOfMembership()+ " level member who has spent: "+getAmountSpent()+".";
	}
}
