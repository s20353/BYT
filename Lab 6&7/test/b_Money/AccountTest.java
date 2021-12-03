package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest
{
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception
	{
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment()
	{
		String x = "whatever";
		
		//empty
		assertFalse(testAccount.timedPaymentExists(x));
		
		//add
		testAccount.addTimedPayment(x, 10, 10, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists(x));
		
		//remove
		testAccount.removeTimedPayment(x);
		assertFalse(testAccount.timedPaymentExists(x));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException
	{
		testAccount.addTimedPayment("whatever", 10, 0, new Money(2000000, SEK), SweBank, "Alice");
		for (int i = 0; i < 50; i++)
		{
			testAccount.tick();
		}

		assertEquals(0, (int)testAccount.getBalance().getAmount());
	}

	@Test
	public void testAddWithdraw()
	{
		//some
		testAccount.deposit(new Money(1000, SEK));
		assertEquals(10001000, (int)testAccount.getBalance().getAmount());
		testAccount.withdraw(new Money(1000, SEK));
		assertEquals(10000000, (int)testAccount.getBalance().getAmount());

		//all
		testAccount.withdraw(new Money(10000000, SEK));
		assertEquals(0, (int)testAccount.getBalance().getAmount());
		
		//EUR => SEK
		testAccount.deposit(new Money(10000000, new Currency("EUR", 0.20)));
		assertEquals(13333333, (int)testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance()
	{
		assertTrue(new Money(10000000, SEK).equals(testAccount.getBalance()));
	}
}
