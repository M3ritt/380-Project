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
import org.xml.sax.SAXException;

public class RegisterTest {
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
	public void testAddItem() {
		Register r = new Register(in);
		r.addItem("Peach", 12.00, "peach company");
		assertTrue(in.findItemByName("Peach", "peach company").getName().equals("Peach"));
	}
	
	@Test
	public void testItemReturn() {
		Register r = new Register(in);
		r.itemReturn("Basketball", "Wilson");
		assertEquals("Basketball", in.findItemByName("basketball", "wilson").getName());
	}
	
	@Test
	public void testAddItemWithRemoveItem() {
		Register r = new Register(in);
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		r.addItem("Sour Patch Kids", 4.99, "Allen Candy Company");
		in.removeItemByName("Sour Patch Kids", "Allen Candy Company");
		assertEquals("Sour Patch Kids", in.findItemByName("Sour Patch Kids", "Allen Candy Company").getName());
	}
	
	@Test
	public void testChangeItemPrice() {
		Register r = new Register(in);
		r.changeItemPrice("Basketball", 19.99, "wilson");
		assertEquals(19.99, in.findItemByName("Basketball", "wilson").getPrice(), .01);
	}
}
