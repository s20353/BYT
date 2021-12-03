package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest
{
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception
	{
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName()
	{
		assertEquals("SEK", SEK.getName());;
	}
	
	@Test
	public void testGetRate()
	{
		assertEquals(0.15, SEK.getRate(),  0.00001);
	}
	
	@Test
	public void testSetRate()
	{
		SEK.setRate(0.2);
		assertEquals(0.2, SEK.getRate(), 0.00001);
	}
	
	
	@Test
	public void testUniversalValue()
	{
		//0 => 0
		assertEquals(0, (int)DKK.universalValue(000));
		
		//1000 => 1000*0.20=200
		assertEquals(200, (int)DKK.universalValue(1000));
		
		//100 => 10*0.20=20
		assertEquals(20, (int)DKK.universalValue(100));
		
		//10 => 1*0.20=2
		assertEquals(2, (int)DKK.universalValue(10));
	}
	
	
	@Test
	public void testValueInThisCurrency()
	{
		//0 => 0
		assertEquals(0, (int)SEK.valueInThisCurrency(000, EUR));
		
		//1000 => 1000*1.5/0.2=7500
		assertEquals(7500, (int)DKK.valueInThisCurrency(1000, EUR));
		
		//1000 => 1000*0.2/0.15=1333
		assertEquals(1333, (int)SEK.valueInThisCurrency(1000, DKK));
	}

}
