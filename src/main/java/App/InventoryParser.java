package App;

//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;

public class InventoryParser extends DefaultHandler {
	private ArrayList<Item> iList;
	private ArrayList<User> uList;
	private double price;
	private Inventory newInv;
	private String itemName, pass, uNam;
	public void startDocument() throws SAXException {
		iList = new ArrayList<Item>();
		uList = new ArrayList<>();
		newInv = new Inventory(iList);
	}

	public void startElement(String namespaceURI,
							 String localName,
							 String qName,
							 Attributes atts) throws SAXException {
		
		switch(qName.toLowerCase()) {
			
		case "item":
			itemName = atts.getValue("name");
			String sprice = atts.getValue("price");
			price = Double.parseDouble(sprice);
			String itemBrand = atts.getValue("brand");
			Item i = new Item(itemName, price, itemBrand);
			//iList.add(i);
			newInv.addItem(i);
			break;
		case "user":
			pass = atts.getValue("password");
			uNam = atts.getValue("username");
			User u = new User(uNam, pass);
			uList.add(u);
		default:
			break;
		}
	}

	public void endElement(String namespaceURI,
							 String localName,
							 String qName) throws SAXException {
		if(qName.equals("item")) {
			newInv = new Inventory(iList);
		}
	}

	public void endDocument() throws SAXException {}

	public Inventory getInvt() {
		return this.newInv;
	}
	
	public ArrayList<User> getUserList() {
    		return this.uList;
    }
}