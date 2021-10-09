package decodeur_texte.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import decodeur_texte.Decodeur;
import decodeur_texte.ListeTrames;

public class DecodeurTest {
	private final String trame_path = "data/trames/trame_";
	
	@Test
	public void testFacile() {
		ListeTrames trames0 = testTaille("0", 3, 19, 1);
		assertEquals(trames0.getOctet(0, 1, 2), 118);
		ListeTrames trames1 = testTaille("1", 3, 19, 2);
		assertEquals(trames1.getOctet(1, 0, 1), 76);
	}
	
	@Test
	public void fichierVide() {
		testTaille("vide", 0, 0, 0);
		testTaille("vide_avec_texte", 0, 0, 0);
	}
	
	@Test
	public void testAvances() {
		testTaille("2", 5, 39, 1);
		testTaille("3", 3, 6, 1);
		assertThrows("4");
		testTaille("5", 4, 7, 3);
		assertThrows("6");
		assertThrows("7");
		testTaille("8", 2, 2, 2);
	}
	
	private ListeTrames testTaille(String fileNumber, int expectedLineSize, int expectedOctetSize, int expectedTramesSize) {
		Decodeur d = new Decodeur(trame_path + fileNumber + ".txt");
		ListeTrames trames = d.getTrames();
		System.out.println("Trame " + fileNumber + " : " + trames);
		assertEquals(expectedLineSize, trames.getNbLignes());
		assertEquals(expectedOctetSize, trames.getNbOctets());
		assertEquals(expectedTramesSize, trames.getTrames().size());
		return trames;
	}
	
	private void assertThrows(String fileNumber) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Decodeur(trame_path + fileNumber + ".txt"));
	}

}
