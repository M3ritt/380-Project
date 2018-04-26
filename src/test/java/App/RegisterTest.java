package App;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.xml.sax.SAXException;

public class RegisterTest {
	private Inventory in;
	private MemberList ml;
	@Before
	public void setUp() {
		String fileName = "InventoryFile.xml";
		SAXParserFactory spf = SAXParserFactory.newInstance();
		String fileName2 = "MemberFile.xml";
		try {
			InputStream xmlInput = new FileInputStream(fileName);
			SAXParser saxParser = spf.newSAXParser();
			InventoryParser ixmlp = new InventoryParser();
			saxParser.parse(xmlInput, ixmlp);
			in = ixmlp.getInvt();
			
			InputStream xmlInput2 = new FileInputStream(fileName2);
			SAXParser saxParser2 = spf.newSAXParser();
			InventoryParser ixmlp2 = new InventoryParser();
			saxParser2.parse(xmlInput2, ixmlp2);
			ml = ixmlp.getMembers();
		} catch(SAXException|ParserConfigurationException|IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddItem() {
		Register r = new Register(in, ml);
		r.addItem("Peach", 12.00, "peach company");
		assertTrue(in.findItemByName("Peach", "peach company").getName().equals("Peach"));
	}
	
	@Test
	public void testItemReturn() {
		Register r = new Register(in, ml);
		r.itemReturn("Basketball", "Wilson");
		assertEquals("Basketball", in.findItemByName("basketball", "wilson").getName());
	}
	
	@Test
	public void testAddItemWithRemoveItem() {
		Register r = new Register(in, ml);
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		in.removeItemByName("Sour Patch Kids", "Allen Candy Company");
		assertEquals("Sour Patch Kids", in.findItemByName("Sour Patch Kids", "Allen Candy Company").getName());
	}
	
	@Test
	public void testChangeItemPrice() {
		Register r = new Register(in, ml);
		r.changeItemPrice("Basketball", 19.99, "wilson");
		assertEquals(19.99, in.findItemByName("Basketball", "wilson").getPrice(), .01);
	}
	/*
	@Ignore @Test
	public void testDailyInventory() {
		Register r = new Register(in, ml);
		Item temp1 = new Item("basketball", 15, "wilson");
		Item temp2 = new Item("soccerball", 15, "wilson");
		in.soldList.add(temp1);
		in.soldList.add(temp2);
		assertTrue(r.dailyInventory().contains("basketball"));
		assertTrue(r.dailyInventory().contains("soccerball"));
		
	}
	*/
	@Test
	public void testEndDay() {
		Register r = new Register(in, ml);
		r.endDay();
		assertEquals("MONDAY", r.getCurrentDay());
			
	}
	
	@Test
	public void testCheckLowInventory() {		
		Register r = new Register(in, ml);
		r.addItem("Curling Broom", 59.99, "Curlers'r'us");
		r.addItem("Curling Broom", 59.99, "Curlers'r'us");
		assertTrue(r.checkLowInventory("curling broom", "curlers'r'us"));
			
	}
}
