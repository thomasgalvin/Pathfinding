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

    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target ) {
        return dijkstra( nodes, origin, target, true );
    }
    
    public static List<Node> dijkstra( Node[][] nodes, Vertex origin, Vertex target, boolean allowDiagonal ) {
        return generic( nodes, origin, target, dijkstraCC, false, allowDiagonal );
    }

    public static List<Node> bestFirst( Node[][] nodes, Vertex origin, Vertex target ) {
        return bestFirst( nodes, origin, target, true );
    }
    
    public static List<Node> bestFirst( Node[][] nodes, Vertex origin, Vertex target, boolean allowDiagonal ) {
        return generic( nodes, origin, target, bestFirstCC, true, allowDiagonal );
    }

    public static List<Node> getAdjacentNodes( Node[][] nodes, Node current, boolean allowDiagonal ) {
        Vertex[] candidates;
        
        if( allowDiagonal ){
            candidates = new Vertex[]{
                new Vertex( current.matrixLocation.x + 1, current.matrixLocation.y ),
                new Vertex( current.matrixLocation.x + 1, current.matrixLocation.y + 1 ),
                new Vertex( current.matrixLocation.x + 1, current.matrixLocation.y - 1 ),
                new Vertex( current.matrixLocation.x, current.matrixLocation.y + 1 ),
                new Vertex( current.matrixLocation.x, current.matrixLocation.y - 1 ),
                new Vertex( current.matrixLocation.x - 1, current.matrixLocation.y ),
                new Vertex( current.matrixLocation.x - 1, current.matrixLocation.y + 1 ),
                new Vertex( current.matrixLocation.x - 1, current.matrixLocation.y - 1 ), 
            };
        } 
        else {
            candidates = new Vertex[]{
                new Vertex( current.matrixLocation.x + 1, current.matrixLocation.y ),
                new Vertex( current.matrixLocation.x, current.matrixLocation.y + 1 ),
                new Vertex( current.matrixLocation.x, current.matrixLocation.y - 1 ),
                new Vertex( current.matrixLocation.x - 1, current.matrixLocation.y ),
            };
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

    public static Node clear( Node[][] nodes ) {
        Node next = null;
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

        return next;
    }

    public static Node getNextNode( Node[][] nodes ) {
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

    private static List<Node> walkBackwards( Node targetNode ) {
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
                    rowStrings[row].append( "[s]" );
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

}
