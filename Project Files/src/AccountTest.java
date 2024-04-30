import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AccountTest {

	@Test
	void getIDTest() {
		Account test1 = new Account("name", "login", 1000);
		assertEquals("name", test1.getID());
	}

	@Test
	void getTypeTest() {
		Account test2 = new Account("name", "login", 1000);
		assertEquals("login", test2.getType());
	}

	@Test
	void getTotalTest() {
		Account test3 = new Account("name", "login", 1000);
		assertEquals(1000, test3.getTotal());
	}

	@Test
	void addToTotalTest() {
		Account test4 = new Account("name", "login", 1000);
		test4.addToTotal(1000);
		assertEquals(2000, test4.getTotal());
	}

	@Test
	void removeFromTotalTest() {
		Account test5 = new Account("name", "login", 1000);
		test5.removeFromTotal(400);
		assertEquals(600, test5.getTotal());
	}

}
