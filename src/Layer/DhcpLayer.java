package Layer;

import java.util.ArrayList;
import java.util.List;

public class DhcpLayer implements Frame {
	private List<Integer> header;
	private List<Integer> data;

	public DhcpLayer(List<Integer> frame) {
		header = new ArrayList<>();
		data = new ArrayList<>();
		int header_limit = 12;
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
	
	getOP() {
		…
	}

}
