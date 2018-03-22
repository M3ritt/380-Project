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
        ArrayList<User> uList = new ArrayList<>();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        String fileName = "InventoryFile.xml";
        
        try{
            InputStream xmlInput = new FileInputStream(fileName);
            SAXParser saxParser = spf.newSAXParser();
            InventoryParser ixmlp = new InventoryParser();
            saxParser.parse(xmlInput, ixmlp);
            invt = ixmlp.getInvt();
        }
        catch(SAXException|ParserConfigurationException|IOException e){
            e.printStackTrace();
            invt = null;
        }
        
        //Login the user to check their level of access
//        UserLogin ul = new UserLogin();
//        ul.callToArms();
        
        reg = new Register(invt);
        System.out.print("What would you like to do? ");
        String command = sc.nextLine();
        command = command.toLowerCase();
        
        while(!(command.equalsIgnoreCase("exit"))) {
        		switch(command) {
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
        				System.out.println("Please enter the brand of the item: ");
        				String changedBrand = sc.nextLine();
        				System.out.print("Please enter the new price for the item: ");
        				Double changedPrice = Double.parseDouble(sc.nextLine());        				
        				reg.changeItemPrice(changedName, changedPrice, changedBrand);
        				break;
        			case "inventory":
        				reg.checkInventory();
        				break;
        			case "remove item":
        				System.out.print("Enter the name of the item you want to remove: ");
        				String eItem = sc.nextLine();
        				System.out.println("Enter the name of the brand of the item: ");
        				String eBrand = sc.nextLine();
        				//invt.removeItemByName(eItem, eBrand);
        				reg.removeItem(eItem, eBrand);
        				break;
        			case "return item":
        				System.out.print("Please enter an item to return: ");
        				String tempName = sc.nextLine();
        				System.out.println("Please enter the brand of the item: ");
        				String itemBrand = sc.nextLine();
        				reg.itemReturn(tempName, itemBrand);
        				break;
        			case "sale":
        				//This should be looped until there are no more items to add
        				reg.sale();
        				break;
        			default:
        				System.out.println("Cannot " + command);
        				break;
        		}
        		System.out.println("What would you like to do?");
        		command = sc.nextLine();
        }
    }
}