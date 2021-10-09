package decodeur_texte;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Exporte le fichier, en gardant les seules lignes valides (toutes trames confondues)
 * Sépare les différentes trames, filtres les fins de ligne, détecte les lignes invalides et …
 * Decoupe les lignes en liste d’octets et sépare les différentes trames
 * Filtre les octets en trop sur une ligne et les range dans des listes d’Integer
 */

//TODO plus que decoupe_en_trames à faire, plus changer tests.

public class Decodeur {
	private List<Line> lines;
	private List<List<List<Integer>>> trames_hexa;
	private ListeTrames trames;
	/* List<String> : une ligne (chaque Integer représente soit un offset soit un octet)
	 * List<List<String>> : une trame
	 * List<List<List<String>>> : toutes les trames */
	private static final Character[] VALEURS_HEXA_CHAR = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 
			'A', 'B', 'C', 'D', 'E', 'F'};
	private static final List<Character> VALEURS_HEXA = Arrays.asList(VALEURS_HEXA_CHAR);
	
	public Decodeur(String path) {
		lines = new ArrayList<Line>();
		try {
			Scanner scanner = new Scanner(new File(path));
			int i = 0;
			while (scanner.hasNextLine()) {
				String contenu = scanner.nextLine();
				if(offsetValide(contenu)) {
					lines.add(new Line(i, contenu));
				}
				i++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<List<Line>> trames_non_decoupee = decoupe_en_trames(lines);
//		System.out.println(trames_non_decoupee);
		trames_hexa = new ArrayList<List<List<Integer>>>();
		for(List<Line> trame_non_decoupee : trames_non_decoupee) { 
			// Pour chaque trame, on réduit (enlève les fin de lignes, détecte lignes invalide ou les octets en trop…)
			trames_hexa.add(decouperEtReduire(trame_non_decoupee));
		}
		trames = new ListeTrames(trames_hexa); //Enveloppe dans une belle classe !
	}
	
	/** Test si une ligne possède un offset valide */
	private boolean offsetValide(String line) {
		if(line.length() == 0) { //Ligne vide
			return false;
		}
		for(int i = 0 ; i < line.length() ; i++) { // Au moins trois hexa, puis que hexa jusqu’à un espace : on garde ligne
			if(line.charAt(i) == ' ') {
				return i >= 2; //Si i> 2, c’est qu’on a déjà vu passer des caractères hexa d’indice 0, 1 et 2, donc plus de 2 symboles hexa
			}
			if(!VALEURS_HEXA.contains(line.charAt(i))) { //S’il y a une valeur non hexa dans l’offset, avant un espace : ligne ignorée
				return false;
			}
		}
		return false; //Si on a parcourut toute la ligne (qui n’était qu’un offset), sans trouver d’espaces
	}
	
	private List<List<Line>> decoupe_en_trames(List<Line> lines) {
		List<List<Line>> trames_non_decoupee = new ArrayList<List<Line>>();
		if(lines.size() == 0) {
			return trames_non_decoupee;
		}
		if(getOffset(lines.get(0)) != 0) { // PREMIER OFFSET À 0, SINON INVALIDE
			throw new IllegalArgumentException("Ligne invalide (premier offset pas à 0) : ligne " + lines.get(0).getLine());
		}
		List<Line> trame_actuelle = new ArrayList<Line>();
		trame_actuelle.add(lines.get(0));
		int offsetPrecedent = 0;
		for(int i = 1 ; i< lines.size() ; i++) {
			Line ligne = lines.get(i);
			int offset = getOffset(ligne);
			if(offset == 0) { //Nouvelle trame
				trames_non_decoupee.add(trame_actuelle); //On ajoute la trame précédente
				trame_actuelle = new ArrayList<Line>();
			}
			else if(offset < offsetPrecedent) { //Offset non nul qui a diminué ! Erreur !
				throw new IllegalArgumentException("Ligne invalide (offset qui se décrémente) : ligne " + ligne.getLine());
			}
			trame_actuelle.add(ligne); //Dans tous les cas, on ajoute la ligne à la trame actuelle
			offsetPrecedent = offset;
		}
		trames_non_decoupee.add(trame_actuelle); //On ajoute la dernière trame
		return trames_non_decoupee;
	}
	
	private List<List<Integer>> decouperEtReduire(List<Line> lines) { // Réduit les lignes d’une trame
		List<List<Integer>> trame = new ArrayList<List<Integer>>();
		// On suppose que chaque ligne commence par un offset valide (les non valides ont été éliminés)
		for(int i = 0 ; i < lines.size() - 1 ; i++) {
			List<Integer> ligne = new ArrayList<Integer>();
			Line line = lines.get(i);
			Line nextLine = lines.get(i+1);
			int offset = getOffset(line);
			int nextOffset = getOffset(nextLine);
			int nbOctets = nextOffset - offset;
			String[] valeurs = line.getData().split(" ");
			int j = 0;
			try {
				for(j = 0 ; j < nbOctets ; j++) {
					String v = valeurs[j+1];// i+1 pour ignorer l’offset
					// Ça doit être un octet, donc exactement 2 symboles hexa
					if(v.length() != 2 || !VALEURS_HEXA.contains(v.charAt(0)) || !VALEURS_HEXA.contains(v.charAt(1))) { 
						// Si on a eu une fin de ligne (texte) sans avoir assez d’octets
						throw new IllegalArgumentException("Ligne invalide (pas assez d’octets) : ligne " + line.getLine());
					}
					ligne.add(HexaStringToInt.convertOctet(v));
				}
			} catch (ArrayIndexOutOfBoundsException e) { //Si on s’est arrêté alors qu’on avait pas assez d’octets
				throw new IllegalArgumentException("Ligne invalide (pas assez d’octets) : ligne " + line.getLine());
			}
			trame.add(ligne);
		}
		//Enfin, on traite la dernière ligne de la trame (ajoute tout tant que c’est des octets de hexa)
		Line line = lines.get(lines.size() - 1);
		List<Integer> last_line = new ArrayList<Integer>();
		String[] valeurs = line.getData().split(" ");
		for(int i = 1 ; i < valeurs.length ; i++) { // i = 1 pour ignorer l’offset
			String v = valeurs[i];
			if(v.length() != 2 || !VALEURS_HEXA.contains(v.charAt(0)) || !VALEURS_HEXA.contains(v.charAt(1))) { 
				break; //fin de ligne
			}
			last_line.add(HexaStringToInt.convertOctet(v));
		}
		trame.add(last_line);
		return trame;
	}
	
	private int getOffset(Line line) {
		return HexaStringToInt.convert(line.getData().split(" ")[0]);
	}
	
	@Override
	public String toString() {
		return lines.toString();
	}
	
	public ListeTrames getTrames() {
		return trames;
	}
	
	public static ListeTrames getListTrames(String path) {
		Decodeur d = new Decodeur(path);
		return d.getTrames();
	}
}
