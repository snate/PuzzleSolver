import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author svalle
 *
 */
public class Puzzle implements GruppoOrdinabile {
	private int rows;
	private int cols;
	private static Charset charset = StandardCharsets.UTF_8;
	private static class Piece {
		private String id;
		private String north;
		private String south;
		private String east;
		private String west;
		private Piece northRef;
		private Piece southRef;
		private Piece westRef;
		private Piece eastRef;

		public String getId()      { return id; }
		public String getNorth()   { return north; }
		public String getSouth()   { return south; }
		public String getWest()    { return west; }
		public String getEast()    { return east;	}
		public Piece getNorthRef() { return northRef; }
		public void setNorthRef(Piece p) {northRef = p;}
		public Piece getSouthRef() { return southRef; }
		public void setSouthRef(Piece p) {southRef = p;}
		public Piece getWestRef()  { return westRef;  }
		public void setWestRef(Piece p) {westRef = p;}
		public Piece getEastRef()  { return eastRef;  }
		public void setEastRef(Piece p) {eastRef = p;}
	}

	private ArrayList<Piece> mucchio;
	private Piece[][] puzzle;
	
	private Piece getPieceMucchio(String id){
		Iterator<Piece> it = mucchio.iterator();
		int index=0;
		while(it.hasNext()){
			Piece current = it.next();
			if(current.getId()==id){
				mucchio.remove(index);
				return current;
			}
			index++;
		}
		return null;
	}
	
	private Piece getPiecePuzz(String id) {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				Piece current = puzzle[i][j]; 
				if(current.getId()==id)
					return current;
			}
		}
		return null;
	}
	
	private String convert(Path path){
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(path,
			charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return content.toString();
	}
	
	@Override
	public void fill(Path path) {
		String content = convert(path);
		System.out.println(content);
	}
	
	@Override
	public void sort() {
		//fai cose
	}
	
	@Override
	public void write(Path path) {
		//scrivi
	}
}