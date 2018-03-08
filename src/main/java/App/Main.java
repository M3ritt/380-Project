package App;

//import java.util.ArrayList;
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
        
        //System.out.println("Enter the name of the file: ");
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
//        invt.getItems();
        
        reg = new Register(invt);
        reg.checkInventory();
        reg.addItem();
        reg.sale();
        //reg.itemReturn();
        //reg.changeItemPrice();
        //invt.getItems();
        //invt.removeItemByName("gOLF CLUB sET");
        //invt.getItems();
        //reg.checkInventory();
        //invt.getItems();
    }
}