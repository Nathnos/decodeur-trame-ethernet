package Layer;

import java.io.FileNotFoundException;
import java.util.List;

import decodeur_texte.Decodeur;
import decodeur_texte.ListeTrames;

public class Analyse {
	
	public static void analyseEthernet(EthernetLayer ethernet) {
		System.out.println("****** Couche 2 (Ethernet) ******");
		System.out.println(ethernet.getMACSource()); 
		System.out.println(ethernet.getMACDest());
		System.out.println(ethernet.getType());
	}
	
	public static void analyseIp(IpLayer ip) {
		System.out.println("\n****** Couche 3 (IP) ******");
		System.out.println("Version : " + ip.getVersion());
		System.out.println("Source : " + ip.getIPSource());
		System.out.println("Destionation : " + ip.getIPDest());
		System.out.println("Header Length : " + ip.getHeaderLength()+  " octets");
		System.out.println("Differentiated Services Field  : " + ip.getTOS());
		System.out.println("Total Length : " + ip.getTotalLength() + " octets");
		System.out.println("Fragment Identification : " + ip.getIden()); 
		System.out.println("Fragment Offset : " + ip.getFragmentOffset());
		System.out.println("Don’t fragment : " + ip.getFlagDF());
		System.out.println("More fragments : " + ip.getFlagMF());
		System.out.println("Time To Live : " + ip.getTTL());
		System.out.println("Protocol : " + ip.getStringProtocol());
		System.out.println("Header Check Sum : " + ip.getHeaderCheckSum());
		System.out.println("Option Length : " + ip.getOptionLength());
	}
	
	public static void analyseUDP(UdpLayer udp) {
		System.out.println("\n****** Couche 4 (UDP) ******");
		System.out.println("Source Port : " + udp.getSourcePort() );
		System.out.println("Destination Port : " + udp.getDestPort() );
		System.out.println("Length : " + udp.getLength() );
		System.out.println("Checksum : " + udp.getCheckSum() );
	}
	
	public static void analyseDNS(DnsLayer dns) {
		System.out.println("\n****** Couche 7 (DNS) ******");
		System.out.println("Transaction ID : " + dns.getTransId());
		System.out.println("Flags : ");
		if(dns.isResponse()) {
			System.out.println("\t" + dns.getFlagResponse());
			System.out.println("\t" + dns.getFlagOptcode());
			System.out.println("\t" + dns.getFlagAuthorative());
			System.out.println("\t" + dns.getFlagTruncated());
			System.out.println("\t" + dns.getFlagRecursionDesired());
			System.out.println("\t" + dns.getFlagRecursionAvailable());
			System.out.println("\t" + dns.getFlagAnswerAuthenticated());
			System.out.println("\t" + dns.getFlagUnauthenticatedAllowed());
			System.out.println("\t" + dns.getFlagReplyCode());
		}
		else {
			System.out.println("\t" + dns.getFlagResponse());
			System.out.println("\t" + dns.getFlagOptcode());
			System.out.println("\t" + dns.getFlagTruncated());
			System.out.println("\t" + dns.getFlagRecursionDesired());
			System.out.println("\t" + dns.getFlagUnauthenticatedAllowed());
		}
		int numberOfQuestions = dns.getQuestions();
		System.out.println("Questions : " + numberOfQuestions);
		for(int i = 0 ; i < numberOfQuestions ; i++) {
			System.out.println("\t" + dns.getNextSection(true));
		}
		int numberOfAnwers = dns.getAnswers();
		System.out.println("Answers : " + numberOfAnwers);
		for(int i = 0 ; i < numberOfAnwers ; i++) {
			System.out.println("\t" + dns.getNextSection());
		}
		int numberOfAuthority = dns.getAuthority();
		System.out.println("Authority RRs : " + numberOfAuthority);
		for(int i = 0 ; i < numberOfAuthority ; i++) {
			System.out.println("\t" + dns.getNextSection());
		}
		int numberOfAdditional = dns.getAdditional();
		System.out.println("Additionnal RRs : " + numberOfAdditional);
		for(int i = 0 ; i < numberOfAdditional ; i++) {
			System.out.println("\t" + dns.getNextSection());
		}
	}
	
	public static void analyseDHCP(DhcpLayer dhcp) {
		System.out.println("\n****** Couche 7 (DHCP) ******");
	}
	
	public static void analyse(List <Integer> frame) {
		EthernetLayer ethernet = new EthernetLayer(frame);
		analyseEthernet(ethernet);
		IpLayer ip = new IpLayer(ethernet.getData());
		analyseIp(ip);
		if(ip.isUDP()) {
			UdpLayer udp = new UdpLayer(ip.getData());
			analyseUDP(udp);
			if(udp.isDHCP()) {
				DhcpLayer dhcp = new DhcpLayer(udp.getData());
				analyseDHCP(dhcp);
			}
			else if(udp.isDNS()) {
				DnsLayer dns = new DnsLayer(udp.getData());
				analyseDNS(dns);
				
			}
		}
	}
	public static void main (String []args) throws FileNotFoundException {
		String path =  "data/trames_analyse/analyse3.txt";
		ListeTrames trames = Decodeur.getListTrames(path);
		analyse(trames.getTrame(0));
		
		//lancer_analyse(trames);
	}
}
