package pathfinding;

/**
 * Represents a point in 2D space.
 */
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
    
    @Override
    public String toString(){
        return "Vertext[" + x + "," + y + "]";
    }
    
    /**
     * Calculates the distance between this point and the target.
     * @param vertex the target
     * @return the distance between this point and the target
     */
    public double distance( Vertex vertex ){
        return distance( this.x, this.y, vertex.x, vertex.y );
    }
    
    /**
     * Calculates the distance between two vertices.
     * @param one the first vertex
     * @param two the second vertex
     * @return the distance between the two
     */
    public static double distance( Vertex one, Vertex two ){
        return distance( one.x, one.y, two.x, two.y );
    }
    
    /**
     * Calculates the distance between two points (x1,y1) and (x2,y2)
     * @param x1 the first x coordinate
     * @param y1 the first y coordinate
     * @param x2 the second x coordinate
     * @param y2 the second y coordinate
     * @return the distance between the two points
     */
    public static double distance( int x1, int y1, int x2, int y2 ){
        double xx = x1 - x2;
        double yy = y1 - y2;
            
        xx *= xx;
        yy *= yy;
        
        double z = xx + yy;
        double result = Math.sqrt( z );
        return result;
    }
}
