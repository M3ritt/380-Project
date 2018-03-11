package App;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.util.Scanner;

public class Main{

    static Scanner sc = new Scanner(System.in);

	public static void main(String[] args){
        Inventory invt;
        Register reg;

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
        
        reg = new Register(invt);
        System.out.println("What would you like to do?");
        String command = sc.nextLine();
        
        while(!(command.equalsIgnoreCase("exit"))) {
        		switch(command) {
        			case "add item":
        				reg.addItem();
        				break;
        			case "change price":
        				reg.changeItemPrice();
        				break;
        			case "inventory":
        				reg.checkInventory();
        				break;
        			case "remove item":
        				System.out.print("Enter the name of the item you want to remove");
        				String eItem = sc.nextLine();
        				invt.removeItemByName(eItem);
        			case "return item":
        				reg.itemReturn();
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