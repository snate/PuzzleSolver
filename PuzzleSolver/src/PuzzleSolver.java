/**
 * @author svalle
 */
public class PuzzleSolver {

	/**
	 * @param {string} stream - Path dei file di input e output
	 */
	public static void main(String[] args) {
		String pathIn = args[0];
		String pathOut = args[1];
		Clump h = new Heap();
		h.fill(pathIn);
		Clump p = new Puzzle();
		p.fill(h);
		
	}

}
