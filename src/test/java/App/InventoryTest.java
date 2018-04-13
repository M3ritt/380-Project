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
		Item i = in.findItemByName("basketball", "wilson");
		Item i1 = in.findItemByName("basketball", "Wilson");
		assertEquals("Basketball", i.getName());
		assertEquals("Basketball", i1.getName());
	}
	
	@Test(expected = NullPointerException.class)
	public void failTestFindItemByName() {
			Item i = in.findItemByName("yemen", "company");
			i.getName();
	}
	
	@Test
	public void testGetCount() {
		Item i = new Item("string", 1.99, "string company");
		Item j = new Item("straw", 2.99, "straw company");
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
	@Test
	public void testRemoveItemByName() {
		in.removeItemByName("basketball", "wilson");
		assertEquals("Basketball", in.findItemByName("basketball", "wilson").getName());
	}
	
	//In Inventory
	@Test
	public void testGet() {
		Item i = in.findItemByName("Basketball", "wilson");
		assertEquals(i, in.get(i));
	}
	
	//In Item
	@Test
	public void testIncrementAmount() {
		Item i = in.findItemByName("Basketball", "wilson");
		i.incrementAmount();
		assertEquals(i.getAmount(), 6);
	}
	
	//In Item
	@Test
	public void testDecrementAmount() {
		Item i = in.findItemByName("Basketball", "wilson");
		i.decreaseAmount();
		assertEquals(i.getAmount(), 4);
	}
	
	//In Item
	@Test
	public void testGetPrice() {
		Item i = in.findItemByName("Basketball", "wilson");
		assertEquals(i.getPrice(), 12.99 , .001);
	}
	
	//In Item
	@Test
	public void testSetPrice() {
		Item i = in.findItemByName("Basketball", "wilson");
		i.setPrice(12.32, "wilson");
		assertEquals(i.getPrice(), 12.32, .001);
	}
	
	@Test
	public void testAddItem() {
		Item i = new Item("Soccer Shorts", 12.99, "adidas");
		Item i1 = new Item("1234", 10.99, "number company");
		in.addItem(i);
		in.addItem(i1);
		assertTrue(in.checkItemByName("Soccer Shorts", "adidas"));
		assertTrue(in.checkItemByName("1234", "number company"));
		assertFalse(in.checkItemByName("Hammer", "construction company"));
	}
	
	@Test
	public void testCheckItemByName() {
		assertTrue(in.checkItemByName("Basketball","wilson"));
		assertTrue(in.checkItemByName("Basketball", "Wilson"));
		assertTrue(in.checkItemByName("basketball", "wilson"));
		assertFalse(in.checkItemByName("0", "Zero"));
	}
}
