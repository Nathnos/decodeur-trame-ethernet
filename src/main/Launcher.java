package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import decodeur_texte.Decodeur;
import decodeur_texte.ListeTrames;

public class Launcher {

	public static void main(String[] args) throws FileNotFoundException {
		String path = args[0];
		ListeTrames trames = Decodeur.getListTrames(path);
		System.out.println("Fichier chargé : " + path);
		System.out.println(trames);
		//lancer_analyse(trames);
//		PrintWriter out = new PrintWriter("retour.txt");
		PrintWriter out = new PrintWriter("C:\\Users\\villa\\Desktop\\retour.txt");
		out.println(trames);
		out.close();
	}

}
