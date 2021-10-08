package analyseur;

/**
 * Classe outil pour convertir un octet hexad�cimal (String deux caract�res valides) en int
 * @author villa
 *
 */
public class HexaStringToInt {
	private HexaStringToInt() {} //Classe statique
	
	public static int convertOctet(String hexa) {
		if(hexa.length() != 2) { // Deux symboles hexa pour un octet
			System.err.println("N�est pas un octet hexad�cimal : " + hexa);
			return -1;
		}
		try {
			return Integer.parseInt(hexa, 16);
		} catch (NumberFormatException e) {
			System.err.println("N�est pas un octet hexad�cimal : " + hexa);
			return -1;
		}
	}
	
	public static int convert(String hexa) {
		try {
			return Integer.parseInt(hexa, 16);
		} catch (NumberFormatException e) {
			System.err.println("N�est pas un nombre hexad�cimal : " + hexa);
			return -1;
		}
	}
	
	public static int convert(char hexa) {
		try {
			return Integer.parseInt(String.valueOf(hexa), 16);
		} catch (NumberFormatException e) {
			System.err.println("N�est pas un nombre hexad�cimal : " + hexa);
			return -1;
		}
	}
}
