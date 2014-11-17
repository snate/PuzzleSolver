/**
 * Questa classe rappresenta un pezzo del puzzle senza riferimenti
 * @author svalle
 */
public class Piece {
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
	public Piece getSouthRef() { return southRef; }
	public Piece getWestRef()  { return westRef;  }
	public Piece getEastRef()  { return eastRef;  }

}
