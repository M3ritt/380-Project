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
		} catch(SAXException|ParserConfigurationException|IOException e) {
			e.printStackTrace();
			ul = new UserLogin();
			invt = null;
		}

		//Login the user to check their level of access
		//        ul.callToArms();
		ul.login(sc);
		ul.writeToXML();

		if(ul.getUserAccess()) {
			reg = new Register(invt);
			ml = new MemberList(new ArrayList<Member>());
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
					String changedName = sc.nextLine();
					System.out.print("Please enter the brand of the item: ");
					String changedBrand = sc.nextLine();
					System.out.print("Please enter the new price for the item: ");
					Double changedPrice = Double.parseDouble(sc.nextLine());        				
					reg.changeItemPrice(changedName, changedPrice, changedBrand);
					break;
				case "inventory":
					reg.checkInventory();
					break;
				case "find item":
					System.out.print("Please enter the item to be found: ");
					String itemToFind = sc.nextLine();
					reg.findItems(itemToFind);
					break;
				case "remove item":
					System.out.print("Enter the name of the item you want to remove: ");
					String eItem = sc.nextLine();
					System.out.print("Enter the name of the brand of the item: ");
					String eBrand = sc.nextLine();
					//invt.removeItemByName(eItem, eBrand);
					reg.removeItem(eItem, eBrand);
					break;
				case "return item":
					System.out.print("Please enter an item to return: ");
					String tempName = sc.nextLine();
					System.out.print("Please enter the brand of the item: ");
					String itemBrand = sc.nextLine();
					reg.itemReturn(tempName, itemBrand);
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
					Member newMember = new Member(mName, mAddress, number, Member.level.BRONZE);
					newMember.setPhoneNumber(number);
					ml.addMember(newMember);
					break;
				case "see members":
					ml.seeMembers();
					break;
				case "search member":
					System.out.print("What is the phone number?");
					String possibleNumber = sc.nextLine();
					Member currentBuyer = ml.findMemberByPhoneNumber(possibleNumber);
					if(currentBuyer != null) 
						System.out.println(currentBuyer);
					else
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