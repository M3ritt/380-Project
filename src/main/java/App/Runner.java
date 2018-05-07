package App;

import java.util.Scanner;

public class Runner {
	private Register reg;
	private UserLogin ul;
	private MemberList ml;

	public Runner(Register reg, UserLogin ul, MemberList ml) {
		this.reg = reg;
		this.ul = ul;
		this.ml = ml;
	}

	public void getIntoSystem() {
		User u = null;	
		boolean entered = false;

		do {
			ul.callToArms();
			u = ul.getCurrentUser();
			if(ul.getUserAccess()) {
				decision(u);
				entered = true;
			} else if(u.getAccessLevel() == null) {
				ul.writeToXML();
				reg.writeToXML();
				ml.writeToXML();
				System.exit(0);
			} else 
				System.out.println("Not a valid User.");
		}while(entered == false);
	}

	public void decision(User current) {
		Scanner sc  = new Scanner(System.in);
		System.out.println("What would you like to do?\n" + 
						   "Options are:\n" + 
						   "Register, Manage Members, Manager, See User, Help, Log Off or Shut Down.");
		String decision = sc.nextLine().toLowerCase();
		while(!decision.equalsIgnoreCase("shut down")) {
			switch(decision) {
			case "help":
				System.out.println("Register: User opens the register.\n" + 
						"Manage Members: Allows user to access membership tasks.\n" +
						"Manager: User can access the manager commands.\n" +
						"See User: Prints current user.\n" +
						"Log Off: Logs the current user off.\n" + 
						"Shut Down: Close down the system.\n");
				break;
			case "register":
				registerStuff(current);
				break;
			case "manage members":
				otherStuff(current);
				break;
			case "manager":
				if(current.getAccessLevel().equals(User.access.MANAGER))
					managerStuff(current);
				else
					System.out.println("You do not have access");
				break;
			case "see user":
				System.out.println("User: " + current.getUserName() +
								  " With the access level of : " + current.getAccessLevel());
				break;
			case "log off":
				getIntoSystem();
				break;
			default:
				System.out.println(decision + " is not a choice.");
			}
			System.out.println("What would you like to do?\n" +
							 "Options are:\n" + "Register, Manage Members, Manager, Help, Log Off or Shut Down.");
			decision = sc.nextLine().toLowerCase();
		}
		System.out.println();
		ul.writeToXML();
		reg.writeToXML();
		ml.writeToXML();
		System.exit(0);
	}

	public void registerStuff(User current) {
		String enteredName, brandName;
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do in the Register? Type 'help' for options");
		String command = sc.nextLine().toLowerCase();
		while(!command.equalsIgnoreCase("exit")) {
			switch(command) {
			case "help":
				System.out.println("Inventory: This prints out the current inventory.\n" +
						"Find Item: Finds the specified item.\n"+ 
						"Return Item: Takes the item being returned if it is valid.\n" +
						"Sale: Check the customer out.\n" +
						"Exit: Exits the register.\n");
				break;
			case "inventory":
				reg.checkInventory();
				break;
			case "find item":
				System.out.print("Please enter the item to be found: ");
				enteredName = sc.nextLine();
				reg.findItems(enteredName);
				break;
			case "return item":
				System.out.print("Please enter an item to return: ");
				enteredName = sc.nextLine();
				System.out.print("Please enter the brand of the item: ");
				brandName = sc.nextLine();
				reg.itemReturn(enteredName, brandName);
				break;
			case "sale":
				reg.sale(current);
				break;
			default:
				System.out.println("Cannot: "+command);
				break;
			}
			System.out.println("What would you like to do in the Register? Type 'help' for options");
			command = sc.nextLine().toLowerCase();
		}
		reg.writeToXML();
		decision(current);
	}

	public void otherStuff(User current) {
		Scanner sc = new Scanner(System.in);
		System.out.println("How would you like to manage members? Type 'help' for options");
		String command = sc.nextLine().toLowerCase();
		while(!command.equalsIgnoreCase("exit")) {
			switch(command) {
			case "help":
				System.out.println("Add Member: Adds a member.\n" +
						"See Members: User can see all members.\n" + 
						"Search Member: User searches for a member based on the member's phone number.\n" +
						"Exit: Exits the Members section.\n");
				break;
			case "add member":
				System.out.print("What is the members full name: ");
				String mName = sc.nextLine();
				System.out.print("What is the members address: ");
				String mAddress = sc.nextLine();				
				System.out.print("What is the phone number: ");
				String number = sc.nextLine();
				Member newMember = new Member(mName, mAddress, number, Member.level.BRONZE, 0);
				newMember.setPhoneNumber(number);
				ml.addMember(newMember);
				break;
			case "see members":
				ml.seeMembers();
				break;
			case "search member":
				System.out.print("What is the member's phone number: ");
				number = sc.nextLine();
				Member currentBuyer = ml.findMemberByPhoneNumber(number);
				if(currentBuyer != null) {
					System.out.println(currentBuyer);
				} else
					System.out.println("No member with that phone number.");
				break;
			default:
				System.out.println("Cannot: "+command);
				break;
			}
			System.out.println("How would you like to manage members? Type 'help' for options");
			command = sc.nextLine().toLowerCase();
		}
		ml.writeToXML();
		decision(current);
	}

