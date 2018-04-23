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
	private ArrayList<Member> mList;
	private double price, sales;
	private int nLogin;
	private Inventory newInv;
	private MemberList members;
	private String itemName, pass, uNam, memName, address, phoneNumber, level, totalSpent, uAccess;
	public void startDocument() throws SAXException {
		iList = new ArrayList<Item>();
		uList = new ArrayList<>();
		newInv = new Inventory(iList);
		mList = new ArrayList<Member>();
		members = new MemberList(mList);
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
			User u;
			pass = atts.getValue("password");
			uNam = atts.getValue("username");
			uAccess = atts.getValue("accessLevel");
			String spouse = atts.getValue("loginTimes");
			nLogin = Integer.parseInt(spouse);
			String yowse = atts.getValue("sales");
			sales = Double.parseDouble(yowse);
			if(uAccess.equalsIgnoreCase("manager"))
				u = new User(uNam, pass, sales, User.access.MANAGER, nLogin);
			else 
				u = new User(uNam, pass, sales, User.access.CASHIER, nLogin);
			
			uList.add(u);
			break;
		case "member":
			memName = atts.getValue("memberName");
			address = atts.getValue("address");
			phoneNumber = atts.getValue("phoneNumber");
			level = atts.getValue("level");
			totalSpent = atts.getValue("totalSpent");
			//int spent = 0;
			double spent = Double.parseDouble(totalSpent);
			if(level.equalsIgnoreCase("bronze")) {
				members.addMember(new Member(memName, address, phoneNumber, Member.level.BRONZE, spent));
			} else if(level.equalsIgnoreCase("silver"))
				members.addMember(new Member(memName, address, phoneNumber, Member.level.SILVER, spent));
			else 
				members.addMember(new Member(memName, address, phoneNumber, Member.level.GOLD, spent));
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
		} else if(qName.equals("member"))
			members = new MemberList(mList);
	}

	public void endDocument() throws SAXException {}

	public Inventory getInvt() {
		return this.newInv;
	}

	public ArrayList<User> getUserList() {
		return this.uList;
	}

	public MemberList getMembers() {
		return this.members;
	}
}