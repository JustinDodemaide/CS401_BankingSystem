package bankingTests;

import static org.junit.Assert.*;

import org.junit.Test;

import bankingPackage.Account;

public class AccountTests {

	@Test
	public void getIDTest() {
		Account accountTest = new Account("1234", "checkings", 100);
		assertEquals("1234", accountTest.getID());
	}

	@Test
	public void getTypeTest() {
		Account accountTest = new Account("1234", "checkings", 100);
		assertEquals("checkings", accountTest.getType());
	}
	
	@Test
	public void getTotalTest() {
		Account accountTest = new Account("1234", "checkings", 100);
		assertEquals(100.00, accountTest.getTotal(), 0.001);
	}
	
}
