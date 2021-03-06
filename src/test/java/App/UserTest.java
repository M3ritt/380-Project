package App;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	private User u;
	
	@Test
	public void testSetUserPassword() {
		User u = new User("Josh", "", User.access.CASHIER);
		u.setPassword("Password321");
		assertEquals("Password321", u.getPassword());
	}
	
	@Test
	public void testGetUserPassword() {
		User u = new User("Josh", "Bet", User.access.CASHIER);
		assertEquals(u.getPassword(), "Bet");
	}
	
	@Test
	public void testUserSale() {
		u = new User("Jerry", "1234", User.access.CASHIER);
		u.userSale(20.00);
		u.userSale(30.50);
		assertEquals(50.50, u.getSalesTotal(), 0.001);
	}
	
	@Test
	public void testUserSaleTwo() {
		u = new User("Jerry", "1234", User.access.CASHIER);
		u.userSale(20.00);
		u.userSale(-30.50);
		try {
			assertEquals(-15.50, u.getSalesTotal(), 0.001);
			fail("need to catch negative numbers");
		} catch(AssertionError a) {
			assertEquals(-10.50, u.getSalesTotal(), 0.001);
		}
		
	}
	
	@Test
	public void testGetUserName() {
		User u = new User("Jon", "a", User.access.CASHIER);
		User u1 = new User("1234", "a", User.access.CASHIER);
		User u2 = new User("Jon Doe", "a", User.access.CASHIER);
		
		assertEquals("Jon", u.getUserName());
		assertEquals("1234", u1.getUserName());
		assertEquals("Jon Doe", u2.getUserName());
		
	}
	@Test
	public void testUserTotal() {
		User u = new User("J", "a", 0.00, User.access.CASHIER, 0);
		u.addLoginTime();
		u.userSale(2.99);
		assertEquals(2.99, u.showSalesTotal(), 0);
	}
}
