package decodeur_texte.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import decodeur_texte.HexFactory;

public class HexaToIntTests {

	@Test
	public void testValeurs() {
		assertEquals(HexFactory.convert("ff"), 255);
		assertEquals(HexFactory.convertOctet("ff"), 255);
		assertEquals(HexFactory.convertOctet("00"), 0);
		assertEquals(HexFactory.convertOctet("0d"), 13);
		assertEquals(HexFactory.convertOctet("De"), 222);
		assertEquals(HexFactory.convert("ff9A"), 65434);
		assertEquals(HexFactory.convert("8dE"), 2270);
		assertEquals(HexFactory.convert('9'), 9);;
		assertEquals(HexFactory.convert('a'), 10);
	}
	
	@Test
	public void testErreurs() {
		assertEquals(HexFactory.convertOctet("fff"), -1); //Erreur : pas un octet
		assertEquals(HexFactory.convertOctet("0"), -1);
		assertEquals(HexFactory.convertOctet("fS"), -1);
		assertEquals(HexFactory.convertOctet("SfS"), -1);
		assertEquals(HexFactory.convert('S'), -1);;
		assertEquals(HexFactory.convert(' '), -1);
		assertEquals(HexFactory.convert("SfS"), -1);
		assertEquals(HexFactory.convert(""), -1);
	}

}
