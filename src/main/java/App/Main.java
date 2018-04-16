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
		
		reg = new Register(invt, ml);
		Runner r = new Runner(invt, reg, ul, ml);
		r.getIntoSystem();
	}
}