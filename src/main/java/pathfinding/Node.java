package pathfinding;

public class Node
{
    public int x;
    public int y;
    public boolean visited;
    public int dist = Integer.MAX_VALUE;
    public boolean traversable = true;
    public boolean origin = false;
    public boolean target = false;
    public boolean clear = false;
    public boolean path = false;
    
    public Node(){
        this(0,0);
    }
    
    public Node(int x, int y){
        this(x, y, false, false);
    }
    
    public Node(int x, int y, boolean origin, boolean target){
        this.x = x;
        this.y = y;
        this.origin = origin;
        this.target = target;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.x;
        hash = 73 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final Node other = (Node)obj;
        if( this.x != other.x ) {
            return false;
        }
        if( this.y != other.y ) {
            return false;
        }
        return true;
    }
    
    
}
