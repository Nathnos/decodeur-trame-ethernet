package decodeur_texte.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import decodeur_texte.Decodeur;

class DecodeurTest {
	private final String trame_path = "data/trames/trame_";

	@Test
	void testTailleTrivial() {
		testTaille("0", 3);
	}
	
	@Test
	void testTailleTexteEntrelace() {
		testTaille("0", 3);
		testTaille("2", 3); //3 lignes valides, 3 non valides
		testTaille("3", 3);
		testTaille("4", 2); //2 lignes valides (si pas d’espace après l’offset, annulé !)
		testTaille("5", 4); //2 lignes valides (si pas d’espace après l’offset, annulé !)
	}
	
	private void testTaille(String fileNumber, int expectedSize) {
		Decodeur d = new Decodeur(trame_path + fileNumber + ".txt");
		System.out.println(d);
		assertEquals(expectedSize, d.getLines().size());
	}

}
