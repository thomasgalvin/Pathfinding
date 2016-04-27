package pathfinding;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathFinder {
    private static final Logger logger = LoggerFactory.getLogger( PathFinder.class );

    public static List<Node> findPath( Node[][] nodes, Vertex origin, Vertex target ) {
        logger.info( "Search from: [" + origin + "] to [" + target + "]" );
        logger.info( "    px wide: " + nodes.length + "px high: " + nodes[0].length );
        
        Node startNode = nodes[origin.y][origin.x];
        startNode.origin = true;
        startNode.visited = true;
        startNode.dist = 0;

        Node targetNode = nodes[target.y][target.x];
        targetNode.target = true;

        Node currentNode = startNode;

        while( currentNode != null ) {
            int newDist = currentNode.dist + 1;
            List<Node> adjacent = getAdjacentNodes( nodes, currentNode );
            for( Node adjacentNode : adjacent ) {
                if( adjacentNode.dist == Integer.MAX_VALUE || adjacentNode.dist > newDist ) {
                    adjacentNode.dist = newDist;
                }
            }

            currentNode.visited = true;
            //print(nodes);

            if( targetNode.dist != Integer.MAX_VALUE ) {
                break;
            }
            else {
                currentNode = getNextNode( nodes );
            }
        }

        //print(nodes);
        
        if( targetNode.dist != Integer.MAX_VALUE ) {
            return walkBackwards( nodes, targetNode );
        }
        else {
            return null;
        }
    }
    
    public static List<Node> getAdjacentNodes(Node[][]nodes, Node current){
        Vertex[] candidates = {
            new Vertex( current.y+1, current.x ),
            new Vertex( current.y+1, current.x+1 ),
            new Vertex( current.y+1, current.x-1 ),
            new Vertex( current.y, current.x+1 ),
            new Vertex( current.y, current.x-1 ),
            new Vertex( current.y-1, current.x ),
            new Vertex( current.y-1, current.x+1 ),
            new Vertex( current.y-1, current.x-1 ),
        };
    
        List<Node> result = new ArrayList();
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

    public static Node getNextNode( Node[][] nodes ){
        Node next = null;
        for( int row = 0; row < nodes.length; row++ ){
            for( int col = 0; col < nodes[row].length; col++ ){
                Node node = nodes[row][col];
                if( !node.visited && node.traversable && node.dist != Integer.MAX_VALUE ){
                    if( next == null || next.dist > node.dist ){
                        next = node;
                    }
                }
            }
        }
        return next;
    }

    public static List<Node> walkBackwards( Node[][] nodes, Node targetNode ){
        List<Node> path = new ArrayList();
        path.add( targetNode );
        Node smallestNeighbor = getSmallestNeighbor(nodes, targetNode);
        while( smallestNeighbor != null ){
            path.add(smallestNeighbor);
            smallestNeighbor = getSmallestNeighbor(nodes, smallestNeighbor);
        }
        return path;
    }

    public static Node getSmallestNeighbor( Node[][] nodes, Node current ){
        Node smallest = null;
        List<Node> candidates = getAdjacentNodes(nodes, current);
        for( Node node : candidates ){
            if( node.dist < current.dist ){
                smallest = node;
            }
        }
        return smallest;
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
                else if( node.dist != Integer.MAX_VALUE ) {
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
