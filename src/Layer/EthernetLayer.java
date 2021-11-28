package Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe outil pour analyser la trame sur la couche Ethernet
 * @author Thien
 *
 */

public class EthernetLayer implements Frame	 {
	private List<Integer> header;
	private List<Integer> data;
	
	public EthernetLayer(List<Integer> frame) {
		header =  new ArrayList<>();
		data =  new ArrayList<>();
		int header_size = 14;
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
	
	public String getMACDest() {
		StringBuffer res = new StringBuffer("");
		res.append("Destination : ");
		for(int i = 0 ;i<6 ; i++) {
			if (this.getHeader().get(i) < 16)
				res.append("0"); //Ajoute un 0 en plus, pour être à 2 char par hexa
			res.append(Integer.toHexString(this.getHeader().get(i)));
			res.append(":");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	
	public String getMACSource() {
		StringBuffer res = new StringBuffer("");
		res.append("Source : ");
		for(int i = 6 ;i<12 ; i++) {
			if (this.getHeader().get(i) < 16)
				res.append("0");
			res.append(Integer.toHexString(this.getHeader().get(i)));
			res.append(":");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	
	public String getType() {
		StringBuffer res = new StringBuffer("");
		res.append("Type : 0x");
		for(int i = 12 ;i<14 ; i++) {
			if (this.getHeader().get(i) < 16)
				res.append("0");
			res.append(Integer.toHexString(this.getHeader().get(i)));
		}
		String type = res.toString();
		if(type.equals("Type : 0x0800"))
			return type + " (IPv4)";
		if(type.equals("Type : 0x08DD"))
			return type + " (IPv6)";
		if(type.equals("Type : 0x0806"))
			return type + " (ARP)";
		if(type.equals("Type : 0x8100"))
			return type + " (VLAN)";
		return type;
	}
	
}
