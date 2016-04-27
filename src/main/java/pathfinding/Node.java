package pathfinding;

public class Node {
    public Vertex location;
    public Vertex matrixLocation;
    public double cost = -1;
    public boolean origin = false;
    public boolean target = false;
    public boolean traversable = true;
    public boolean visited;
    public boolean clear = false;
    public boolean path = false;

    public Node() {
        this( 0, 0, 0, 0 );
    }

    public Node( int x, int y, int matrixX, int matrixY ) {
        this( new Vertex( x, y ), 
              new Vertex( matrixX, matrixY ), 
              false, false );
    }

    public Node( Vertex location, Vertex matrixLocation, boolean origin, boolean target ) {
        this.location = location;
        this.matrixLocation = matrixLocation;
        this.origin = origin;
        this.target = target;
    }
}
