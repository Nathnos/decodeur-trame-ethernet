package decodeur_texte;

import java.util.List;
/**
 * Listes de Trames, sans offset
 * Wrapper pour utiliser des listes de listes :
 * List<Integer> : une trame, où chaque Integer un octet
 * List<List<Integer>> : toutes les trames (dans l’ordre)
 */
public class ListeTrames {
	private List<List<Integer>> trames;
	
	public ListeTrames(List<List<Integer>> trames_hexa) {
		trames = trames_hexa;
	}

	public int getNbOctets() {
		int somme = 0;
		for(List<Integer> trame : trames) {
			somme += trame.size();
		}
		return somme;
	}
	
	public int getNbTrames() {
		return trames.size();
	}
	
	public List<List<Integer>> getTrames() {
		return trames;
	}
	
	public List<Integer> getTrame(int i) {
		return trames.get(i);
	}
	
	public int getOctet(int i_trame, int i_octet) {
		return trames.get(i_trame).get(i_octet);
	}
	
	@Override
	public String toString() {
		return trames.toString();
	}
}
