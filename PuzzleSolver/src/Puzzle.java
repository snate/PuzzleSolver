import java.util.Iterator;
import java.nio.file.Path;

/**
 * @author svalle
 *
 */
public class Puzzle implements Gruppo,GruppoOrdinabile {
	private int rows;
	private int cols;

	private Gruppo mucchio;
	private PuzzleItem[][] puzzle;
	
	private static class Piece implements PuzzleItem {
		private String id;
		private String car;
		private String north;
		private String south;
		private String east;
		private String west;

		public Piece(String[] str) {
			for(int i = 0; i< str.length; i++)
				if(str[i].isEmpty())
					str[i] = null;
			id = str[0];
			car = str[1];
			north = str[2];
			east = str[3];
			south = str[4];
			west = str[5];
		}
		@Override
		public String getId() {return id;}
		@Override
		public String getAdjacent(Dir dir){
			switch(dir.toString()){
			case "n":
				return north;
			case "e":
				return east;
			case "w":
				return west;
			case "s":
				return south;
			}
			return null;
		}
		public String toString() {return car;}
		public boolean equals(String str) {return id.equals(str); }
	}

	public static PuzzleItem createPiece(String str[]){
		return new Piece(str);
	}
	
	@Override
	public void fill(Path path) {
		mucchio = new Scrum();
		mucchio.fill(path);
	}
	
	@Override
	public void sort() {
		setDim();
		puzzle = new PuzzleItem[rows][cols];
		if(rows <= cols)
			sortX();
		else
			sortY();
		/*
		 * if(rows >= cols)
		 *    for(int i = 0; i < rows; i++)
		 *       partialSort(i,true);
		 * else
		 *    for(int i = 0; i < cols; i++)
		 *       partialSort(i,false);
		 *       
		 *  partialSort(int line, boolean rowOrdering) {
		 *     if(rowOrdering)
		 *         ...
		 *     else
		 *         ...
		 *  }
		 */
	}
	
	private void setDim(){
		cols = mucchio.conta(new Dir("n"));
		rows = mucchio.conta(new Dir("e"));
	}
	
	private void sortX(){
		if(rows == 1)
			partialSort(0,0,0,new Dir("n"),null,null);
		int limit = rows/2;
		if(rows % 2 == 1) limit++; 
		String northRef1 = "VUOTO", northRef2 = "VUOTO";
		String eastRef1 = "VUOTO", eastRef2 = "VUOTO";
		String southRef1 = "VUOTO", southRef2 = "VUOTO";
		String westRef1 = "VUOTO", westRef2 = "VUOTO";
		for(int i=0; i < limit; i++){
			if(!mucchio.isEmpty()) northRef1 = partialSort(i,i,i,new Dir("n"),northRef1,northRef2);
			if(!mucchio.isEmpty()) eastRef1 = partialSort(i,cols-1-i,i,new Dir("e"),eastRef1,eastRef2);
			if(!mucchio.isEmpty()) southRef1 = partialSort(rows-1-i,cols-1-i,i,new Dir("s"),southRef1,southRef2);
			if(!mucchio.isEmpty()) westRef1 = partialSort(rows-1-i,i,i,new Dir("w"),westRef1,westRef2);
			if(northRef1 != null) northRef2 = puzzle[i+1][i].getId();
			if(eastRef1 != null) eastRef2 = puzzle[i][cols-2-i].getId();
			if(southRef1 != null) southRef2 = puzzle[rows-2-i][cols-1-i].getId();
			if(westRef1 != null) westRef2 = puzzle[rows-1-i][i+1].getId();
		}
	}	
	
	private void sortY(){
		if(cols == 1)
			partialSort(0,0,0,new Dir("e"),null,null);
		int limit = cols/2;
		if(cols % 2 == 1) limit++;
		String northRef1 = "VUOTO", northRef2 = "VUOTO";
		String eastRef1 = "VUOTO", eastRef2 = "VUOTO";
		String southRef1 = "VUOTO", southRef2 = "VUOTO";
		String westRef1 = "VUOTO", westRef2 = "VUOTO";
		for(int i=0; i < limit; i++){
			if(!mucchio.isEmpty()) eastRef1 = partialSort(i,cols-1-i,i,new Dir("e"),eastRef1,eastRef2);
			if(!mucchio.isEmpty()) northRef1 = partialSort(i,i,i,new Dir("n"),northRef1,northRef2);
			if(!mucchio.isEmpty()) westRef1 = partialSort(rows-1-i,i,i,new Dir("w"),westRef1,westRef2);
			if(!mucchio.isEmpty()) southRef1 = partialSort(rows-1-i,cols-1-i,i,new Dir("s"),southRef1,southRef2);
			
			for(int k=0;k<rows;k++)
				for(int j=0;j<cols;j++)
					System.out.print(puzzle[k][j] + " - ");
			
			System.out.print("\n++ EST: " + eastRef1 + " ++\n");
			if(eastRef1 != null) eastRef2 = puzzle[i][cols-2-i].getId();
			System.out.print("\n++ EST: " + eastRef2 + " ++\n");
			if(northRef1 != null) northRef2 = puzzle[i+1][i].getId();
			if(westRef1 != null) westRef2 = puzzle[rows-1-i][i+1].getId();
			if(southRef1 != null) southRef2 = puzzle[rows-2-i][cols-1-i].getId();
		}
	}
	
	private String partialSort(int row,int col,int iter,Dir side,String ref1, String ref2) {
		int mX = side.mX();
		int mY = side.mY();
		int limit = cols-iter-1;
		if(side.equals("e") || side.equals("w"))
			limit = rows-iter-1;
		PuzzleItem piece = mucchio.getPieceByRefs(side,ref1,ref2);
		puzzle[row][col] = piece;
		for(int i = 1; i < limit; i++){
			String next = piece.getAdjacent(side.next());
			piece = mucchio.getPiece(next);
			puzzle[row+i*mX][col+i*mY] = piece;
		}
		if(rows == 1 || cols == 1 || limit < 1) return null;
		return (puzzle[row+mX][col+mY]).getId();
	}
	
	@Override
	public void write(Path path){
		String output = "";
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				output += puzzle[i][j];
		output += "\n\n";

		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				output += puzzle[i][j];
				if(j!=cols-1) output += "\t";
			}
			output+="\n";
		}
		output += "\n";

		output += rows + " " + cols;
		
		InputOutput.writeContent(path, output);
	}

	@Override
	public int conta(Dir side){
		if(side.equals("r")) return rows;
		else return cols;
	}
	
	@Override
	public boolean isEmpty() {
		boolean somethingPresent = false;
		if(!mucchio.isEmpty()) somethingPresent = true;
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				if(puzzle[i][j] != null)
					somethingPresent = true;
		return somethingPresent;
	}

	@Override
	public Iterator<PuzzleItem> iterator() {
		return mucchio.iterator();
	}

	@Override
	public PuzzleItem getPiece(String id) {
		PuzzleItem piece = mucchio.getPiece(id);
		if(piece != null) return piece;
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				piece = puzzle[i][j];
				if(piece.equals(id))
					return piece;
			}
		return null;
	}

	@Override
	public PuzzleItem getPieceByRefs(Dir side, String ref1, String ref2) {
		return mucchio.getPieceByRefs(side, ref1, ref2);
	}
}