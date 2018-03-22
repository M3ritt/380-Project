package App;

import static org.junit.Assert.*;
import java.util.Scanner;

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
	
	
	@Test @Ignore
	public void testLogin() {
		ul = new UserLogin();
		ul.addUser(new User("Taddy Mason", "password"));
//		ul.login("Taddy Mason", "password");
		ul.login();
		assertTrue(ul.getUserAccess());
	}

	@Test @Ignore
	public void testNewUserSetup() {
		ul = new UserLogin();
		User u = new User("Taddy Mason", "password");
//		ul.newUserSetup("Taddy Mason", "password", "password");
		ul.newUserSetup();
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	@Test @Ignore
	public void testNewUserThenLogin() {
		ul = new UserLogin();
//		ul.newUserSetup("Taddy Mason", "password", "password");
//		ul.login("Taddy Mason", "password");
		ul.newUserSetup();
		ul.login();
		assertTrue(ul.getUserAccess());
	}
	
}
