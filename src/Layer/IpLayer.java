package Layer;

import java.util.ArrayList;
import java.util.List;

import decodeur_texte.HexFactory;

/**
 * Classe outil pour analyser la trame sur la couche IP
 * @author Thien
 *
 */
public class IpLayer implements Frame {
	private List <Integer> header;
	private List <Integer> data;
	
	public IpLayer(List <Integer> frame) {
		header = new ArrayList<>();
		data = new ArrayList<>();
		int header_size = HexFactory.split_hex_second(frame.get(0)) * 4; // IHL * 4;
		for (int i = 0; i < header_size ; i++)
			header.add(frame.get(i));
		for (int i = header_size; i < frame.size() ; i++)
			data.add(frame.get(i));
	}
	
	public List<Integer> getHeader() {
		return header;
	}
	
	public List<Integer> getData() {
		return data;
	}
	
	public String getVersion() {
		int IPv = HexFactory.split_hex_first(header.get(0)); //4 pour IPv4, 6 pour IPv6
		return "IPv" + IPv;
	}
	
	public int getHeaderLength() {
		return HexFactory.split_hex_second(header.get(0)) * 4;
	}
	
	public String getTOS() {
		return ("0x"+ header.get(1));
	}
	
	public int getTotalLength() {
		return HexFactory.merge2(header.get(2), header.get(3));
	}
	
	public String getIden() { //TrustedHostID ; Fragment Identification
		return "0x" + Integer.toHexString(HexFactory.merge2(header.get(4), header.get(5)));
		}
	
	public String getFragmentOffset() {
		return "Ob" + HexFactory.split_binary_8(header.get(6)).substring(3);
	}
	
	public boolean getFlagDF() {
		return HexFactory.split_binary_8(header.get(6)).charAt(1) == '1';
	}
	
	public boolean getFlagMF() {
		return HexFactory.split_binary_8(header.get(6)).charAt(2) == '1';
	}
	
	public int getTTL() {
		return this.getHeader().get(8);

	}
	public int getProtocol() {
		return this.getHeader().get(9);
	}
	
	public String getStringProtocol () {
		int protocol = getProtocol();
		if(protocol == 1) {
			return "Protocole : ICMP (01)";
		}
		if(protocol == 2) {
			return "Protocole : IGMP (02)";
		}
		if(protocol == 6) {
			return "Protocole : TCP (06)";
		}
		if(protocol == 17	) {
			return "Protocole : UDP (17)";
		}
		return "Protocole : " + protocol;
	}
	
	public boolean isUDP() {
		return getProtocol() == 17;
	}
	
	public String getHeaderCheckSum() {
		return "0x" + Integer.toHexString(HexFactory.merge2(header.get(10), header.get(11)));
	}
	
	public String getIPSource() {
		StringBuffer res = new StringBuffer("");
		for(int i = 12 ;i < 16 ; i++) {
			res.append(this.getHeader().get(i));
			res.append(".");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	
	public String getIPDest() {
		StringBuffer res = new StringBuffer("");
		for(int i = 16 ;i < 20 ; i++) {
			res.append(this.getHeader().get(i));
			res.append(".");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	
	public int getOptionLength() {
		return getHeaderLength() - 20;
	}
}
