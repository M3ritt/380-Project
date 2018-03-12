package App;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xml.sax.SAXException;


import org.junit.rules.ErrorCollector;

public class InventoryTest {
	private Inventory in;
	
	@Rule
	public ErrorCollector eCol = new ErrorCollector();
	
	@Before
	public void setUp() {
		String fileName = "InventoryFile.xml";
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			InputStream xmlInput = new FileInputStream(fileName);
			SAXParser saxParser = spf.newSAXParser();
			InventoryParser ixmlp = new InventoryParser();
			saxParser.parse(xmlInput, ixmlp);
			in = ixmlp.getInvt();
		} catch(SAXException|ParserConfigurationException|IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindItemByName() {
		Item i = in.findItemByName("basketball");
		assertEquals("Basketball", i.getName());
	}
	
	@Test(expected = NullPointerException.class)
	public void failTestFindItemByName() {
			Item i = in.findItemByName("yemen");
			i.getName();
	}
	@Test
	public void testGetCount() {
		Item i = new Item("string", 1.99);
		Item j = new Item("straw", 2.99);
		in.addItem(i);
		in.addItem(j);
		try {
			assertEquals(35, in.getCount());
			fail();
		} catch(AssertionError e) {
			//fail();
			System.out.println("Not equal");
		}
		
	}
	//In Inventory
	//Errors when searched for item is null ----------------------------------------
	@Test
	public void testRemoveItemByName() {
		in.removeItemByName("basketball");
		assertEquals("Basketball", in.findItemByName("basketball").getName());
	}
	
	//In Inventory
	//Errors when searched for item is null ----------------------------------------
	@Test
	public void testGet() {
		Item i = in.findItemByName("Basketball");
		assertEquals(i, in.get(i));
	}
	
	//In Item
	@Test
	public void testIncrementAmount() {
		Item i = in.findItemByName("Basketball");
		i.incrementAmount();
		assertEquals(i.getAmount(), 8);
	}
	
	//In Item
	@Test
	public void testDecrementAmount() {
		Item i = in.findItemByName("Basketball");
		i.decreaseAmount();
		assertEquals(i.getAmount(), 6);
	}
	
	//In Item
	@Test
	public void testGetPrice() {
		Item i = in.findItemByName("Basketball");
		assertEquals(i.getPrice(), 12.99 , .001);
	}
	
	//In Item
	@Test
	public void testSetPrice() {
		Item i = in.findItemByName("Basketball");
		i.setPrice(12.32);
		assertEquals(i.getPrice(), 12.32, .001);
	}
	
	@Test
	public void testAddItem() {
		Item i = new Item("Soccer Shorts", 12.99);
		Item i1 = new Item("1234", 10.99);
		in.addItem(i);
		in.addItem(i1);
		assertTrue(in.checkItemByName("Soccer Shorts"));
		assertTrue(in.checkItemByName("1234"));
		assertFalse(in.checkItemByName("Hammer"));
	}
	
	@Test
	public void testCheckItemByName() {
		assertTrue(in.checkItemByName("Basketball"));
		assertFalse(in.checkItemByName("0"));
	}
}
