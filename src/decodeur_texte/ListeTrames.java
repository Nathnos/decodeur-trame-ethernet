package decodeur_texte;

import java.util.List;
/**
 * Wrapper pour utiliser des listes de listes de listes :
 * 
 * List<String> : une ligne (chaque Integer repr�sente soit un offset soit un octet)
 * List<List<String>> : une trame
 * List<List<List<String>>> : toutes les trames
 */
public class ListeTrames {
	private List<List<List<Integer>>> trames;
	
	public ListeTrames(List<List<List<Integer>>> trames_hexa) {
		trames = trames_hexa;
	}

	public int getNbLignes() {
		int somme = 0;
		for(List<List<Integer>> trame : trames) {
			somme += trame.size();
		}
		return somme;
	}
	
	public int getNbOctets() {
		int somme = 0;
		for(List<List<Integer>> trame : trames) {
			for(List<Integer> ligne : trame) {
				somme += ligne.size();
			}
		}
		return somme;
	}
	
	public List<List<List<Integer>>> getTrames() {
		return trames;
	}
	
	public List<List<Integer>> getTrame(int i) {
		return trames.get(i);
	}
	
	public List<Integer> getLigne(int i_trame, int i_ligne) {
		return trames.get(i_trame).get(i_ligne);
	}
	
	public int getOctet(int i_trame, int i_ligne, int i_octet) {
		return trames.get(i_trame).get(i_ligne).get(i_octet);
	}
	
	@Override
	public String toString() {
		return trames.toString();
	}
}