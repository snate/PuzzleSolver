import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author svalle
 *
 */
public class Puzzle implements Clump {
	private int rows;
	private int cols;
	private Piece[][] lista;
	
	@Override
	public Piece getPiece(String id) {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				Piece current = lista[i][j]; 
				if(current.getId()==id)
					return current;
			}
		}
		return null;
	}
	
	public void fill(Object clump) {
		if(clump instanceof Clump) {
			//costruisci
		}
	}
	
	public void write(String path) {
		//scrivi
	}
}
