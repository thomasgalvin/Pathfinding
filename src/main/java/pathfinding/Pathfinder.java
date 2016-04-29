package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A collection of pathfinding / graph traversal algorithms.
 * 
 * Each of the search methods provided can be configured to return fast 
 * (as soon as any valid path between the origin and the target is found) or
 * to be exhaustive (guaranteeing that the path returned is equal to or 
 * lower-cost than any other path between the two nodes within the search
 * space).
 * 
 * Each method can also be configured to allow or disallow diagonal movement 
 * between nodes.
 * 
 * No particular path is guaranteed to be returned, but the algorithms are
 * deterministic (each method will always return the same result for the 
 * same inputs).
 * 
 * In all cases, if no valid path exists, null will be returned.
 * 
 * In general, the performance of the algorithms is (from best to worst)
 * 
 * 1. Best-first
 * 2. Dijkstra's
 * 3. A*
 * 
 * However, a return-fast algorithm will almost always be faster than an 
 * exhaustive search, especially over large search spaces. 
 * 
 * Similarly, an algorithm that allows diagonal traversal will generally be
 * faster than one that does not.
 * 
 * This class also contains a method for generating a search space of a given
 * width and height, and mapped to "real world" coordinates.
 */
public class Pathfinder {
    private static final Logger logger = LoggerFactory.getLogger( Pathfinder.class );
    
    /// Best-First Algorithm ///
    
