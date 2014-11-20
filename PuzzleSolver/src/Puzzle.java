import java.util.ArrayList;
import java.util.Iterator;
import java.nio.file.Path;

/**
 * @author svalle
 *
 */
public class Puzzle implements GruppoOrdinabile {
	private int rows;
	private int cols;
	private static class Piece {
		private String id;
		private String car;
		private String north;
		private String south;
		private String east;
		private String west;

		public Piece(String[] str){
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
		public String getId() {return id;}
		public String getAdjacent(String dir){
			switch(dir){
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
	}

	private ArrayList<Piece> mucchio = new ArrayList<Piece>();
	private Piece[][] puzzle;
	
	@Override
	public void fill(Path path) {
		ArrayList<String> rows = InputOutput.readContent(path);
		Iterator<String> ita = rows.iterator();
		while(ita.hasNext()){
			String line = ita.next();
			String[] piece = line.split("\\t",-1);
			Piece item = new Piece(piece);
			mucchio.add(item);
		}
	}
	
	@Override
	public void sort() {
		setDim();
		puzzle = new Piece[rows][cols];
		if(rows <= cols)
			sortX();
		else
			sortY();
	}
	
	private void setDim(){
		int r = 0, c = 0;
		Iterator<Piece> it = mucchio.iterator();
		while(it.hasNext()){
			Piece item = it.next();
			if(item.getAdjacent("n") == null) c++;
			if(item.getAdjacent("e") == null) r++;
		}
		cols = c;
		rows = r;
		System.out.println(c + " " + r);
	}
	
	private void sortX(){
		if(rows == 1)
			sortPartial(0,0,0,"n",null,null);
		int limit = rows/2;
		if(rows % 2 == 1) limit++; 
		String northRef1 = null, northRef2 = null;
		String eastRef1 = null, eastRef2 = null;
		String southRef1 = null, southRef2 = null;
		String westRef1 = null, westRef2 = null;
		for(int i=0; i < limit; i++){
			if(!mucchio.isEmpty()) northRef1 = sortPartial(i,i,i,"n",northRef1,northRef2);
			if(!mucchio.isEmpty()) eastRef1 = sortPartial(i,cols-1-i,i,"e",eastRef1,eastRef2);
			if(!mucchio.isEmpty()) southRef1 = sortPartial(rows-1-i,cols-1-i,i,"s",southRef1,southRef2);
			if(!mucchio.isEmpty()) westRef1 = sortPartial(rows-1-i,i,i,"w",westRef1,westRef2);
			if(northRef1 != null) northRef2 = puzzle[i+1][i].getId();
			if(eastRef1 != null) eastRef2 = puzzle[i][cols-2-i].getId();
			if(southRef1 != null) southRef2 = puzzle[rows-2-i][cols-1-i].getId();
			if(westRef1 != null) westRef2 = puzzle[rows-1-i][i+1].getId();
		}
	}	
	
	private void sortY(){
		if(cols == 1)
			sortPartial(0,0,0,"e",null,null);
		int limit = cols/2;
		if(cols % 2 == 1) limit++; 
		String northRef1 = null, northRef2 = null;
		String eastRef1 = null, eastRef2 = null;
		String southRef1 = null, southRef2 = null;
		String westRef1 = null, westRef2 = null;
		for(int i=0; i < limit; i++){
			if(!mucchio.isEmpty()) eastRef1 = sortPartial(i,cols-1-i,i,"e",eastRef1,eastRef2);
			if(!mucchio.isEmpty()) northRef1 = sortPartial(i,i,i,"n",northRef1,northRef2);
			if(!mucchio.isEmpty()) westRef1 = sortPartial(rows-1-i,i,i,"w",westRef1,westRef2);
			if(!mucchio.isEmpty()) southRef1 = sortPartial(rows-1-i,cols-1-i,i,"s",southRef1,southRef2);
			System.out.println();
			
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
	
	private Piece getPiece(String side, String ref1, String ref2) {
		String init = "w";
		switch(side){
		case "e": init = "n";
			break;
		case "s": init = "e";
			break;
		case "w": init = "s";
			break;
		}
		Iterator<Piece> it = mucchio.iterator();
		int index = 0;
		while(it.hasNext()){
			Piece current = it.next();
			String strSide = current.getAdjacent(side);
			String strInit = current.getAdjacent(init);
			if(ref2 == null && ref1 == null 
			  && strInit == null && strSide == null) {
				System.out.print(current + "\n");
				mucchio.remove(index);
				return current;
			}
			else{
				System.out.print(current);
				if(ref1 != null && ref2 != null &&
				 ref1.equals(strSide) && ref2.equals(strInit)) {
					mucchio.remove(index);
					return current;
				}
			}
			index++;
		}
		return null;
	}
	
	private String sortPartial(int row,int col,int iter,String side,String ref1, String ref2) {
		int mX = 0, mY = 1;
		if(side == "s") mY = -1;
		if(side == "w" || side == "e"){
			mY = 0;
			if(side == "w") mX = -1;
			else			mX = 1;
		}
		int limit = cols-iter-1;
		if(side=="e" || side=="w")
			limit = rows-iter-1;
		Piece piece = getPiece(side,ref1,ref2);
		System.out.print(piece);
		puzzle[row][col] = piece;
		String next = piece.getAdjacent(next(side));
		for(int i = 1; i < limit; i++){
			boolean found = false;
			Iterator<Piece> it = mucchio.iterator();
			int index = 0;
			while(it.hasNext() && !found){
				piece = it.next();
				if(next.equals(piece.getId())){
					puzzle[row+i*mX][col+i*mY] = piece;
					mucchio.remove(index);
					next = piece.getAdjacent(next(side));
					found = true;
				}
				index++;
			}
		}
		if(rows == 1 || cols == 1 || limit < 1) return null;
		return (puzzle[row+mX][col+mY]).getId();
	}
	
	private String next(String side){
		switch(side){
		case "w": return "n";
		case "e": return "s";
		case "n": return "e";
		case "s": return "w";
		}
		return null;
	}
	
	@Override
	public void write(Path path) {
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
}
