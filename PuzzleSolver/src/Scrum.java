import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author svalle
 */
public class Scrum implements Gruppo {
	private ArrayList<PuzzleItem> mucchio = new ArrayList<PuzzleItem>();
	
	@Override
	public int conta(Dir side) {
		int x = 0;
		Iterator<PuzzleItem> it = mucchio.iterator();
		while(it.hasNext()){
			PuzzleItem item = it.next();
			if(item.getAdjacent(side).equals("VUOTO")) x++;
		}
		return x;
	}
	
	@Override
	public PuzzleItem getPiece(String id) {
		Iterator<PuzzleItem> it = mucchio.iterator();
		while(it.hasNext()) {
			PuzzleItem item = it.next();
			if(item.equals(id)) {
				it.remove();
				return item;
			}
		}
		return null;
	}
	
	@Override
	public PuzzleItem getEdgePiece(Dir edge, Dir refSide, String ref){
		Iterator<PuzzleItem> it = mucchio.iterator();
		while(it.hasNext()){
			PuzzleItem item = it.next();
			String border = item.getAdjacent(edge);
			String closeRef = item.getAdjacent(refSide);
			if(border.equals("VUOTO") && closeRef.equals(ref)){
				it.remove();
				return item;
			}	
		}
		return null;
	}
	
	@Override
	public void fill(Path path) {
		ArrayList<String> rows = InputOutput.readContent(path);
		Iterator<String> ita = rows.iterator();
		while(ita.hasNext()){
			String line = ita.next();
			String[] piece = line.split("\\t",-1);
			PuzzleItem item = Puzzle.createPiece(piece);
			mucchio.add(item);
		}
	}
	
	public boolean isEmpty(){
		return mucchio.isEmpty();
	}
}
