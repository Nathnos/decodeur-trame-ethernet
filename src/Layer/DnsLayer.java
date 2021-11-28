package Layer;

import java.util.ArrayList;
import java.util.List;

import decodeur_texte.HexFactory;

public class DnsLayer implements Frame{
	private List<Integer> header;
	private List<Integer> data;
	private int sectionPointer = 0;
	
	public DnsLayer(List <Integer> frame) {
		header = new ArrayList<>();
		data = new ArrayList<>();
		int header_size = 12;
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
	
	public String getTransId() {
		String res = "0x";
		int first = header.get(0);
		if (first < 16) {
			res+="0";
		}
		res+=Integer.toHexString(first);
		int seconde = header.get(1);
		if (seconde < 16) {
			res+="0";
		}
		res+=Integer.toHexString(seconde);
		return res;
	}
	
	public String getBinaryFlags() {
		int flags = HexFactory.merge2(header.get(2), header.get(3));
		return HexFactory.split_binary_16(flags);
	}
	
	public boolean isResponse() {
		return getBinaryFlags().charAt(0) == '1';
	}
	
	public String getFlagResponse() {
		boolean response = isResponse();
		return "Response : message is a " + (response ? "response" : "query");
	}
	
	public String getFlagOptcode() {
		String optcodeStr = getBinaryFlags().substring(1, 5);
		int optcode = Integer.parseInt(optcodeStr, 2);
		String opt = "Optcode : ";
		if(optcode == 0)
			return opt + "Standard Query";
		if(optcode == 1)
			return opt + "Inquery";
		if(optcode == 2)
			return opt + "Status";
		return opt + "Reserved";
	}

	public String getFlagAuthorative() { //Only for response
		boolean authorative = getBinaryFlags().charAt(5) == '1';
		return "Authorative : Server is " + (authorative ? " " : "not ") + "an authority for domain";
	}
	
	public String getFlagTruncated() {
		boolean truncated = getBinaryFlags().charAt(6) == '1';
		return "Truncated : Message " + (truncated ? " is truncated" : "is not truncated");
	}
	
	public String getFlagRecursionDesired() {
		boolean truncated = getBinaryFlags().charAt(7) == '1';
		return "Recursion Desired : " + (truncated ? "Do " : "Don’t do ") + "query recursively";
	}
	
	public String getFlagRecursionAvailable() { //Only for response
		boolean available = getBinaryFlags().charAt(8) == '1';
		return "Recursion Available : Server " + (available ? "can" : "can’t") + " do recursive queries";
	}
	
	// 9 reservered
	
	public String getFlagAnswerAuthenticated() { //Only for response
		boolean auth = getBinaryFlags().charAt(10) == '1';
		return "Answer Authenticated : Answer " + (auth ? "is" : "is not") + " authentified";
	}
	
	public String getFlagUnauthenticatedAllowed() {
		boolean allowed = getBinaryFlags().charAt(10) == '1';
		return "Non-authenticated data : " + (allowed ? "Acceptable" : "Unacceptable");
	}
	
	public String getFlagReplyCode() { //Only for response
		int error = HexFactory.convert(getBinaryFlags().substring(12, 16));
		String rc = "Reply Code : ";
		if(error == 0)
			return rc + "No errors";
		if(error == 1)
			return rc + "Request format error";
		if(error == 2)
			return rc + "Server problem";
		if(error == 3)
			return rc + "Name does not exist";
		if(error == 4)
			return rc + "Not implemented";
		if(error == 5)
			return rc + "Declined";
		return rc + "Reserved";
	}
	
	public int getQuestions() {
		return HexFactory.merge2(header.get(4), header.get(5));
	}
	
	public int getAnswers() {
		return HexFactory.merge2(header.get(6), header.get(7));
	}
	
	public int getAuthority() {
		return HexFactory.merge2(header.get(8), header.get(9));
	}
	
	public int getAdditional() {
		return HexFactory.merge2(header.get(10), header.get(11));
	}
	
	public String getNextSection() {
		return getNextSection(false);
	}
	
	public String getNextSection(boolean isQuestion) {
		StringBuilder name = new StringBuilder();
		if(data.get(sectionPointer) == 192) { //c0 ; pointer
			sectionPointer += 2;
		}
		else {
			while(data.get(sectionPointer) != 0) {
				int c = data.get(sectionPointer);
				if(c == 1 || c == 3 || c == 7 || c== 4) 
					name.append('.');
				else
					name.append((char) c);
				sectionPointer++;
			}
			name.append(": ");
			sectionPointer++; //Pass the '00' END OF STRING
		}
		int type = HexFactory.merge2(data.get(sectionPointer + 0), data.get(sectionPointer + 1));
		int classDNS = HexFactory.merge2(data.get(sectionPointer + 2), data.get(sectionPointer + 3));
		String typeS = "type ";
		if(type == 1)
			typeS += "A (Host Adress) (01)";
		else if(type == 2)
			typeS += "ns (02)";
		else if(type == 3)
			typeS += "MD (03)";
		else if(type == 4)
			typeS += "MF (04)";
		else if(type == 5)
			typeS += "CNAME (05)";
		else if(type == 6)
			typeS += "SOA (06)";
		else if(type == 7)
			typeS += "MB (07)";
		else if(type == 8)
			typeS += "MG (08)";
		else if(type == 9)
			typeS += "MR (09)";
		else if(type == 10)
			typeS += "NULL (10)";
		else if(type == 11)
			typeS += "WKS (11)";
		else if(type == 12)
			typeS += "PTR (domain name pointer) (12)";
		else if(type == 13)
			typeS += "HINFO (13)";
		else if(type == 14)
			typeS += "MINFO (14)";
		else if(type == 15)
			typeS += "MX (15)";
		else if(type == 16)
			typeS += "TXT (16)";
		//Class
		String classDNSS = "class ";
		if(classDNS == 1)
			classDNSS += "Internet";
		else if(classDNS == 2)
			classDNSS += "Class Csnet (depreciated)";
		else if(classDNS == 3)
			classDNSS += "Chaos";
		else if(classDNS == 4)
			classDNSS += "Hesiod";
		else
			classDNSS += "Unknown (" + classDNS + ")";
		sectionPointer += 4; // 4 for Type + Class
		if(isQuestion)
			return name.toString() + typeS + ", " + classDNSS;
		// Else, we need TTL and Data Length
		int ttl = HexFactory.merge4(data.get(sectionPointer + 0), data.get(sectionPointer + 1),
				data.get(sectionPointer + 2), data.get(sectionPointer + 3));
		String ttlS= "TTL : " + ttl;
		int dataLength = HexFactory.merge2(data.get(sectionPointer + 4), data.get(sectionPointer + 5));
		String dataLengthS = "Data Length : " + dataLength;
		sectionPointer += 6; // 6 for TTL + Data Length
		StringBuilder dataS = new StringBuilder();
		for(int i = 0 ; i < dataLength ; i++) {
			int c = data.get(sectionPointer);
			if(c >= 1 && c <= 9) 
				dataS.append('.');
			else
				dataS.append((char) c);
			sectionPointer++;
		}
		return name.toString() + typeS + ", " + classDNSS + ", " + ttlS + ", " + dataLengthS + ", " + dataS.toString();
	}
}
