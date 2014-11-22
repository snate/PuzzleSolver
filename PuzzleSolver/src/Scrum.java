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
	public int dim(String side) {
		int x = 0,rc;
		if(side=="c") rc = 0;
		else		  rc = 1;
		Iterator<PuzzleItem> it = mucchio.iterator();
		while(it.hasNext()){
			PuzzleItem item = it.next();
			if(item.getAdjacent(DIR[rc]).equals("VUOTO")) x++;
		}
		return x;
	}
	
	@Override
	public void fill(Path path) {
		ArrayList<String> rows = InputOutput.readContent(path);
		Iterator<String> ita = rows.iterator();
		while(ita.hasNext()){
			String line = ita.next();
			String[] piece = line.split("\\t",-1);
			PuzzleItem item = Puzzle.createPiece(piece);
			for(int i=0; i<piece.length; i++)
				System.out.print(piece[i] + " ");
			mucchio.add(item);
			System.out.println();
		}
	}

	public Iterator<PuzzleItem> iterator(){
		return mucchio.iterator();
	}
	
	public boolean isEmpty(){
		return mucchio.isEmpty();
	}
}