    /**
     * An implementation of a best-first, heuristic pathfinding algorithm.
     * 
     * In general, this is the best-performing algorithm in Pathfinder. The
     * most efficient use is to return fast and allow diagonal movements, 
     * though return fast does not guarantee a lowest-cost path.
     * 
     * For more information on best-first search, see:
     * https://en.wikipedia.org/wiki/Best-first_search
     * 
     * @param nodes         The search space
     * 
     * @param origin        The location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        The location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @param returnFast    If true, the algorithm will return as soon as any
     *                      valid path between origin and target is found. This
     *                      may have a much faster execution time, but is not
     *                      guaranteed to return a lowest-cost path.
     * 
     *                      If false, the algorithm will be exhaustive (all 
     *                      reachable nodes will be visited and evaluated), 
     *                      and the returned path is guaranteed to be equal to
     *                      or lower in cost than any other valid path, but the
     *                      execution time may be much higher.
     * 
     * @param allowDiagonal If true, the returned path may contain diagonal 
     *                      movements. If false, the returned path will not
     *                      contain diagonal movements.
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> bestFirst( Node[][] nodes, Vertex origin, Vertex target, boolean returnFast, boolean allowDiagonal ) {
        return genericSearch( nodes, origin, target, bestFirstCC, returnFast, allowDiagonal );
    }
    
    /**
     * Searches nodes to find origin and target, then calls bestFirst with
     * returnFast = true and allowDiagonal = true.
     * 
     * @param nodes The search space
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> bestFirst( Node[][] nodes ){
        Vertex origin = findOrigin( nodes );
        Vertex target = findTarget( nodes );
        return bestFirst( nodes, origin, target, true, true );
    }
    
    /**
     * Calls bestFirst with returnFast = true and allowDiagonal = true.
     * 
     * @param nodes The search space
     * 
     * @param origin        The location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        The location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> bestFirst( Node[][] nodes, Vertex origin, Vertex target ){
        return bestFirst( nodes, origin, target, true, true );
    }
    
    
    /// Dijkstra's Algorithm ///
    
    /**
     * An implementation of Dijkstra's shortest path algorithm.
     * 
     * In general, this algorithm is less performant than best-first, but
     * more performant than A*.
     * 
     * For more information on this algorithm, see: 
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     * 
     * @param nodes         The search space
     * 
     * @param origin        The location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        The location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @param returnFast    If true, the algorithm will return as soon as any
     *                      valid path between origin and target is found. This
     *                      may have a much faster execution time, but is not
     *                      guaranteed to return a lowest-cost path.
     * 
     *                      If false, the algorithm will be exhaustive (all 
     *                      reachable nodes will be visited and evaluated), 
     *                      and the returned path is guaranteed to be equal to
     *                      or lower in cost than any other valid path, but the
     *                      execution time may be much higher.
     * 
     * @param allowDiagonal If true, the returned path may contain diagonal 
     *                      movements. If false, the returned path will not
     *                      contain diagonal movements.
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target, boolean returnFast, boolean allowDiagonal ) {
        return genericSearch( nodes, origin, target, dijkstraCC, returnFast, allowDiagonal );
    }
    
    /**
     * Searches nodes to find origin and target, then calls dijkstra with
     * returnFast = false and allowDiagonal = true.
     * 
     * @param nodes The search space
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> dijkstra( Node[][] nodes ){
        Vertex origin = findOrigin( nodes );
        Vertex target = findTarget( nodes );
        return dijkstra( nodes, origin, target, false, true );
    }
    
    /**
     * Calls dijkstra with returnFast = false and allowDiagonal = true.
     * 
     * @param nodes The search space
     * 
     * @param origin        The location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        The location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target ){
        return dijkstra( nodes, origin, target, false, true );
    }
    
    /// A* Algorithm ///
    
    /**
     * An implementation of the A* pathfinding algorithm. 
     * 
     * In general, this is the least performant algorithm in Pathfinder.
     * 
     * For more information on A*, see:
     * https://en.wikipedia.org/wiki/A*_search_algorithm
     * 
     * @param nodes         The search space
     * 
     * @param origin        The location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        The location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @param returnFast    If true, the algorithm will return as soon as any
     *                      valid path between origin and target is found. This
     *                      may have a much faster execution time, but is not
     *                      guaranteed to return a lowest-cost path.
     * 
     *                      If false, the algorithm will be exhaustive (all 
     *                      reachable nodes will be visited and evaluated), 
     *                      and the returned path is guaranteed to be equal to
     *                      or lower in cost than any other valid path, but the
     *                      execution time may be much higher.
     * 
     * @param allowDiagonal If true, the returned path may contain diagonal 
     *                      movements. If false, the returned path will not
     *                      contain diagonal movements.
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> astar( Node[][] nodes, 
                                    Vertex origin, Vertex target, 
                                    boolean returnFast,
                                    boolean allowDiagonal ) {
        Setup setup = setup( nodes, origin, target );
        if( setup.result != null ){
            return setup.result;
        }
        
        List<Node> open = new ArrayList();
        List<Node> closed = new ArrayList();
        
        open.add( setup.startNode );
        Node targetNode = setup.targetNode;
        
        while( !open.isEmpty() ){
            Collections.sort( open, astarComparator );
            Node currentNode = open.remove(0);
            closed.add( currentNode );
            
            List<Node> adjacent = getAdjacentNodes( nodes, currentNode, allowDiagonal );
            adjacent.removeAll( closed );
            
            for( Node adjacentNode : adjacent ){
                double graphCost = Vertex.distance( currentNode.location, adjacentNode.location );
                double heurCost = Vertex.distance( currentNode.location, adjacentNode.location );
                double newCost = currentNode.cost + graphCost + heurCost;
                
                if( adjacentNode.cost == -1 || adjacentNode.cost > newCost ) {
                    adjacentNode.cost = newCost;
                    adjacentNode.previous = currentNode;
                }
                
                if( !open.contains( adjacentNode ) ){
                    open.add( adjacentNode );
                }
            }
            
            if( returnFast && currentNode.target ){
                return walkBackwards( currentNode );
            }
        }
        
        if( targetNode.cost != -1 ) {
            return walkBackwards( targetNode );
        }
        else {
            return null;
        }
    }
    
    /**
     * Searches nodes to find origin and target, then calls astar with
     * returnFast = true and allowDiagonal = true.
     * 
     * @param nodes The search space
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> astar( Node[][] nodes ){
        Vertex origin = findOrigin( nodes );
        Vertex target = findTarget( nodes );
        return astar( nodes, origin, target, true, true );
    }
    
    /**
     * Calls astar with returnFast = true and allowDiagonal = true.
     * 
     * @param nodes The search space
     * 
     * @param origin        The location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        The location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @return              A valid path between origin and target, or null if
     *                      no such path exists.
     */
    public static List<Node> astar( Node[][] nodes, Vertex origin, Vertex target ){
        return astar( nodes, origin, target, true, true );
    }
    
    /// utilities ///

