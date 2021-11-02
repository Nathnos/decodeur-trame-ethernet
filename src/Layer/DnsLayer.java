package Layer;

import java.util.ArrayList;
import java.util.List;

public class DnsLayer implements Frame{
	List <Integer> frame;
	public DnsLayer(List <Integer> frame) {
		this.frame = frame;
	}
	@Override
	/*
	 * Retourner les 12 premieres octets
	 */
	public List<Integer> getHeader() {
		// TODO Auto-generated method stub
		List <Integer> res =  new ArrayList<>();
		for (int i = 0; i< 12 ; i++)
			res.add(frame.get(i));
		return res;
	}
	/*
	 * Retourner le restant
	 */
	@Override
	public List<Integer> getData() {
		// TODO Auto-generated method stub
		List <Integer> res =  new ArrayList<>();
		for (int i = 12; i< frame.size() ; i++)
			res.add(frame.get(i));
		return res;
	}
	public String getTransId() {
		String res = "0x";
		int first = this.getHeader().get(0);
		
		if (first < 10) {
			res+="0";
		}
		res+=Integer.toHexString(first);
		int seconde = this.getHeader().get(1);
		
		if (seconde < 10) {
			res+="0";
		}
		res+=Integer.toHexString(seconde);
		return (res);
	}
	public String getFlag() {
		String res = "0x";
		int first = this.getHeader().get(2);
		
		if (first < 10) {
			res+="0";
		}
		res+=Integer.toHexString(first);
		int seconde = this.getHeader().get(3);
		
		if (seconde < 10) {
			res+="0";
		}
		res+=Integer.toHexString(seconde);
		return (res);
	}
	public int getQuestions() {
		int res = this.frame.get(4) +this.frame.get(5);
		return res;
	}
	public int getAnswer() {
		int res = this.frame.get(6) +this.frame.get(7);
		return res;
	}
	public int getAuthority() {
		int res = this.frame.get(8) +this.frame.get(9);
		return res;
	}
	public int getAdditional() {
		int res = this.frame.get(10) +this.frame.get(11);
		return res;
	}
}
