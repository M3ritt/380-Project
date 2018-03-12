package App;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class InventoryTest {
	private Inventory in;
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
	
	@Test
	public void testGetCount() {
		assertEquals(25, in.getCount());
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
}
