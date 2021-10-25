package Layer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import decodeur_texte.Decodeur;
import decodeur_texte.ListeTrames;

public class Analyse {
	public static void analyseEthernet(EthernetLayer ethernet) {

		System.out.println("****** Ethernet Couche ******");
		System.out.println("Get Header : " + ethernet.getHeader() );
		System.out.println("Get Data : " + ethernet.getData());
		System.out.println(ethernet.getDest());
		System.out.println(ethernet.getSource()); 
		System.out.println(ethernet.getType());
	}
	public static void analyseIp(IpLayer ip) {
		System.out.println("****** IP Couche ******");
		System.out.println("Get Header : " + ip.getHeader() );
		System.out.println("Get Data : " + ip.getData());
		System.out.println("Version : " + ip.getVersion());
		System.out.println("Header Length : " + ip.getHeaderLength()+  " octets");
		System.out.println("Differentiated Services Field  : " + ip.getDSCP());
		System.out.println("Totale Legnth : " + ip.getTotalLength() + " octets");
		System.out.println("Identification : " + ip.getIden()); 
		ip.analyseFlag();
		System.out.println("Flag : " + ip.getFlag());
		System.out.println("Time To Live : " + ip.getTTL());
		System.out.println("Protocol : " + ip.getProtocol());
		System.out.println("Header Check Sum : " + ip.getHeaderCheckSum());
		System.out.println("Source : " + ip.getSource());
		System.out.println("Destionation : " + ip.getDest());
		System.out.println("Option Length : " + ip.getOptionLength());
	}
	public static void analyseUDP(UdpLayer udp) {
		System.out.println("****** UDP Couche ******");
		System.out.println("Get Header : " + udp.getHeader() );
		System.out.println("Get Data : " + udp.getData() );
		System.out.println("Source Port : " + udp.getSourcePort() );
		System.out.println("Destination Port : " + udp.getDestPort() );
		System.out.println("Length : " + udp.getLength() );
		System.out.println("Checksum : " + udp.getCheckSum() );
		
	}
	public static void analyse(List <Integer> frame) {
		EthernetLayer ethernet = new EthernetLayer(frame);
		analyseEthernet(ethernet);
		IpLayer ipLayer = new IpLayer(ethernet.getData());
		analyseIp(ipLayer);
		UdpLayer udp =  new UdpLayer(ipLayer.getData());
		analyseUDP(udp);
	}
	public static void main (String []args) throws FileNotFoundException {
		String path =  "data/trames_analyse/analyse3.txt";
		ListeTrames trames = Decodeur.getListTrames(path);
		analyse(trames.getTrame(0));
		
		//lancer_analyse(trames);
	}
}
