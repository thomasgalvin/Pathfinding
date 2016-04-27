package pathfinding;

public class Node {
    public Vertex location;
    public boolean visited;
    public double finalCost = -1;
    public boolean traversable = true;
    public boolean origin = false;
    public boolean target = false;
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

//    public void setCost( double graphCost, double heurCost, double finalCost ){
//        this.graphCost = graphCost;
//        this.heurCost = heurCost;
//        this.finalCost = finalCost;
//    }
    
}