    /**
     * Returns a list of traversable nodes touching the current node.
     * 
     * @param nodes         the search space
     * 
     * @param current       the current node
     * 
     * @param allowDiagonal if true, the returned list may contain nodes
     *                      reachable from current via diagonal movement; if false, the list
     *                      will only contain nodes reachable via single-axis movement.
     * 
     * @return A list of traversable nodes touching the current node.
     */
    public static List<Node> getAdjacentNodes( Node[][] nodes, Node current, boolean allowDiagonal ) {
        Vertex[] candidates;

        if( allowDiagonal ) {
            candidates = new Vertex[]{
                new Vertex( current.searchSpaceLocation.x + 1, current.searchSpaceLocation.y ),
                new Vertex( current.searchSpaceLocation.x + 1, current.searchSpaceLocation.y + 1 ),
                new Vertex( current.searchSpaceLocation.x + 1, current.searchSpaceLocation.y - 1 ),
                new Vertex( current.searchSpaceLocation.x, current.searchSpaceLocation.y + 1 ),
                new Vertex( current.searchSpaceLocation.x, current.searchSpaceLocation.y - 1 ),
                new Vertex( current.searchSpaceLocation.x - 1, current.searchSpaceLocation.y ),
                new Vertex( current.searchSpaceLocation.x - 1, current.searchSpaceLocation.y + 1 ),
                new Vertex( current.searchSpaceLocation.x - 1, current.searchSpaceLocation.y - 1 ), };
        }
        else {
            candidates = new Vertex[]{
                new Vertex( current.searchSpaceLocation.x + 1, current.searchSpaceLocation.y ),
                new Vertex( current.searchSpaceLocation.x, current.searchSpaceLocation.y + 1 ),
                new Vertex( current.searchSpaceLocation.x, current.searchSpaceLocation.y - 1 ),
                new Vertex( current.searchSpaceLocation.x - 1, current.searchSpaceLocation.y ), };
        }

        List<Node> result = new ArrayList( candidates.length );
        for( int i = 0; i < candidates.length; i++ ) {
            Vertex candidate = candidates[i];
            if( candidate.x >= 0 && candidate.x < nodes.length ) {
                if( candidate.y >= 0 && candidate.y < nodes[0].length ) {
                    Node node = nodes[candidate.x][candidate.y];
                    if( node.traversable ) {
                        result.add( node );
                    }
                }
            }
        }
        return result;
    }

    /**
     * Clears out all nodes' "working" data, while leaving traversable,
     * location, and search space location as found.
     *
     * @param nodes the list of nodes
     */
    public static void clear( Node[][] nodes ) {
        for( int col = 0; col < nodes.length; col++ ) {
            for( int row = 0; row < nodes[col].length; row++ ) {
                Node node = nodes[col][row];
                node.cost = -1;
                node.visited = false;
                node.clear = false;
                node.path = false;
                node.previous = null;
            }
        }
    }

    /**
     * Creates a list of nodes, using Node.previous to walk the chain.
     *
     * @param targetNode the last node in the chain
     * 
     * @return a list of nodes, from origin to target (in that order), based
     *         upon each node's previous field.
     */
    public static List<Node> walkBackwards( Node targetNode ) {
        if( targetNode.previous == null ) {
            return null;
        }

        List<Node> path = new ArrayList();
        path.add( targetNode );

        Node previous = targetNode.previous;
        while( previous != null ) {
            path.add( previous );
            previous = previous.previous;
        }

        Collections.reverse( path );
        return path;
    }

