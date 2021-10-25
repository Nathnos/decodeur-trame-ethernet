package Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe outil pour analyser la trame sur la couche IP
 * @author Thien
 *
 */
public class IpLayer implements Frame {
	private List <Integer> frame;
	public IpLayer(List <Integer> frame) {
		this.frame =frame;
	}
	@Override
	/*
	 * Retourner les 20 primeres octets
	 */
	public List<Integer> getHeader() {
		List <Integer> res = new ArrayList<Integer>();
		for (int i = 0 ;i < getHeaderLength(); i++) {
			res.add(frame.get(i));
		}
		return res;
	}
	/*
	 * Retourner les restant de trame
	 */
	@Override
	public List<Integer> getData() {
		List <Integer> res = new ArrayList<Integer>();
		for (int i = getHeaderLength() ;i < frame.size(); i++) {
			res.add(frame.get(i));
		}
		return res;
	}
	/*
	 * Retourner le type de IPv4 ou IPv6
	 * IPv4 si 4 premiers bit = 0100 => en dec 4
	 * from range 40 to 4E in Heximal => from 64 to 78 in Decimal
	 */
	public boolean isIPv4() {
		if ((this.frame.get(0)<=78) && (this.frame.get(0)>=64)){
			return true;
		}
		return false;
	}
	public String getVersion() {
		if (this.isIPv4()) {
			return "4";
		}
		return "6";
	}
	/*
	 * Retourner la taille d'entete
	 * elle a 4 bits de 5eme à 8eme dans la trame
	 */
	public int getHeaderLength() {
		String firstOctet = Integer.toHexString(frame.get(0));
		int length = Integer.parseInt(String.valueOf(firstOctet.charAt(1)),16);
		return length*4;	
		
	}
	/*
	 * Retourner Differentiated Services Code qui sititue a 2eme octet
	 * 
	 */
	public String getDSCP() {
		
		return ("0x"+ Integer.toHexString(this.getHeader().get(1)));
	}
	/*
	 * Retourner la taille de la trame
	 * sititue a 16eme bits a 32eme bits
	 *   
	 */
	public int getTotalLength() {
		String lengthString = "";
		String firstHex = Integer.toHexString(this.getHeader().get(2));
		String secondHex = Integer.toHexString(this.getHeader().get(3));
		
		lengthString = firstHex + secondHex;
		return Integer.parseInt(lengthString, 16);
	}
	/*
	 * Retourner Identification 
	 * sititue a 5eme octet et 6eme octet
	 *   
	 */
	public String getIden() {
		String res = "0x";
		int first = this.getHeader().get(4);
		
		if (first < 10) {
			res+="0";
		}
		int seconde = this.getHeader().get(5);
		
		if (seconde < 10) {
			res+="0";
		}
		res=res + Integer.toHexString(first) + Integer.toHexString(seconde);
		return (res);
		}
	/*
	 * Retouner Flags qui sititue a 7eme octet
	 */
	public String getFlag() {
		return ("0x"+ Integer.toHexString(this.getHeader().get(6)));
	}
	public void analyseFlag() {
		String hexa = Integer.toHexString(this.getHeader().get(6));
		StringBuffer binary = new StringBuffer("");
		if (Integer.parseInt(String.valueOf(hexa.charAt(0)))<=8)
			binary.append("0");	
		binary.append(Integer.toBinaryString(this.getHeader().get(6)));
		System.out.println(binary.charAt(0) + " = Reserved bit: "  + ((binary.charAt(0)=='1')?"Set" : "Not Set"));
		System.out.println(binary.charAt(1) + " = Dont't fragment: "  + ((binary.charAt(1)=='1')?"Set" : "Not Set"));
		System.out.println(binary.charAt(2) + " = More fragments: "  + ((binary.charAt(2)=='1')?"Set" : "Not Set"));
	}
	/* Retouner Time To Live qui sititue a 9eme octet
	 * 
	 */
	public int getTTL() {
		return this.getHeader().get(8);
	/*
	 * Retourner le Protocol qui sititue a 10eme octet			
	 */
	}
	public int getProtocol() {
		return this.getHeader().get(9);
	}
	/*
	 * Retourner Header Check Sum qui sititue a 11eme et 12 octet		
	 */
	public String getHeaderCheckSum() {
		String res = "0x";
		int first = this.getHeader().get(10);
		
		if (first < 10) {
			res+="0";
		}
		int seconde = this.getHeader().get(11);
		
		if (seconde < 10) {
			res+="0";
		}
		res=res + Integer.toHexString(first) + Integer.toHexString(seconde);
		return (res);
	}
	/*
	 * Retouner Source adresse qui sititue de 13eme a 16eùe octer 
	 */
	public String getSource() {
		StringBuffer res = new StringBuffer("");
		for(int i = 12 ;i<16 ; i++) {
			res.append(this.getHeader().get(i));
			res.append(".");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	/*
	 * Retouner Destination adresse qui sititue de 17eme a 20eme octer 
	 */
	public String getDest() {
		StringBuffer res = new StringBuffer("");
		for(int i = 16 ;i<20 ; i++) {
			res.append(this.getHeader().get(i));
			res.append(".");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	/*
	 * Retouner Optinal si il exist
	 * Option exist si IHL  > 5 (getHeaderLegnth > 20) 
	 */
	public int getOptionLength() {
		int optionLength = 0;
		if (getHeaderLength() > 20) {
			optionLength = getTotalLength() - getHeaderLength();
		}
		return optionLength;
	}
}
