package decodeur_texte;

public class Line {
	private int line;
	private String data;
	
	public Line(int line, String data) {
		this.line = line;
		this.data = data.replaceAll("( )+", " ");
	}
	
	public int getLine() {
		return line + 1; //+1 car les �diteurs de textes commencent � compter � 1
	}
	
	public String getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return data;
	}
}