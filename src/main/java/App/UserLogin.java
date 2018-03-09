package App;

import java.util.Scanner;
public class UserLogin {
	//Ask if first time user -> Y = call a function inorder to create the new user || N = call a function to check the...
	//users creds --> this would be used at first in terminal
	//Parse the XML file that the users are stored in
	Scanner sc = new Scanner(System.in);
	boolean userAccess;
	
	public UserLogin() {
		userAccess = false;
	}
	public void callToArms() {
		System.out.println("New User?");
		String userStatus = sc.nextLine();
		if(userStatus.equalsIgnoreCase("Yes")) {
			//Call the newUserSetup() method
			//Call the login() method
		} else {
			//Call the login() method
		}
	}
	
	public void newUserSetup() {
		//Create the User object
	}

	public void login() {
		//Call the database the users are saved in
		//Compare to see if this is a valid user
		System.out.println();
	}

	public boolean getUserAccess() {
		return userAccess;
	}

	//----------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) {

	}
}