	public void managerStuff(User current) {
		String enteredName, brandName;
		Double enteredDouble = 0.0;
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do as a manager? Type 'help' for options");
		String command = sc.nextLine().toLowerCase();
		while(!command.equalsIgnoreCase("exit")) {
			switch(command) {
			case "help":
				System.out.print("Add Member: Adds a member to the system.\n" +
						"See Members: User can see all members.\n" + 
						"Search Member: User searches for a member based on the member's phone number.\n"+
						"Remove User: Removes a user.\n" + 
						"Remove Member: Removes a member based on phone number.\n" +
						"Add Item: Adds an item to the inventory.\n" +
						"Change Price: Changes the price of an item.\n" + 
						"Remove Item: Removes an item from the inventory.\n"+
						"User Stats: Shows the average sales of each user by times logged in.\n" +
						"End Day: Ends the current day.\n" +
						"Daily Inventory: Gets todays inventory.\n"+
						"Daily Report: Gets the number of items sold that day.\n" + 
						"Exit: Exits manager section.\n");
				break;
			case "remove user":
				ul.removeOneUser(sc);
				break;
			case "remove member":
				System.out.println("What is the phone number of the member?");
				String removeNumber = sc.nextLine();
				Member removeMember = ml.findMemberByPhoneNumber(removeNumber);
				System.out.println("Remove "+removeMember.getName() + "?");
				String yesOrNo = sc.nextLine();
				if(yesOrNo.equalsIgnoreCase("yes") || yesOrNo.equalsIgnoreCase("y")) {				
					System.out.println(removeMember.getName() + " is being removed.");
					ml.removeMember(removeMember);
				} else
					System.out.println(removeMember.getName() + " was not removed.");
				break;
			case "add item":
				System.out.print("Please enter the item you would like to add: ");
				enteredName = sc.nextLine();
				System.out.print("Please enter the price of the item: ");
				while(!sc.hasNextDouble()) {
					System.out.println("Invalid input. Please enter the price of the item: ");
					sc.nextLine();
				}
				enteredDouble = Double.parseDouble(sc.nextLine());
				System.out.println("Please enter the brand of the item: ");
				brandName = sc.nextLine();
				reg.addItem(enteredName, enteredDouble, brandName);
				break;
			case "change price":
				System.out.print("Please enter the item to change the price: ");
				enteredName = sc.nextLine();
				System.out.print("Please enter the brand of the item: ");
				brandName = sc.nextLine();
				System.out.print("Please enter the new price for the item: ");
				while(!sc.hasNextDouble()) {
					System.out.println("Invalid input. Please enter the price of the item: ");
					sc.nextLine();
				}
				enteredDouble = Double.parseDouble(sc.nextLine());        				
				reg.changeItemPrice(enteredName, enteredDouble, brandName);
				break;
			case "remove item":
				System.out.print("Enter the name of the item you want to remove: ");
				enteredName = sc.nextLine();
				System.out.print("Enter the name of the brand of the item: ");
				brandName = sc.nextLine();
				reg.removeItem(enteredName, brandName);
				break;
			case "end day":
				System.out.println("WARNING! This will clear all current sale totals and move to the next day.");
				System.out.print("Are you sure you want to end the day? ");
				String input = sc.nextLine();
				input = input.toLowerCase();
				if (input.equals("yes")) {
					System.out.println("Ending " + reg.currentDay.toString().toLowerCase() + " and beggining " + reg.currentDay.getNext().toString().toLowerCase() + ".");
					reg.endDay();
				}
				else 
					System.out.println("Day has not been ended.");
				break;
			case "daily inventory":
				System.out.println(reg.dailyInventory());
				break;
			case "weekly inventory":
				System.out.println(reg.weeklyInventory());
				break;
			case "weekly report":
				System.out.println(reg.weeklyReport());
				break;
			case "daily report":
				System.out.println(reg.dailyReport());
				break;
			case "add member":
				System.out.print("What is the members full name: ");
				String mName = sc.nextLine();
				System.out.print("What is the members address: ");
				String mAddress = sc.nextLine();				
				System.out.print("What is the phone number: ");
				String number = sc.nextLine();
				Member newMember = new Member(mName, mAddress, number, Member.level.BRONZE, 0);
				newMember.setPhoneNumber(number);
				ml.addMember(newMember);
				break;
			case "see members":
				ml.seeMembers();
				break;
			case "search member":
				System.out.print("What is the phone number?");
				number = sc.nextLine();
				Member currentBuyer = ml.findMemberByPhoneNumber(number);
				if(currentBuyer != null) {
					System.out.println(currentBuyer);
				} else
					System.out.println("That number is not in our system.");
				break;
			case "user stats":
				ul.displayEmployeeStats();
				break;
			default:
				System.out.println("Cannot: "+command);
				break;
			}
			System.out.println("What would you like to do as a manager? Type 'help' for options");
			command = sc.nextLine().toLowerCase();
		}

		ul.writeToXML();
		ml.writeToXML();
		reg.writeToXML();
		decision(current);
	}
}
