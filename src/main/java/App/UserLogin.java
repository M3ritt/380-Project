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
	public User callToArms() {
		System.out.println("New User?");
		String userStatus = sc.nextLine();
		if(userStatus.equalsIgnoreCase("Yes")) {
			return newUserSetup();
			//Call the login() method
		} else {
			//Call the login() method
		}
		return null;
	}
	
	public User newUserSetup() {
		//Create the User object
		System.out.print("Enter a username. ");
		String createdUName = sc.nextLine();
		System.out.print("Enter a password. ");
		String firstEnteredPass = sc.nextLine();
		System.out.print("Enter the password again. ");
		if(firstEnteredPass.equals(sc.nextLine())) {
			User userToCreate = new User(createdUName, firstEnteredPass);
			return userToCreate;
		}
		else
			newUserSetup();
		return null;
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