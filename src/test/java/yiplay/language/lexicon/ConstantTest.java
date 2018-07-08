package yiplay.language.lexicon;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConstantTest {

	public List<Integer> analyzeText(String text) {
		List<Integer> values = new ArrayList<Integer>();
		
		text = text.toUpperCase();
		StringReader reader = new StringReader(text);
		
		Lexicon lexicon = new Lexicon(reader);
		
		try {
			int token;
			
			while ((token=lexicon.yylex())!=0) {
				values.add((Integer)token);
			}
		} catch (IOException e) {
			return null;
		}
		
		return values;
	}
	
	public void constantAssert(String instruction, int token) {
		String[] code = {instruction.toUpperCase(), instruction.toLowerCase(), instruction.toUpperCase() + "\n"};
		
		for(String temp : code) {
			int result = analyzeText(temp).get(0);
			assertEquals(token,result);
		}
		
	}
	
	@Test
	public void testConstants() {
		constantAssert("1234", TokenInfo.NUMERO_ENTERO);
		constantAssert("01234", TokenInfo.NUMERO_ENTERO);
		constantAssert("12", TokenInfo.NUMERO_ENTERO);
		constantAssert("1234h", TokenInfo.NUMERO_HEXADECIMAL);
		constantAssert("AB1234h", TokenInfo.NUMERO_HEXADECIMAL);
		constantAssert("01234h", TokenInfo.NUMERO_HEXADECIMAL);
		constantAssert("0h1234", TokenInfo.NUMERO_HEXADECIMAL);
		constantAssert("0hAB0", TokenInfo.NUMERO_HEXADECIMAL);
		constantAssert("0b10110", TokenInfo.NUMERO_BINARIO);
		constantAssert("R2", TokenInfo.REGISTRO);
		constantAssert("r2", TokenInfo.REGISTRO);
		constantAssert("R123", TokenInfo.REGISTRO);
		constantAssert("hola", TokenInfo.STRING);
		constantAssert("prueba123123123", TokenInfo.STRING);
		constantAssert("test", TokenInfo.STRING);
	}

}
