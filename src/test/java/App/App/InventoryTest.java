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
		Item i = in.findItemByName("Basketball");
		assertEquals("Basketball", i.getName());
	}
	
	@Test
	public void testGetCount() {
		assertEquals(25, in.getCount());
	}
}
