import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UserTests {

	@Test
	public void getUsernameTest() {
		ArrayList<String> accountIDs = new ArrayList<String>();
		User userTest = new User("test", "1111", accountIDs);
		assertEquals("test", userTest.getUsername());
	}

	@Test
	public void getPINTest() {
		ArrayList<String> accountIDs = new ArrayList<String>();
		User userTest = new User("test", "1111", accountIDs);
		assertEquals("1111", userTest.getPIN());
	}
	
	@Test
	public void getAccountsTest() {
		ArrayList<String> accountIDs = new ArrayList<String>();
		User userTest = new User("test", "1111", accountIDs);
		assertEquals(accountIDs, userTest.getAccounts());
	}
}
