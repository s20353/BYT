package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest
{
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception
	{
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName()
	{
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency()
	{
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException
	{
		assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Ulrika"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException
	{
		SweBank.deposit("Bob", new Money(1000, SEK));
		assertEquals(1000, (int)SweBank.getBalance("Bob"));

		assertThrows(AccountDoesNotExistException.class, () -> SweBank.deposit("x", new Money(1000, SEK)));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException
	{
		SweBank.withdraw("Bob", new Money(100, SEK));
		assertEquals(-100, (int)SweBank.getBalance("Bob"));
		
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.deposit("x", new Money(1000, SEK)));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException
	{
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.getBalance("x"));
		assertEquals(0, (int)SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException
	{
		//not exists
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.transfer("boo", SweBank, "Bob", new Money(1000, SEK)));
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.transfer("Bob", SweBank, "boo", new Money(1000, SEK)));
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.transfer("boo", SweBank, "boo", new Money(1000, SEK)));
		//"boo" - as a ghost's line, because ghosts don't exist

		//same account
		SweBank.deposit("Bob", new Money(10000, SEK));
		SweBank.transfer("Bob", SweBank, "Bob", new Money(10000, SEK));
		assertEquals(10000, (int)SweBank.getBalance("Bob"));

		//same bank different account
		SweBank.transfer("Bob", "Ulrika", new Money(1000, SEK));
		assertEquals(9000, (int)SweBank.getBalance("Bob"));
		assertEquals(1000, (int)SweBank.getBalance("Ulrika"));

		//different bank
		SweBank.transfer("Bob", Nordea, "Bob", new Money(1000, SEK));
		assertEquals(8000, (int)SweBank.getBalance("Bob"));
		assertEquals(1000, (int)Nordea.getBalance("Bob"));

		//different currency
		SweBank.transfer("Bob", DanskeBank, "Gertrud", new Money(1000, SEK));
		assertEquals(7000, (int)SweBank.getBalance("Bob"));
		assertEquals(750, (int)DanskeBank.getBalance("Gertrud"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException
	{
		//not exist
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.addTimedPayment("Bob", "whatever", 10, 50, new Money(1000, SEK), SweBank, "boo"));
		
		//not existx2
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.addTimedPayment("1", "whatever", 10, 50, new Money(1000, SEK), SweBank, "2"));

		assertThrows(AccountDoesNotExistException.class, () -> SweBank.addTimedPayment("boo", "whatever", 10, 50, new Money(1000, SEK), SweBank, "Ulrika"));
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.removeTimedPayment("boo", "whatever"));
	}
}
