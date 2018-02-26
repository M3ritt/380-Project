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
	private double price;
	private Inventory newInv;
	private String itemName;
	public void startDocument() throws SAXException {
		iList = new ArrayList<Item>();
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
				Item i = new Item(itemName, price);
				iList.add(i);
				newInv.addItem(i);
				break;
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
}