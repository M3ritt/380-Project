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
		ul.callToArms();

		User u = ul.getCurrentUser();

		if(ul.getUserAccess()) {
			decision(u);
		} else if(u == null) {
			ul.writeToXML();
			reg.writeToXML();
			ml.writeToXML();
			System.exit(0);
		} else 
			System.out.println("Not a valid User.");
	}

	public void decision(User current) {
		Scanner sc  = new Scanner(System.in);
		System.out.println();
		System.out.println("Register, Other, Manager, log off, or exit?");
		String decision = sc.nextLine().toLowerCase();
		while(!decision.equalsIgnoreCase("exit")) {
			switch(decision) {
			case "register":
				registerStuff(current);
				break;
			case "other":
				otherStuff(current);
				break;
			case "manager":
				if(current.getAccessLevel().equals(User.access.MANAGER))
					managerStuff(current);
				else
					System.out.println("You do not have access");
				break;
			case "log off":
				getIntoSystem();
				break;
			default:
				System.out.println(decision + " is not a choice.");
			}
			System.out.println("Register, Other, Manager or log off?");
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
		System.out.println("What would you like to do?");
		String command = sc.nextLine().toLowerCase();
		while(!command.equalsIgnoreCase("exit")) {
			switch(command) {
			case "help":
				System.out.println("inventory: prints out the current inventory." + "\n" +
						"find item: finds a desired item." +"\n"+ 
						"return item: can return an item." + "\n" +
						"sale: can do a sale.");
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
			System.out.println();
			System.out.println("What would you like to do?");
			command = sc.nextLine();
		}
		reg.writeToXML();
		decision(current);
	}

	public void otherStuff(User current) {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do?");
		String command = sc.nextLine().toLowerCase();
		while(!command.equalsIgnoreCase("exit")) {
			switch(command) {
			case "help":
				System.out.println("add member: adds a member."+ "\n" +
						"see members: can see all members." + "\n" + 
						"search member: searches for a member from specific phone number.");
				break;
			case "add member":
				System.out.print("What is the members name(first and last): ");
				String mName = sc.nextLine();
				System.out.print("What is the members address: ");
				String mAddress = sc.nextLine();				
				System.out.print("What is the phone number?");
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
			default:
				System.out.println("Cannot: "+command);
				break;
			}
			System.out.println();
			System.out.println("What would you like to do?");
			command = sc.nextLine();
		}
		ml.writeToXML();
		decision(current);
	}

	public void managerStuff(User current) {
		System.out.println();
		String enteredName, brandName;
		Double enteredDouble = 0.0;
		Scanner sc = new Scanner(System.in);
		System.out.println("What would you like to do?");
		String command = sc.nextLine().toLowerCase();
		while(!command.equalsIgnoreCase("exit")) {
			switch(command) {
			case "help":
				System.out.println("add member: adds a member."+ "\n" +
						"see members: can see all members." + "\n" + 
						"search member: searches for a member from specific phone number." + "\n"+
						"remove user: removes a user." + "\n" + 
						"remove member: removes a member based on phone number." + "\n" +
						"add item: adds an item to the inventory." + "\n" +
						"change price: changes the price of an item." + "\n" + 
						"remove item: removes an item from the inventory." + "\n"+
						"end day: ends current day." + "\n" +
						"daily inventory: gets the inventory of the day." + "\n"+
						"daily report: gets the number of items sold that day.");
				break;
			case "remove user":
				ul.removeOneUser(sc);
				break;
			case "remove member":
				System.out.println("What is the phone number of the member?");
				String removeNumber = sc.nextLine();
				Member removeMember = ml.findMemberByPhoneNumber(removeNumber);
				System.out.println("Remove "+removeMember.getName() + " ?");
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
					System.out.println("Invalid input. Please enter the price of the item:");
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
					System.out.println("Invalid input. Please enter the price of the item:");
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
				System.out.println("WARNING! This will clear all current sale totals and move to the next day");
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
				System.out.print("What is the members name(first and last): ");
				String mName = sc.nextLine();
				System.out.print("What is the members address: ");
				String mAddress = sc.nextLine();				
				System.out.print("What is the phone number?");
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
			default:
				System.out.println("Cannot: "+command);
				break;
			}
			System.out.println();
			System.out.print("What would you like to do?");
			command = sc.nextLine();
		}
		//Manager can add users/members/items so all 3 are needed.
		ul.writeToXML();
		ml.writeToXML();
		reg.writeToXML();
		decision(current);
	}
}
