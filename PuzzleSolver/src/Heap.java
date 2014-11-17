import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author svalle
 */
public class Heap implements Clump {
	private ArrayList<Piece> lista = new ArrayList<Piece>();

	public void fill(Object file) {
		if(file instanceof String) {
			//costruisci
		}
	}
	
	@Override
	public Piece getPiece(String id) {
		Iterator<Piece> it = lista.iterator();
		int index=0;
		while(it.hasNext()) {
			Piece current = it.next();
			if(current.getId()==id) {
				lista.remove(index);
				return current;
			}
			index++;
		}
		return null;
	}
	
	@Override
	public void write(String path) {
		Iterator<Piece> it = lista.iterator();
		while(it.hasNext()) {
			Piece current = it.next();
			//write current on file
		}
	}
}
