package App;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class MemberListTest {
	MemberList ml;
	ArrayList<Member> mList;
	@Before
	public void setUp() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		String fileName = "MemberFile.xml";
    		try {
    			SAXParser saxParser = spf.newSAXParser();        
			InputStream xmlInput = new FileInputStream(fileName);
			InventoryParser ixmlp = new InventoryParser();
			saxParser.parse(xmlInput, ixmlp);
			ml = ixmlp.getMembers();
    		} catch(SAXException|ParserConfigurationException|IOException e) {
    			mList = null;
    			e.printStackTrace();
    		}
	}	
	@Test
	public void testGetCount() {
		assertEquals(2, ml.getCount());
	}
	
	@Test
	public void testAddMember() {
		Member m = new Member("n", "n" ,"n", Member.level.BRONZE, 0);
		ml.addMember(m);
		assertEquals(true, ml.existsMember(m));
	}
	
	@Test
	public void testRemoveMember() {
		Member m = new Member("n", "n" ,"n", Member.level.BRONZE, 0);
		ml.removeMember(m);
		assertEquals(false, ml.existsMember(m));
	}
	
	@Test
	public void testFindMemberByNameAndAddress() {
		Member m = new Member("n", "n" ,"n", Member.level.BRONZE, 0);
		ml.addMember(m);
		assertEquals(ml.findMemberByNameAndAddress("n", "n"), m);
	}
	
	@Test
	public void testFindMemberByPhoneNumber() {
		Member m = new Member("n", "n" ,"n", Member.level.BRONZE, 0);
		ml.addMember(m);
		assertEquals(ml.findMemberByPhoneNumber("n"), m);
	}

}
