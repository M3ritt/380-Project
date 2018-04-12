package App;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main{
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args){
		Inventory invt;
		Register reg;
		UserLogin ul;
		MemberList ml;

		SAXParserFactory spf = SAXParserFactory.newInstance();
		String fileName = "InventoryFile.xml";

		try {
			InputStream xmlInput = new FileInputStream(fileName);
			SAXParser saxParser = spf.newSAXParser();
			InventoryParser ixmlp = new InventoryParser();
			saxParser.parse(xmlInput, ixmlp);
			invt = ixmlp.getInvt();

			//SAXParserFactory spf = SAXParserFactory.newInstance();
			String fileName2 = "users.xml";
			InputStream xmlInput2 = new FileInputStream(fileName2);
			InventoryParser ixmlp2 = new InventoryParser();
			saxParser.parse(xmlInput2, ixmlp2);

			ul = new UserLogin(ixmlp2.getUserList());
			
			String fileName3 = "MemberFile.xml";
			InputStream xmlInput3 = new FileInputStream(fileName3);
			InventoryParser ixmlp3 = new InventoryParser();
			saxParser.parse(xmlInput3, ixmlp3);
			ml = ixmlp3.getMembers();
		} catch(SAXException|ParserConfigurationException|IOException e) {
			e.printStackTrace();
			ul = new UserLogin();
			invt = null;
			ml = null;
		}

		//Login the user to check their level of access
		ul.callToArms();
		//ul.login(sc);
		ul.writeToXML();

		if(ul.getUserAccess()) {
			reg = new Register(invt, ml);
			//ml = new MemberList(new ArrayList<Member>());
			System.out.print("What would you like to do? ");
			String command = sc.nextLine();
			command = command.toLowerCase();

			while(!(command.equalsIgnoreCase("exit"))) {
				switch(command) {
				//should make it only for manager
				case "remove user":
					ul.removeOneUser(sc);
					ul.writeToXML();
					break;
				case "add item":
					System.out.print("Please enter the item you would like to add: ");
					String enteredName = sc.nextLine();
					System.out.print("Please enter the price of the item: ");
					Double enteredDouble = Double.parseDouble(sc.nextLine());
					System.out.println("Please enter the brand of the item: ");
					String brandName = sc.nextLine();
					reg.addItem(enteredName, enteredDouble, brandName);
					break;
				case "change price":
					System.out.print("Please enter the item to change the price: ");
					enteredName = sc.nextLine();
					System.out.print("Please enter the brand of the item: ");
					brandName = sc.nextLine();
					System.out.print("Please enter the new price for the item: ");
					enteredDouble = Double.parseDouble(sc.nextLine());        				
					reg.changeItemPrice(enteredName, enteredDouble, brandName);
					break;
				case "inventory":
					reg.checkInventory();
					break;
				case "find item":
					System.out.print("Please enter the item to be found: ");
					enteredName = sc.nextLine();
					reg.findItems(enteredName);
					break;
				case "remove item":
					System.out.print("Enter the name of the item you want to remove: ");
					enteredName = sc.nextLine();
					System.out.print("Enter the name of the brand of the item: ");
					brandName = sc.nextLine();
					//invt.removeItemByName(eItem, eBrand);
					reg.removeItem(enteredName, brandName);
					break;
				case "return item":
					System.out.print("Please enter an item to return: ");
					enteredName = sc.nextLine();
					System.out.print("Please enter the brand of the item: ");
					brandName = sc.nextLine();
					reg.itemReturn(enteredName, brandName);
					break;
				case "sale":
					//This should be looped until there are no more items to add 
					reg.sale();
					break;
				//I don't plan to keep the member parts here and will work on members more,  
				//just wanted to get it started
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
					ml.writeToXML();
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
					System.out.println("Cannot " + command);
					break;
				}
				System.out.print("What would you like to do?");
				command = sc.nextLine();
			}
			reg.writeToXML();
		} else {
			System.out.println("Not a valid user.");
		}
	}
}