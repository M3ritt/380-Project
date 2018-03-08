package App;

public class User {
	private String userName, password;
	private double salesTotal;

	public User() {
		this.userName = this.password = "";
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
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
}