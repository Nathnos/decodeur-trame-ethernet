package Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe outil pour analyser la trame sur la couche Ethernet
 * @author Thien
 *
 */
public class EthernetLayer implements Frame	 {
	private List<Integer> frames;
	
	public EthernetLayer(List<Integer> frames) {
		this.frames = frames;
	}
	/*
	 * Retourner 18 octets de champ d'entete(14 priemere d'octets et 4 a la fin)
	 */
	@Override
	public List<Integer> getHeader() {
		// TODO Auto-generated method stub
		List <Integer> header =  new ArrayList();
		for (int i = 0 ; i < 14 ; i ++) {
			header.add(frames.get(i));
		}
		return header;
	}

	@Override
	/*
	 * Retourner 46-1500 octets de donnee
	 */
	public List<Integer> getData() {
		// TODO Auto-generated method stub
		List <Integer> data =  new ArrayList();
		for (int i = 14; i < frames.size();i++){
			data.add(frames.get(i));
		}
		return data;
	}
	public String getDest() {
		StringBuffer res = new StringBuffer("");
		res.append("Destination : ");
		for(int i = 0 ;i<6 ; i++) {
			if (this.getHeader().get(i) < 10)
				res.append("0");
			res.append(Integer.toHexString(this.getHeader().get(i)));
			res.append(":");
		}
		res.deleteCharAt(res.length()-1);
		return (res.toString());
	}
	public String getSource() {
		StringBuffer res = new StringBuffer("");
		res.append("Source : ");
		for(int i = 6 ;i<12 ; i++) {
			if (this.getHeader().get(i) < 10)
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
			if (this.getHeader().get(i) < 10)
				res.append("0");
			res.append(Integer.toHexString(this.getHeader().get(i)));
			
		}
		return (res.toString());
	}
	
}
