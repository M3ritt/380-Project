package App;

import static org.junit.Assert.*;

import org.junit.Test;

public class MemberTest {
	private Member m = new Member("Josh", "123 Street", "123-456-7890", Member.level.BRONZE, 00);

	@Test
	public void testSetName() {
		m.setName("Henry");
		assertEquals("Henry",m.getName());
	}
	
	@Test
	public void testSetAddress() {
		m.setAddress("1234 Street");
		assertEquals("1234 Street", m.getAddress());
	}
	
	@Test
	public void testSetPhoneNumber() {
		m.setPhoneNumber("1234567891");
		assertEquals("123-456-7891", m.getPhoneNumber());
	}
	
	@Test
	public void testSetState() {
		m.setState(Member.level.BRONZE);
		assertEquals(Member.level.BRONZE, m.getLevelOfMembership());
	}
	
	@Test
	public void testGetAmountSpent() {
		m.addToSales(100);
		assertEquals(100, m.getAmountSpent(), .001);
	}
	
	@Test
	public void testGetDiscount() {
		assertEquals(.01, m.getDiscount(), .001);
		m.setState(Member.level.SILVER);
		assertEquals(.02, m.getDiscount(), .001);
		m.setState(Member.level.GOLD);
		assertEquals(.03, m.getDiscount(), .001);
	}

}
