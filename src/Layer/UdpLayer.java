package Layer;

import java.util.ArrayList;
import java.util.List;

import decodeur_texte.HexFactory;

public class UdpLayer {
	private List<Integer> header;
	private List<Integer> data;
	
	public UdpLayer (List <Integer> frame) {
		header = new ArrayList<>();
		data = new ArrayList<>();
		int header_size = 8;
		for (int i = 0; i < header_size ; i++)
			header.add(frame.get(i));
		for (int i = header_size; i < frame.size() ; i++)
			data.add(frame.get(i));
	}
	
	public List <Integer> getHeader(){
		return header;
	}
	
	public List <Integer> getData(){
		return data;
	}
	
	public int getSourcePort() {
		return HexFactory.merge2(header.get(0), header.get(1));
	}
	
	public int getDestPort() {
		return HexFactory.merge2(header.get(2), header.get(3));
	}
	
	public int getLength() {
		return HexFactory.merge2(header.get(4), header.get(5));
	}
	
	public String getCheckSum() {
		StringBuffer res =  new StringBuffer("0x");
		res.append(Integer.toHexString(getHeader().get(6)));
		if (getHeader().get(7)< 16)
			res.append('0');
		res.append(Integer.toHexString(getHeader().get(7)));
		return res.toString();
	}
	
	public boolean isDHCP() {
		return getDestPort() == 67 || getSourcePort() == 67;
	}
	
	public boolean isDNS() {
		return getDestPort() == 53 || getSourcePort() == 53;
	}
	
	public String getApplication() {
		if(isDNS())
			return "DNS";
		if(isDHCP())
			return "DHCP";
		if(getDestPort() == 80 || getSourcePort() == 80)
			return "Web (HTTP)";
		if(getDestPort() == 443 || getSourcePort() == 443)
			return "Web (HTTPS)";
		if(getDestPort() == 22 || getSourcePort() == 22)
			return "SSH";
		return "Application unknown (not implemented in this programm";
	}
	
}
