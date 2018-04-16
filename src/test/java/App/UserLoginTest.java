package App;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import App.*;

public class UserLoginTest {
	UserLogin ul;
	ArrayList<User> uList;
	@Before
	public void setUp() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		String fileName = "users.xml";
    		try {
    			SAXParser saxParser = spf.newSAXParser();        
			InputStream xmlInput = new FileInputStream(fileName);
			InventoryParser ixmlp = new InventoryParser();
			saxParser.parse(xmlInput, ixmlp);
			uList = ixmlp.getUserList();
    		} catch(SAXException|ParserConfigurationException|IOException e) {
    			uList = null;
    			e.printStackTrace();
    		}
	}	
	
	@Test
	public void testExistsUserByName() {
		ul = new UserLogin(uList);
		ul.addUser(new User("Taddy Mason", "password", User.access.CASHIER));
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	@Test
	public void testCallToArms() {
		ul = new UserLogin(uList);
		String data = "yes\n" + "poser\n" + "password\n" + "password\n" + "poser\n" + "password\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.callToArms();
		assertTrue(ul.existsUserByName("poser"));
		assertTrue(ul.getUserAccess());
	}
			
	@Test
	public void testLogin() {
		ul = new UserLogin(uList);
		ul.addUser(new User("Taddy Mason", "password", User.access.CASHIER));
		String stuff = "Taddy Mason\n" +"password\n";
		System.setIn(new ByteArrayInputStream(stuff.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.login(sc);
		assertTrue(ul.getUserAccess());
	}

	@Test
	public void testNewUserSetup() {
		ul = new UserLogin(uList);
		User u = new User("Taddy Mason", "password", User.access.CASHIER);
		String j = "Taddy Mason\n" + "password\n" + "password\n";
		System.setIn(new ByteArrayInputStream(j.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.newUserSetup(sc);
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	@Test
	public void testAddOneUser() {
		ul = new UserLogin(uList);
		User u = new User("Taddy Mason", "password", User.access.CASHIER);
		ul.addUser(u);
		String j = "Taddy Mason\n" + "password\n" + "password\n";
		System.setIn(new ByteArrayInputStream(j.getBytes()));
		Scanner sc = new Scanner(System.in);
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	public void testRemoveOneUser() {
		ul = new UserLogin(uList);
		User u = new User("Taddy Mason", "password", User.access.CASHIER);
		String j = "Taddy Mason\n" + "y";
		System.setIn(new ByteArrayInputStream(j.getBytes()));
		Scanner sc = new Scanner (System.in);
		ul.removeOneUser(sc);
		assertFalse(ul.existsUserByName("Taddy Mason"));
		assertTrue(ul.existsUserByName("manager"));
	}
	
	@Test
	public void testNewUserThenLogin() {
		ul = new UserLogin(uList);
		String k = "Taddy Mason\n" + "password\n" + "password\n" + "Taddy Mason\n" + "password\n";
		System.setIn(new ByteArrayInputStream(k.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.newUserSetup(sc);
		ul.login(sc);
		assertTrue(ul.getUserAccess());
	}
	
	@Test
	public void testLimitOfCallToArms() {
		ul = new UserLogin(uList);
		String data = "yes\n" + "user\n" + "password\n" + 
					  "password\n" + "user\n" + "o\n" +
					  "o\n" + "o\n" + "o\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.callToArms();
		assertFalse(ul.getUserAccess());
	}
	
	@Test @Ignore
	public void testExitstingUserInCallToArms() {
		ul = new UserLogin(uList);
		String data = "no\n" + "user\n" + "pass\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.callToArms();
		assertTrue(ul.getUserAccess());
	}
	
}
