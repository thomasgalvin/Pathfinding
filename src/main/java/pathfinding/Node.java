package pathfinding;

public class Node {
    public Vertex location;
    public double cost = -1;
    public boolean origin = false;
    public boolean target = false;
    public boolean traversable = true;
    public boolean visited;
    public boolean clear = false;
    public boolean path = false;

    public Node() {
        this( 0, 0 );
    }

    public Node( int x, int y ) {
        this( new Vertex( x, y ), false, false );
    }

    public Node( Vertex location, boolean origin, boolean target ) {
        this.location = location;
        this.origin = origin;
        this.target = target;
    }
}
