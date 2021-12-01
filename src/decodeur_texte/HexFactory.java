package decodeur_texte;

/**
 * Classe outil pour convertir un octet hexad�cimal (String deux caract�res valides) en int
 * @author villa
 *
 */
public class HexFactory {
	private HexFactory() {} //Classe statique
	
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
	
	public static int split_hex_first(int val) {
		return Integer.parseInt("" + Integer.toHexString(val).charAt(0));
	}
	
	public static int split_hex_second(int val) {
		return Integer.parseInt("" + Integer.toHexString(val).charAt(1));
	}
	
	public static String split_binary_8(Integer val) {
		String bin = Integer.toBinaryString(val);
		while(bin.length() < 8) {
			bin = "0" + bin;
		}
		return bin;
	}
	
	public static String split_binary_16(Integer val) {
		String bin = Integer.toBinaryString(val);
		while(bin.length() < 16) {
			bin = "0" + bin;
		}
		return bin;
	}
	
	/*
	 * Merge 2 Bytes (Integer must be between 0 and 255
	 */
	public static int merge2(Integer val1, Integer val2) {
		String firstHex = "";
		String secondHex = "";
		//1st hex
		if(val1 < 16) {
			firstHex += "0";
		}
		firstHex += Integer.toHexString(val1);
		//2nd hex
		if(val2 < 16) {
			secondHex += "0";
		}
		secondHex += Integer.toHexString(val2);
		return convert(firstHex + secondHex);
	}
	
	public static int merge4(Integer val1, Integer val2, Integer val3, Integer val4) {
		int val10 = merge2(val1, val2);
		int val20 = merge2(val3, val4);
		return merge2(val10, val20);
	}
	public static String converTo2Octet(int value){
		StringBuffer res = new StringBuffer("");
		if (value < 16)
			res.append("0");
		res.append(Integer.toHexString(value));
		return res.toString();
	}
}
