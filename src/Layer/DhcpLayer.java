package Layer;

import java.util.List;

public class DhcpLayer implements Frame {

	private List<Integer> frame;
	public DhcpLayer(List<Integer> frame) {
		this.frame = frame;
	}
	@Override
	public List<Integer> getHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
