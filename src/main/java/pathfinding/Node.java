package pathfinding;

/**
 * A node in the search space used by the Pathfinder's algorithms.
 * 
 * Each Node contains a "real-world" location (node.location), as well as a
 * search-space location (node.searchSpaceLocation). That is, the following
 * real-word search space:
 * 
 * (0,0)      (5,0)
 * (0,5)      (5,5)
 * (0,10)     (5,10)
 * 
 * Will be represented by the following nodes:
 * 
 * [ location: 0,0  searchSpace: 0,0 ]  [ location: 5,0  searchSpace: 1,0 ]
 * [ location: 0,5  searchSpace: 0,1 ]  [ location: 5,5  searchSpace: 1,1 ]
 * [ location: 0,10 searchSpace: 0,2 ]  [ location: 5,10 searchSpace: 1,2 ]
 * 
 * The origin and target nodes should be marked using the appropriate field. Nodes
 * that cannot appear in the resulting path can be marked with node.traversable = false.
 * 
 * @author galvint
 */
public class Node {
    public Vertex location;
    public Vertex searchSpaceLocation;
    public boolean origin = false;
    public boolean target = false;
    public boolean traversable = true;
    
    public double cost = -1;
    public boolean visited; //used during processing to prevent loopback
    public boolean clear = false; //used when printing the final path
    public boolean path = false; //used to indicate final path
    public Node previous;

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
        this.searchSpaceLocation = matrixLocation;
        this.origin = origin;
        this.target = target;
    }
}
