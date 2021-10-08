package decodeur_texte.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import decodeur_texte.HexaStringToInt;

public class HexaToIntTests {

	@Test
	public void testValeurs() {
		assertEquals(HexaStringToInt.convert("ff"), 255);
		assertEquals(HexaStringToInt.convertOctet("ff"), 255);
		assertEquals(HexaStringToInt.convertOctet("00"), 0);
		assertEquals(HexaStringToInt.convertOctet("0d"), 13);
		assertEquals(HexaStringToInt.convertOctet("De"), 222);
		assertEquals(HexaStringToInt.convert("ff9A"), 65434);
		assertEquals(HexaStringToInt.convert("8dE"), 2270);
		assertEquals(HexaStringToInt.convert('9'), 9);;
		assertEquals(HexaStringToInt.convert('a'), 10);
	}
	
	@Test
	public void testErreurs() {
		assertEquals(HexaStringToInt.convertOctet("fff"), -1); //Erreur : pas un octet
		assertEquals(HexaStringToInt.convertOctet("0"), -1);
		assertEquals(HexaStringToInt.convertOctet("fS"), -1);
		assertEquals(HexaStringToInt.convertOctet("SfS"), -1);
		assertEquals(HexaStringToInt.convert('S'), -1);;
		assertEquals(HexaStringToInt.convert(' '), -1);
		assertEquals(HexaStringToInt.convert("SfS"), -1);
		assertEquals(HexaStringToInt.convert(""), -1);
	}

}
