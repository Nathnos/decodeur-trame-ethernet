package Layer;

import java.util.ArrayList;
import java.util.List;

import decodeur_texte.HexFactory;

public class DhcpLayer implements Frame {
	private List<Integer> header;
	private List<Integer> data;

	public DhcpLayer(List<Integer> frame) {
		header = new ArrayList<>();
		data = new ArrayList<>();
		int header_limit = 240;
		for (int i = 0; i < header_limit ; i++) //12 premiers octets
			header.add(frame.get(i));
		for (int i = header_limit; i < frame.size() ; i++)
			data.add(frame.get(i));
	}
	
	public List<Integer> getHeader() {
		return header;
	}

	public List<Integer> getData() {
		return data;
	}
	public int getOperationCode() {
		return header.get(0);
	}
	public int getHType() {
		return header.get(1);
	}
	public int getHLength() {
		return header.get(2);
	}
	public int getHop() {
		return header.get(3);
	}
	public String getTransId() {
		StringBuffer res = new StringBuffer("0x");
		for (int i =4 ; i<=7;i++) {
			res.append(HexFactory.converTo2Octet(header.get(i)));
		}
		return res.toString();
	}
	public int getSecond() {
		return header.get(8)+header.get(9);
	}
	public String getBootpFlags() {
		StringBuffer res = new StringBuffer("0x");
		for (int i =10 ; i<=11;i++) {
			res.append(HexFactory.converTo2Octet(header.get(i)));
		}
		//if first bit is 1 (1000) => broadcast
		res.append(header.get(10) == 64 ?" Broadcast flag(Broadcast)": " Unicast");
		res.append("\n");
		res.append("\t");
		res.append(header.get(11) == 64 ?"1... .... .... .... = Broadcast flag: Broadcast":"0... .... .... .... = Broadcast flag : Unicast");
		res.append("\n");
		res.append("\t");
		res.append(".000 0000 0000 0000 = Reseverd flags : 0x00" );
		return res.toString();
	}
	public String getClientIpAdres() {
		StringBuffer res = new StringBuffer("");
		for (int i =12; i<=15;i++) {
			res.append(header.get(i)+":");	
		}
		return res.deleteCharAt(res.length()-1).toString();
	}
	public String getYourClientIpAdres() {
		StringBuffer res = new StringBuffer("");
		for (int i =16; i<=19;i++) {
			res.append(header.get(i)+":");	
		}
		return res.deleteCharAt(res.length()-1).toString();
	}
	public String getNextServerIpAdress() {
		StringBuffer res = new StringBuffer("");
		for (int i =20; i<=23;i++) {
			res.append(header.get(i)+":");	
		}
		return res.deleteCharAt(res.length()-1).toString();
	}
	public String getGateWayIpAdres() {
		StringBuffer res = new StringBuffer("");
		for (int i =24; i<=27;i++) {
			res.append(header.get(i)+":");	
		}
		return res.deleteCharAt(res.length()-1).toString();
	}
	public String getClientMacAdres() {
		StringBuffer res = new StringBuffer("");
		for (int i =28; i<28+this.getHLength();i++) {
			res.append(HexFactory.converTo2Octet(header.get(i))+":");	
		}
		return res.deleteCharAt(res.length()-1).toString();
	}
	public String getClientPadding() {
		StringBuffer res = new StringBuffer("");
		for (int i =28+this.getHLength(); i<44;i++) {
			res.append(HexFactory.converTo2Octet(header.get(i))+":");	
		}
		return res.deleteCharAt(res.length()-1).toString();
	}
	public String getMacgicCookie() {
		if((header.get(236) ==99)&&(header.get(237) ==130)&&(header.get(238) ==83) && header.get(239) ==99)
			return ("DHCP");
		return "Undefine";
	}
	public void analyseAllOption() {
		List<DhcpOption> b = getAllOption();
		for(DhcpOption temp : b)
			temp.analyseOption();
	}
	public List <DhcpOption> getAllOption(){
		List <DhcpOption> res = new ArrayList<DhcpOption>();
		int pointeur =1 ;
		int optionLength = data.get(pointeur);
		while (data.get(pointeur -1)!=255) {
			List<Integer>optionData = new ArrayList<Integer>();
			for (int i = pointeur+1; i<= pointeur + optionLength;i++) {
				optionData.add(data.get(i));
			}
			if (Constant.DHCPOPTION.containsKey(pointeur-1))
				res.add(new DhcpOption(data.get(pointeur - 1),data.get(pointeur),optionData));
			else
				res.add(new DhcpOption(256,data.get(pointeur),optionData));
			pointeur=pointeur+ data.get(pointeur)+2;
			// if we reach END option , end of option list
			if (data.get(pointeur-1)==255) {
				res.add(new DhcpOption(data.get(pointeur-1),0,null));
				break;
			}
			optionLength = data.get(pointeur);
			
		}
		return res;
		
	}
}
