package Layer;

import java.util.Arrays;
import java.util.List;

import decodeur_texte.HexFactory;

public class DhcpOption {
	private int optionCode;
	private int optionLength;
	private List<Integer> data;
	public DhcpOption(int optionCode, int optionLength,List<Integer> data) {
		this.optionCode = optionCode;
		this.optionLength = optionLength;
		this.data =  data;
	}
	public int getOptionCode() {
		return optionCode;
	}
	public int getOptionLength() {
		return optionLength;
	}
	public List<Integer> getData() {
		return data;
	}
	public void analyseOption() {
		System.out.println("Option : (" +optionCode+") " +Constant.DHCPOPTION.get(optionCode));
		System.out.println("\t Length : " + this.getOptionLength());
		System.out.println("\t "+getDetailOption());
	}
	public String getDetailOption() {
		StringBuffer res = new StringBuffer("");
		int dex = 0;
		String hex ="";
		switch(optionCode) {
		case 1:
			for(int temp : data) {
				res.append(temp+".");
			}
			return "Subnet Mask: "+res.deleteCharAt(res.length()-1).toString();
		case 2:
			for (int i = 0;i < data.size();i++) {
				hex+=(HexFactory.converTo2Octet(data.get(i)));
			}
			dex = Integer.parseInt(hex,16);
			return "IP Address Lease Time : " +dex +"s " +dex+" seconds";
		case 3:
			for(int temp : data) {
				res.append(temp+".");
			}
			return "Router: "+res.deleteCharAt(res.length()-1).toString();
		case 6:
			for(int i = 0; i < this.optionLength;i++) {
				if (i%4 ==0) {
					res.append("\n\tDomain Name :");
				}
				res.append(data.get(i)+".");
			}
			return res.toString();
		case 15:
			for(int temp : data) {
				res.append((char)temp);
			}
			return ("Domain Name: "+res.toString());
		case 23:
			return ("Default IP Time-to-Live: " +data.get(0));
		case 53:
			return "DHCP: " +Constant.DHCP_MESSAGE_TYPE.get(data.get(0)); 
		case 50:
			for (Integer tmp: data) {
				res.append(tmp +".");
			}
			return "Requested IP Address: "+res.deleteCharAt(res.length()-1).toString();
		case 51:
			for (int i = 0;i < data.size();i++) {
				hex+=(HexFactory.converTo2Octet(data.get(i)));
			}
			dex = Integer.parseInt(hex,16);
			return "IP Address Lease Time : " +dex +"s " +dex/(60*60*24)+" days";
		case 54:
			for (Integer tmp: data) {
				res.append(tmp +".");
			}
			return "DhCP Server Identifier: "+res.deleteCharAt(res.length()-1).toString();
		
		case 55:
			for(Integer tmp :data) {
				res.append("Parameter Request List Item : ("+tmp+") " +Constant.DHCPOPTION.get(tmp)+"\n\t ");
			}
			return res.toString();
		case 58:
			String hex2="";
			for (int i = 0;i < data.size();i++) {
				hex2+=(HexFactory.converTo2Octet(data.get(i)));
			}
			dex = Integer.parseInt(hex2,16);
			return "Renewal Time Value: " +dex +"s " +dex/(60*60)+" hours";
		case 59:
			String hex3="";
			for (int i = 0;i < data.size();i++) {
				hex3+=(HexFactory.converTo2Octet(data.get(i)));
			}
			dex = Integer.parseInt(hex3,16);
			return "Rebinding Time Value: " +dex +"s " +dex/(60*60)+" hours";
		
			
		case 60:
			for(int temp : data) {
				res.append((char)temp);
			}
			return ("Vendor class identifier: "+res.toString());
		case 61:
			res.append("Hardware Type: "+Constant.HTYPE.get(data.get(0)));
			res.append("\n\t Client MAC address: (");
			for (int i = 1;i < data.size();i++) {
				res.append(HexFactory.converTo2Octet(data.get(i))+".");
			}
			return (res.deleteCharAt(res.length()-1).toString() +")");
			
		case 12:
			for(int temp : data) {
				res.append((char)temp);
			}
			return ("Host Name: "+res.toString());
		case 57:
			for(int temp : data) {
				res.append(HexFactory.converTo2Octet(temp));
			}
			int decimal=Integer.parseInt(res.toString(),16);  
			return "Maximum DHCP Message Size :" + decimal;
		case 255:
			return "Option End (255)";
		default:
			for(int temp : data) {
				res.append(HexFactory.converTo2Octet(temp)+".");
			}
			return "Data : " +res.deleteCharAt(res.length()-1).toString();
		}
	}
}
