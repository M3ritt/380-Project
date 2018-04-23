package App;

public class User {
	private String userName, password;
	private double salesTotal;
	private int timesLoggedIn;
	private access level;

	public User() {
		this.userName = this.password = "";
	}
	public User(String userName, String password, access level) {
		this.userName = userName;
		this.password = password;
		this.level = level;
	}
	public User(String userName, String password, double salesTotal, access level, int timesLoggedIn) {
		this.userName = userName;
		this.password = password;
		this.salesTotal = salesTotal;
		this.level = level;
		this.timesLoggedIn = timesLoggedIn;
	}
	
	public enum access{
		CASHIER, MANAGER
	}
	
	public void setAccessLevel(access level) {
		this.level = level;
	}
	
	public access getAccessLevel() {
		return this.level;
	}

	public double getSalesTotal() {
		return salesTotal;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setUserName(String influx) {
		this.userName = influx;
	}

	public void setPassword(String qwop) {
		this.password = qwop;
	}
	
	public void userSale(double sale) {
		this.salesTotal += sale;
	}
	
	public void addLoginTime() {
		this.timesLoggedIn++;
	}

	public String showDailySalesAverage() {
		double returnable = salesTotal / timesLoggedIn;
		return "" + returnable;
	}
	
	public double showSalesTotal() {
		return salesTotal;
	}

	@Override
	public String toString() {
		return "User: "+getUserName() + " with the password: "+getPassword();
	}
}