package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pathfinder
{
    private static final Logger logger = LoggerFactory.getLogger( Pathfinder.class );
    
    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target ) {
//        logger.info( "Search from: [" + origin + "] to [" + target + "]" );
//        logger.info( "    px wide: " + nodes.length + "px high: " + nodes[0].length );

        clear( nodes );

        Node startNode = nodes[origin.y][origin.x];
        startNode.origin = true;
        startNode.visited = true;
        startNode.cost = 0;

        Node targetNode = nodes[target.y][target.x];
        targetNode.target = true;

        Node currentNode = startNode;

        while( currentNode != null ) {
            logger.info( "currentNode: row: " + currentNode.matrixLocation.y + " col: " + currentNode.matrixLocation.x );
            
            List<Node> adjacent = getAdjacentNodes( nodes, currentNode );
            for( Node adjacentNode : adjacent ) {
                double currentCost = currentNode.cost;
                double graphCost = Vertex.distance( currentNode.location, adjacentNode.location );
                double newCost = currentCost + graphCost;
                
                if( adjacentNode.cost == -1 || adjacentNode.cost > newCost ) {
                    adjacentNode.cost = newCost;
                    adjacentNode.previous = currentNode;
                }
                
                if( currentNode.matrixLocation.x == 9 ){}
            }

            currentNode.visited = true;

            if( targetNode.cost != -1 ) {
                break;
            }
            else {
                currentNode = getNextNode( nodes );
            }
            
            print(nodes);
        }

        if( targetNode.cost != -1 ) {
            return walkBackwards( targetNode );
        }
        else {
            return null;
        }
    }
    
    public static List<Node> getAdjacentNodes(Node[][]nodes, Node current){
        Vertex[] candidates = {
            new Vertex( current.matrixLocation.y+1, current.matrixLocation.x ),
            new Vertex( current.matrixLocation.y+1, current.matrixLocation.x+1 ),
            new Vertex( current.matrixLocation.y+1, current.matrixLocation.x-1 ),
            new Vertex( current.matrixLocation.y,   current.matrixLocation.x+1 ),
            new Vertex( current.matrixLocation.y,   current.matrixLocation.x-1 ),
            new Vertex( current.matrixLocation.y-1, current.matrixLocation.x ),
            new Vertex( current.matrixLocation.y-1, current.matrixLocation.x+1 ),
            new Vertex( current.matrixLocation.y-1, current.matrixLocation.x-1 ),
        };
    
        List<Node> result = new ArrayList( candidates.length );
        for( int i = 0; i < candidates.length; i++ ){
            Vertex candidate = candidates[i];
            if( candidate.y >= 0 && candidate.y < nodes.length ){
                if( candidate.x >= 0 && candidate.x < nodes[0].length ){
                    Node node = nodes[candidate.y][candidate.x];
                    if( node.traversable ){
                        result.add(node);
                    }
                }
            }
        }
        return result;
    }

    public static Node clear( Node[][] nodes ){
        Node next = null;
        for( int row = 0; row < nodes.length; row++ ){
            for( int col = 0; col < nodes[row].length; col++ ){
                Node node = nodes[row][col];
                node.cost = -1;
                node.origin = false;
                node.target = false;
                node.visited = false;
                node.clear = false;
                node.path = false;
                node.previous = null;
            }
        }
        
        return next;
    }
    
    public static Node getNextNode( Node[][] nodes ){
        Node next = null;
        for( int row = 0; row < nodes.length; row++ ){
            for( int col = 0; col < nodes[row].length; col++ ){
                Node node = nodes[row][col];
                
                if( !node.visited && node.traversable && node.cost != -1 ){
                    if( next == null || next.cost > node.cost ){
                        next = node;
                    }
                }
            }
        }
        
        return next;
    }

    private static List<Node> walkBackwards( Node targetNode ){
        List<Node> path = new ArrayList();
        path.add( targetNode );
        
        Node previous = targetNode.previous;
        while( previous != null ){
            path.add( previous );
            previous = previous.previous;
        }
        
        Collections.reverse( path );
        return path;
    }

    public static void print( Node[][] nodes ) {
        String result = "\n";
        for( int row = 0; row < nodes.length; row++ ) {
            for( int col = 0; col < nodes[row].length; col++ ) {
                Node node = nodes[row][col];

                if( !node.traversable ) {
                    result += "[!]";
                }
                else if( node.origin ) {
                    result += "[s]";
                }
                else if( node.target ) {
                    result += "[t]";
                }
                else if( node.path ) {
                    result += "[*]";
                }
                else if( node.clear ) {
                    result += "[ ]";
                }
                else if( node.visited ) {
                    result += "[v]";
                    //result += "[" + node.dist + "]";
                }
                else if( node.cost != -1 ) {
                    result += "[?]";
                }
                else {
                    result += "[ ]";
                }
            }
            result += "\n";
        }
        logger.info( result );
    }

    public static void printPath( Node[][] nodes, List<Node> path ){
        if( path == null ){
            logger.error( "No path found" );
            return;
        }

        for( int row = 0; row < nodes.length; row++ ){
            for( int col = 0; col < nodes[row].length; col++ ){
                Node node = nodes[row][col];
                node.clear = true;
            }
        }

        for( Node node : path ){
            node.clear = false;
            node.path = true;
        }

        print( nodes );
    }
}