    /**
     * Prints out a field of nodes to the console.
     * The ouTput uses the following symbols:
     * [!] - non-traversable nodes
     * [o] - the origin
     * [t] - the target
     * [*] - nodes on the path from origin to target
     * [v] - visited nodes
     * [?] - unvisited nodes
     * [ ] - all other nodes
     * <p>
     * If this method is being used to print a path, visited and unvisited nodes
     * will not be specially marked, and instead show as [ ].
     *
     * @param nodes the search space
     */
    public static void print( Node[][] nodes ) {
        int cols = nodes.length;
        int rows = nodes[0].length;
        StringBuilder[] rowStrings = new StringBuilder[ rows ];
        for( int row = 0; row < rowStrings.length; row++ ) {
            rowStrings[row] = new StringBuilder();
        }

        for( int row = 0; row < rows; row++ ) {
            for( int col = 0; col < cols; col++ ) {
                Node node = nodes[col][row];

                if( !node.traversable ) {
                    rowStrings[row].append( "[!]" );
                }
                else if( node.origin && node.target ) {
                    rowStrings[row].append( "[*]" );
                }
                else if( node.origin ) {
                    rowStrings[row].append( "[o]" );
                }
                else if( node.target ) {
                    rowStrings[row].append( "[t]" );
                }
                else if( node.path ) {
                    rowStrings[row].append( "[*]" );
                }
                else if( node.clear ) {
                    rowStrings[row].append( "[ ]" );
                }
                else if( node.visited ) {
                    rowStrings[row].append( "[v]" );
                }
                else if( node.cost != -1 ) {
                    rowStrings[row].append( "[?]" );
                }
                else {
                    rowStrings[row].append( "[ ]" );
                }
            }
        }

        StringBuilder result = new StringBuilder();
        result.append( "\n" );
        for( StringBuilder row : rowStrings ) {
            result.append( row.toString() );
            result.append( "\n" );
        }
        result.append( "\n" );
        logger.info( result.toString() );
    }

    /**
     * Prints out the given path over the search space.
     *
     * @param nodes the search space
     * @param path  the path
     */
    public static void printPath( Node[][] nodes, List<Node> path ) {
        if( path == null ) {
            logger.error( "No path found" );
            return;
        }

        for( int col = 0; col < nodes.length; col++ ) {
            for( int row = 0; row < nodes[col].length; row++ ) {
                Node node = nodes[col][row];
                node.clear = true;
                node.path = false;
            }
        }

        for( Node node : path ) {
            node.path = true;
        }

        print( nodes );
    }

    /**
     * Creates a search space.
     *
     * @param width  the width of the search space, in nodes
     * @param height the height of the search space, in nodes
     * @param startX the "real-world" starting x coordinate
     * @param startY the "real-world" starting y coordinate
     * @param stepX  the increment of each "real-world" x coordinate between
     *               nodes.
     * @param stepY  the increment of each "real-world" y coordinate between
     *               nodes.
     * @return the search space
     */
    public static Node[][] makeNodes( int width, int height,
                                      int startX, int startY,
                                      int stepX, int stepY ) {
        Node[][] nodes = new Node[ width ][ height ];
        for( int x = 0; x < width; x++ ) {
            for( int y = 0; y < height; y++ ) {
                int xx = startX + ( x * stepX );
                int yy = startY + ( y * stepY );
                Node node = new Node( xx, yy, x, y );
                nodes[x][y] = node;
            }
        }
        return nodes;
    }

    public static Vertex findOrigin( Node[][] nodes ){
        for( int x = 0; x < nodes.length; x++ ){
            for( int y = 0; y < nodes.length; y++ ){
                if( nodes[x][y].origin ){
                    return new Vertex(x,y);
                }
            }
        }
        return null;
    }
    
    public static Vertex findTarget( Node[][] nodes ){
        for( int x = 0; x < nodes.length; x++ ){
            for( int y = 0; y < nodes.length; y++ ){
                if( nodes[x][y].target ){
                    return new Vertex(x,y);
                }
            }
        }
        return null;
    }
    
    
    
    /// internal utilities ///
    
    private interface CostCalculator {
        public double getCost( Node currentNode, Node adjacentNode, Node targetNode );

    }
    
    private static class Setup{
        public Node startNode;
        public Node targetNode;
        public List<Node> result;

        public Setup( Node startNode, Node targetNode, List<Node> result ) {
            this.startNode = startNode;
            this.targetNode = targetNode;
            this.result = result;
        }
    }
    
    private static CostCalculator bestFirstCC = new CostCalculator() {
        @Override
        public double getCost( Node currentNode, Node adjacentNode, Node targetNode ) {
            double currentCost = currentNode.cost;
            double graphCost = Vertex.distance( currentNode.location, adjacentNode.location );
            double heurCost = Vertex.distance( currentNode.location, adjacentNode.location );
            double newCost = currentCost + graphCost + heurCost;
            return newCost;
        }
    };
    
