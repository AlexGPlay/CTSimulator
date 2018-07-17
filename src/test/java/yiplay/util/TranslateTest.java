package yiplay.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TranslateTest {

	@Test
	public void testBinaryString() {
		assertEquals( "00000000" , Translate.toBinaryString((short) 0, 8) );
		assertEquals( "00000001" , Translate.toBinaryString((short) 1, 8) );
		assertEquals( "00000010" , Translate.toBinaryString((short) 2, 8) );
		assertEquals( "00000011" , Translate.toBinaryString((short) 3, 8) );
		assertEquals( "00000100" , Translate.toBinaryString((short) 4, 8) );
		assertEquals( "11111111" , Translate.toBinaryString((short) -1, 8) );
		assertEquals( "11111110" , Translate.toBinaryString((short) -2, 8) );
		assertEquals( "11111101" , Translate.toBinaryString((short) -3, 8) );
		assertEquals( "11111100" , Translate.toBinaryString((short) -4, 8) );
	}
	
	@Test
	public void testBinaryStringRadix16() {
		assertEquals( "00000000" , Translate.toBinaryString("0", 16, 8) );
		assertEquals( "00000001" , Translate.toBinaryString("1", 16, 8) );
		assertEquals( "00000010" , Translate.toBinaryString("2", 16, 8) );
		assertEquals( "00000011" , Translate.toBinaryString("3", 16, 8) );
		assertEquals( "00000100" , Translate.toBinaryString("4", 16, 8) );
		assertEquals( "00001010" , Translate.toBinaryString("A", 16, 8) );
		assertEquals( "00001111" , Translate.toBinaryString("F", 16, 8) );
		assertEquals( "11111111" , Translate.toBinaryString("FF", 16, 8) );
	}
	
	@Test
	public void testDecimalInteger() {
		assertEquals( 0 , Translate.toDecimalInteger("00000000") );
		assertEquals( 1 , Translate.toDecimalInteger("00000001") );
		assertEquals( 2 , Translate.toDecimalInteger("00000010") );
		assertEquals( 3 , Translate.toDecimalInteger("00000011") );
		assertEquals( 4 , Translate.toDecimalInteger("00000100") );
		assertEquals( -1 , Translate.toDecimalInteger("11111111") );
		assertEquals( -2 , Translate.toDecimalInteger("11111110") );
		assertEquals( -3 , Translate.toDecimalInteger("11111101") );
		assertEquals( -4 , Translate.toDecimalInteger("11111100") );
	}
	
	@Test
	public void testDecimalIntegerRegister() {
		assertEquals( 0 , Translate.toDecimalInteger("000",true) );
		assertEquals( 1 , Translate.toDecimalInteger("001",true) );
		assertEquals( 2 , Translate.toDecimalInteger("010",true) );
		assertEquals( 3 , Translate.toDecimalInteger("0011",true) );
		assertEquals( 4 , Translate.toDecimalInteger("100",true) );
		assertEquals( 5 , Translate.toDecimalInteger("101",true) );
		assertEquals( 6 , Translate.toDecimalInteger("110",true) );
		assertEquals( 7 , Translate.toDecimalInteger("111",true) );
	}
	
	@Test
	public void testMemoryPosition() {
		assertEquals( 0 , Translate.toMemoryPosition("0000000000000000") );
		assertEquals( 1 , Translate.toMemoryPosition("0000000000000001") );
		assertEquals( 2 , Translate.toMemoryPosition("0000000000000010") );
		assertEquals( 65535 , Translate.toMemoryPosition("1111111111111111") );
	}

}
