package pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pathfinder {
    private static final Logger logger = LoggerFactory.getLogger( Pathfinder.class );

    private interface CostCalculator {
        public double getCost( Node currentNode, Node adjacentNode, Node targetNode );

    }

    private static CostCalculator dijkstraCC = new CostCalculator() {
        @Override
        public double getCost( Node currentNode, Node adjacentNode, Node targetNode ) {
            double currentCost = currentNode.cost;
            double graphCost = Vertex.distance( currentNode.location, adjacentNode.location );
            double newCost = currentCost + graphCost;
            return newCost;
        }
    };

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

    /**
     * Implements functionality common to several pathfinding algorithms, like
     * Dijkstra's, Greedy Best First, and A*.
     *
     * @param nodes         The search space
     * @param origin        the location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * @param target        the location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * @param calculator    calculates the distance between two nodes
     * @param returnFast    if true, returns as soon as any path from origin to
     *                      target is found;
     *                      this is faster, but does not guarentee a lowest-cost path. If false,
     *                      the result will be exhaustive and guarentee a lowest-cost path, but
     *                      the runtime may be significantly higher.
     * @param allowDiagonal if true, the algorithm may contain diagonal
     *                      movements. If
     *                      false, the path will contain no diagonal movements.
     * @return A path from origin to target, or null if no such path exists.
     */
    private static List<Node> generic( Node[][] nodes,
                                       Vertex origin, Vertex target,
                                       CostCalculator calculator,
                                       boolean returnFast,
                                       boolean allowDiagonal ) {
        //logger.info( "Search from: " + origin + " to " + target );

        clear( nodes );

        Node startNode = nodes[origin.x][origin.y];
        startNode.origin = true;
        startNode.visited = true;
        startNode.cost = 0;

        Node targetNode = nodes[target.x][target.y];
        targetNode.target = true;

        if( origin.equals( target ) ) {
            List<Node> result = new ArrayList();
            result.add( targetNode );
            return result;
        }

        //print(nodes);
        Node currentNode = startNode;

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

    /**
     * An implementation of Dijkstra's Shortest Path algorithm.
     * This algorithm is exhaustive, and guarantees a lowest-cost path from
     * origin to target, if a path exists. The path returned will have a cost
     * equal to or less than any other path within the search space, but no
     * particular path is guaranteed to be returned. The path may contain
     * diagonals. This algorithm is deterministic, and will always return the 
     * same path for the same inputs.
     *
     * @param nodes  The search space
     * @param origin the location at which the path starts (e.g.
     *               nodes[origin.x][origin.y]
     * @param target the location at which the path ends (e.g.
     *               nodes[target.x][target.y]
     * @return A path from origin to target, or null if no such path exists.
     */
    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target ) {
        return dijkstra( nodes, origin, target, true );
    }

    /**
     * An implementation of Dijkstra's Shortest Path algorithm, which can be
     * configured to disallow diagonal movement.
     *
     * @param nodes         The search space
     * @param origin        the location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * @param target        the location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * @param allowDiagonal if false, the path returned will contain no diagonal
     *                      movements.
     * @return A lowest-cost path from origin to target, or null if no such path
     *         exists.
     */
    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target, boolean allowDiagonal ) {
        return generic( nodes, origin, target, dijkstraCC, false, allowDiagonal );
    }

    /**
     * An implementation of a best-first heuristic algorithm, which uses
     * straight-line distance to estimate the cost of a node, and returns as
     * soon as any path from origin to target is found. This path is *not* guaranteed
     * to be a lowest-cost path, but the execution time may be significantly
     * faster than Dijkstra's algorithm. The path may contain diagonals. This algorithm
     * is deterministic, and will always return the same path for the same
     * inputs.
     *
     * @param nodes  The search space
     * @param origin the location at which the path starts (e.g.
     *               nodes[origin.x][origin.y]
     * @param target the location at which the path ends (e.g.
     *               nodes[target.x][target.y]
     * @return The first path found from origin to target, or null if no such
     *         path exists.
     */
    public static List<Node> bestFirst( Node[][] nodes, Vertex origin, Vertex target ) {
        return bestFirst( nodes, origin, target, true );
    }

    /**
     * An implementation of a best-first heuristic algorithm, which uses
     * straight-line distance to estimate the cost of a node, returns as soon
     * as any path from origin to target is found, and can be configured to
     * disallow diagonal movement.
     *
     * @param nodes         The search space
     * @param origin        the location at which the path starts (e.g.
     *                      nodes[origin.x][origin.y]
     * @param target        the location at which the path ends (e.g.
     *                      nodes[target.x][target.y]
     * @param allowDiagonal if true, the algorithm may contain diagonal
     *                      movements. If
     *                      false, the path will contain no diagonal movements.
     * @return The first path found from origin to target, or null if no such
     *         path exists.
     */
    public static List<Node> bestFirst( Node[][] nodes, Vertex origin, Vertex target, boolean allowDiagonal ) {
        return generic( nodes, origin, target, bestFirstCC, true, allowDiagonal );
    }

    /**
     * Returns a list of nodes touching the current node.
     *
     * @param nodes         the search space
     * @param current       the current node
     * @param allowDiagonal if true, the returned list may contain nodes
     *                      reachable
     *                      from current via diagonal movement; if false, the list
     *                      will only contain nodes reachable via single-axis movement.
     * @return A list of nodes touching the current node.
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
     * location,
     * and matric location as found.
     *
     * @param nodes the list of nodes
     */
    public static void clear( Node[][] nodes ) {
        for( int col = 0; col < nodes.length; col++ ) {
            for( int row = 0; row < nodes[col].length; row++ ) {
                Node node = nodes[col][row];
                node.cost = -1;
                node.origin = false;
                node.target = false;
                node.visited = false;
                node.clear = false;
                node.path = false;
                node.previous = null;
            }
        }
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

    /**
     * Creates a list of nodes, using node.previous to walk the chain.
     *
     * @param targetNode the last node in the chain
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

}
