package Layer;

import java.util.ArrayList;
import java.util.List;

public class UdpLayer {
	private List <Integer> frame ;
	public UdpLayer (List <Integer> frame) {
		this.frame = frame;
	}
	public List <Integer> getHeader(){
		List <Integer> header = new ArrayList<Integer>();
		for (int i = 0; i<8 ;i++) {
			header.add(frame.get(i));
		}
		return header;
	}
	public List <Integer> getData(){
		List <Integer> header = new ArrayList<Integer>();
		for (int i = 8; i<frame.size() ;i++) {
			header.add(frame.get(i));
		}
		return header;
	}
	public int getSourcePort() {
		StringBuffer res = new StringBuffer("");
		String firstOctet = Integer.toHexString(getHeader().get(0));
		res.append(firstOctet);
		if (getHeader().get(1)< 16) {
			res.append('0');
		}
		String secondeOctet = Integer.toHexString(getHeader().get(1));
		res.append(secondeOctet);
		return Integer.parseInt (res.toString(),16);
	}
	public int getDestPort() {
		StringBuffer res = new StringBuffer("");
		String firstOctet = Integer.toHexString(getHeader().get(2));
		res.append(firstOctet);
		if (getHeader().get(3)< 16) {
			res.append('0');
		}
		String secondeOctet = Integer.toHexString(getHeader().get(3));
		res.append(secondeOctet);
		return Integer.parseInt (res.toString(),16);
	}
	public int getLength() {
		StringBuffer res = new StringBuffer("");
		String firstOctet = Integer.toHexString(getHeader().get(4));
		res.append(firstOctet);
		if (getHeader().get(5)< 16) {
			res.append('0');
		}
		String secondeOctet = Integer.toHexString(getHeader().get(5));
		res.append(secondeOctet);
		System.out.println(res);
		return Integer.parseInt (res.toString(),16);
	}
	public String getCheckSum() {
		StringBuffer res =  new StringBuffer("0x");
		res.append(Integer.toHexString(getHeader().get(6)));
		if (getHeader().get(7)< 16) {
			res.append('0');
		}
		res.append(Integer.toHexString(getHeader().get(7)));
		return res.toString();
	}
	
}
