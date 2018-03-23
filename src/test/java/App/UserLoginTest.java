package App;

import static org.junit.Assert.*;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import org.junit.Ignore;
import org.junit.Test;
import App.*;

public class UserLoginTest {
	UserLogin ul;
	
	@Test
	public void testExistsUserByName() {
		ul = new UserLogin();
		ul.addUser(new User("Taddy Mason", "password"));
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	@Test
	public void testCallToArms() {
		ul = new UserLogin();
		String data = "yes\n" + "user\n" + "password\n" + "password\n" + "user\n" + "password\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.callToArms();
		assertTrue(ul.existsUserByName("user"));
		assertTrue(ul.getUserAccess());
	}
			
	@Test
	public void testLogin() {
		ul = new UserLogin();
		ul.addUser(new User("Taddy Mason", "password"));
		String stuff = "Taddy Mason\n" +"password\n";
		System.setIn(new ByteArrayInputStream(stuff.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.login(sc);
		assertTrue(ul.getUserAccess());
	}

	@Test
	public void testNewUserSetup() {
		ul = new UserLogin();
		User u = new User("Taddy Mason", "password");
		String j = "Taddy Mason\n" + "password\n" + "password\n";
		System.setIn(new ByteArrayInputStream(j.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.newUserSetup(sc);
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	@Test
	public void testNewUserThenLogin() {
		ul = new UserLogin();
		String k = "Taddy Mason\n" + "password\n" + "password\n" + "Taddy Mason\n" + "password\n";
		System.setIn(new ByteArrayInputStream(k.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.newUserSetup(sc);
		ul.login(sc);
		assertTrue(ul.getUserAccess());
	}
	
	@Test
	public void testLimitOfCallToArms() {
		ul = new UserLogin();
		String data = "yes\n" + "user\n" + "password\n" + 
					  "password\n" + "user\n" + "o\n" +
					  "o\n" + "o\n" + "o\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner sc = new Scanner(System.in);
		ul.callToArms();
		assertFalse(ul.getUserAccess());
	}
	
}