    private static CostCalculator dijkstraCC = new CostCalculator() {
        @Override
        public double getCost( Node currentNode, Node adjacentNode, Node targetNode ) {
            double currentCost = currentNode.cost;
            double graphCost = Vertex.distance( currentNode.location, adjacentNode.location );
            double newCost = currentCost + graphCost;
            return newCost;
        }
    };
    
    private static Comparator astarComparator = new Comparator(){
        @Override
        public int compare( Object o1, Object o2 ) {
            Node node1 = (Node)o1;
            Node node2 = (Node)o2;
            
            if( node2.cost < node1.cost ){
                return 1;
            }
            else if( node2.cost > node1.cost ){
                return -1;
            }
            else{
                return 0;
            }
        }
    };
    
    /**
     * Implements functionality common to several pathfinding algorithms, such
     * as Dijkstra's and the Greedy Best First Heuristic algorithm.
     *
     * @param nodes         The search space
     * 
     * @param origin        the location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * 
     * @param target        the location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * 
     * @param calculator    calculates the distance between two nodes
     * 
     * @param returnFast    if true, returns as soon as any path from origin to
     *                      target is found; this is computationally faster, 
     *                      but does not guarantee a lowest-cost path. 
     * 
     *                      If false, the search will be exhaustive and guarantee 
     *                      a lowest-cost path, but the run time may be 
     *                      significantly higher.
     * @param allowDiagonal if true, the returned path may contain diagonal
     *                      movements. If false, the path will contain no 
     *                      diagonal movements.
     * 
     * @return A path from origin to target, or null if no such path exists.
     */
    private static List<Node> genericSearch( Node[][] nodes,
                                             Vertex origin, Vertex target,
                                             CostCalculator calculator,
                                             boolean returnFast,
                                             boolean allowDiagonal ) {
        //logger.info( "Search from: " + origin + " to " + target );

        Setup setup = setup( nodes, origin, target );
        if( setup.result != null ){
            return setup.result;
        }

        //print(nodes);
        Node currentNode = setup.startNode;
        Node targetNode = setup.targetNode;

        while( currentNode != null ) {
            //logger.info( "currentNode: row: " + currentNode.matrixLocation.y + " col: " + currentNode.matrixLocation.x );

            List<Node> adjacent = getAdjacentNodes( nodes, currentNode, allowDiagonal );
            for( Node adjacentNode : adjacent ) {
                double newCost = calculator.getCost( currentNode, adjacentNode, targetNode );

                if( adjacentNode.cost == -1 || adjacentNode.cost > newCost ) {
                    adjacentNode.cost = newCost;
                    adjacentNode.previous = currentNode;
                }
            }

            currentNode.visited = true;

            if( returnFast && targetNode.cost != -1 ) {
                break;
            }
            else {
                currentNode = getNextNode( nodes );
            }

            //print(nodes);
        }

        //print(nodes);
        if( targetNode.cost != -1 ) {
            return walkBackwards( targetNode );
        }
        else {
            return null;
        }
    }
    
    private static Setup setup( Node[][] nodes, Vertex origin, Vertex target ){
        clear( nodes );

        Node startNode = nodes[origin.x][origin.y];
        startNode.origin = true;
        startNode.visited = true;
        startNode.cost = 0;

        Node targetNode = nodes[target.x][target.y];
        targetNode.target = true;

        List<Node> result = null;
        if( origin.equals( target ) ) {
            result = new ArrayList();
            result.add( targetNode );
        }
        
        return new Setup( startNode, targetNode, result );
    }
    
    /**
     * Finds the lowest-cost, traversable, unvisited node in the list. This is
     * used to determine which branch to expand first.
     *
     * @param nodes the list of nodes
     * @return the next node that should be expanded.
     */
    private static Node getNextNode( Node[][] nodes ) {
        Node next = null;

        for( int col = 0; col < nodes.length; col++ ) {
            for( int row = 0; row < nodes[col].length; row++ ) {
                Node node = nodes[col][row];

                if( !node.visited && node.traversable && node.cost != -1 ) {
                    if( next == null || next.cost > node.cost ) {
                        next = node;
                    }
                }
            }
        }

        return next;
    }
}
