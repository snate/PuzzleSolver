import java.util.Iterator;
import java.nio.file.Path;

/**
 * @author svalle
 */
public interface Gruppo {
	public static final String[] DIR = {"n", "e", "w", "s"};
	public void fill(Path path);
	public int conta(Dir side);
	public PuzzleItem getPiece(String id);
	public PuzzleItem getPieceByRefs(Dir side, String ref1, String ref2);
	public boolean isEmpty();
	public Iterator<PuzzleItem> iterator();
	public static class Dir {
		private String dir;
		public Dir(String d) { dir = d; }
		public Dir(int index) { dir = DIR[index]; }
		public String toString() {return dir;}
		public boolean equals(String s) { return dir.equals(s);	}
		public Dir next() {
			switch(dir){
			case "w": return new Dir("n");
			case "e": return new Dir("s");
			case "n": return new Dir("e");
			case "s": return new Dir("w");
			}
			return null;
		}
		public Dir init() {
			switch(dir.toString()){
			case "n": return new Dir("w");
			case "e": return new Dir("n");
			case "s": return new Dir("e");
			case "w": return new Dir("s");
			}
			return null;
		}
		public int mX(){
			switch(dir) {
			case "n":
			case "s": return 0;
			case "e": return 1;
			case "w": return -1;
			}
			return 0;
		}
		public int mY(){
			switch(dir) {
			case "n": return 1;
			case "s": return -1;
			case "e": 
			case "w": return 0;
			}
			return 0;
		}
	}
}
