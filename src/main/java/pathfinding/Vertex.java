package pathfinding;

public class Vertex
{
    public final int x, y;

    public Vertex( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
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
        final Vertex other = (Vertex)obj;
        if( this.x != other.x ) {
            return false;
        }
        if( this.y != other.y ) {
            return false;
        }
        return true;
    }
    
}
