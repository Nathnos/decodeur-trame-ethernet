package decodeur_texte;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Exporte le fichier, en gardant les seules lignes valides (toutes trames confondues)
 */

public class Decodeur {
	private List<String> lines;
	private static final Character[] VALEURS_HEXA_CHAR = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 
			'A', 'B', 'C', 'D', 'E', 'F'};
	private static final List<Character> VALEURS_HEXA = Arrays.asList(VALEURS_HEXA_CHAR);
	
	public Decodeur(String path) {
		lines = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(isCorrect(line)) {
					lines.add(line);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/** Test si une ligne est valide */
	private boolean isCorrect(String line) {
		if(line.length() == 0) {
			return false;
		}
		if (! VALEURS_HEXA.contains(line.charAt(0)) ) { //Si le début de la ligne n’est pas un début d’offset hexa
			return false;
		}
		boolean space_after_offset = false;
		int cpt = 1;
		for(int i = 1 ; i < line.length() ; i++) { // Au moins trois hexa, puis que hexa jusqu’à un espace : on garde ligne
			cpt++;
			if(line.charAt(i) == ' ') {
				space_after_offset = true;
				break;
			}
			if(!VALEURS_HEXA.contains(line.charAt(i))) { //S’il y a une valeur non hexa dans l’offset, avant un espace : ligne ignorée
				return false;
			}
		}
		if (cpt <= 2) {
			return false;
		}
		if(! space_after_offset) { //Si on a parcourut toute la ligne (qui n’était qu’un offset), sans trouver d’espaces
			return false;
		}
		int compteur_octet = 1; //Pour savoir s’il est sensé y avoir un espace ou un symbole hexa. 1 ou 2 : hexa, 0 : espace
		// On continue de regarder si la ligne est valide après l’offset :
		for(int i = cpt ; i < line.length() ; i++) {
			if(compteur_octet == 1 || compteur_octet == 2) { //Doit être un symbole hexa
				if(!VALEURS_HEXA.contains(line.charAt(i))) { 
					return false;
				}
			}
			if(compteur_octet == 0) { //Doit être un espace
				if(line.charAt(i) != ' ') { 
					return false;
				}
			}
			compteur_octet = (compteur_octet + 1) % 3;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return lines.toString();
	}
	
	
	public List<String> getLines() {
		return lines;
	}
}
