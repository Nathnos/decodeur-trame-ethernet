import decodeur_texte.Decodeur;
import decodeur_texte.ListeTrames;

public class Launcher {

	public static void main(String[] args) {
		String path = args[1];
		System.out.println("Fichier chargé : " + path);
		ListeTrames trames = Decodeur.getListTrames(path);
		//lancer_analyse(trames);
	}

}
