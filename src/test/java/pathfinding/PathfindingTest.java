package pathfinding;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author galvint
 */
public class PathfindingTest {
    private static final Logger logger = LoggerFactory.getLogger( PathfindingTest.class );

    public PathfindingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public static Node[][] makeNodes() {
        int width = 10;
        int height = 10;
        Node[][] nodes = new Node[ width ][ height ];
        for( int x = 0; x < width; x++ ) {
            for( int y = 0; y < height; y++ ) {
                Node node = new Node(x,y);
                nodes[x][y] = node;
            }
        }
        return nodes;
    }

    @Test
    public void testOne() throws Exception {
        Node[][] nodes = makeNodes();

        nodes[0][8].traversable = false;
        nodes[1][8].traversable = false;
        nodes[2][8].traversable = false;
        nodes[3][8].traversable = false;

        nodes[9][2].traversable = false;
        nodes[8][2].traversable = false;
        nodes[7][2].traversable = false;
        nodes[6][2].traversable = false;

        nodes[2][4].traversable = false;
        nodes[3][4].traversable = false;
        nodes[4][4].traversable = false;
        nodes[5][4].traversable = false;
        
        Vertex start = new Vertex(9,0);
        Vertex end = new Vertex(0,9);

        List<Node> dij = Pathfinder.dijkstra(nodes, start, end );
        logger.info( "Dijkstra" );
        Pathfinder.printPath( nodes, dij );
        
        List<Node> astar = Pathfinder.astar(nodes, start, end );
        logger.info( "A*" );
        Pathfinder.printPath( nodes, astar );
        
        logger.info( "Equal? " + dij.equals( astar ) );
    }

    @Test
    public void testTwo() throws Exception {
        Node[][] nodes = makeNodes();

        nodes[0][1].traversable = false;
        nodes[1][1].traversable = false;
        nodes[2][1].traversable = false;
        nodes[3][1].traversable = false;
        nodes[4][1].traversable = false;
        nodes[5][1].traversable = false;
        nodes[5][2].traversable = false;
        nodes[5][3].traversable = false;
        nodes[5][4].traversable = false;
        nodes[5][5].traversable = false;

        Vertex start = new Vertex(0,2);
        Vertex end = new Vertex(9,9);

        List<Node> dij = Pathfinder.dijkstra(nodes, start, end );
        logger.info( "Dijkstra" );
        Pathfinder.printPath( nodes, dij );
        
        List<Node> astar = Pathfinder.astar(nodes, start, end );
        logger.info( "A*" );
        Pathfinder.printPath( nodes, astar );
        
        logger.info( "Equal? " + dij.equals( astar ) );
    }

    @Test
    public void testThree() throws Exception {
        Node[][] nodes = makeNodes();

        nodes[0][1].traversable = false;
        nodes[1][1].traversable = false;
        nodes[2][1].traversable = false;
        nodes[3][1].traversable = false;
        nodes[4][1].traversable = false;
        nodes[5][1].traversable = false;
        nodes[6][1].traversable = false;
        nodes[7][1].traversable = false;
        nodes[8][1].traversable = false;

        nodes[1][2].traversable = false;
        nodes[1][3].traversable = false;
        nodes[1][4].traversable = false;
        nodes[1][5].traversable = false;
        nodes[1][6].traversable = false;
        nodes[1][7].traversable = false;
        nodes[1][8].traversable = false;

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex(2,0);

        List<Node> dij = Pathfinder.dijkstra(nodes, start, end );
        logger.info( "Dijkstra" );
        Pathfinder.printPath( nodes, dij );
        
        List<Node> astar = Pathfinder.astar(nodes, start, end );
        logger.info( "A*" );
        Pathfinder.printPath( nodes, astar );
        
        logger.info( "Equal? " + dij.equals( astar ) );
    }
    
    @Test
    public void testFour() throws Exception {
        Node[][] nodes = makeNodes();

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex( nodes.length-1, nodes[0].length-1 );

        List<Node> dij = Pathfinder.dijkstra(nodes, start, end );
        logger.info( "Dijkstra" );
        Pathfinder.printPath( nodes, dij );
        
        List<Node> astar = Pathfinder.astar(nodes, start, end );
        logger.info( "A*" );
        Pathfinder.printPath( nodes, astar );
        
        logger.info( "Equal? " + dij.equals( astar ) );
    }

    @Test
    public void testFive() throws Exception {
        Node[][] nodes = makeNodes();

        nodes[0][1].traversable = false;
        nodes[1][1].traversable = false;
        nodes[2][1].traversable = false;
        nodes[3][1].traversable = false;
        nodes[4][1].traversable = false;
        nodes[5][1].traversable = false;
        nodes[6][1].traversable = false;
        nodes[7][1].traversable = false;
        nodes[8][1].traversable = false;

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex( nodes.length-1, nodes[0].length-1 );

        List<Node> dij = Pathfinder.dijkstra(nodes, start, end );
        logger.info( "Dijkstra" );
        Pathfinder.printPath( nodes, dij );
        
        List<Node> astar = Pathfinder.astar(nodes, start, end );
        logger.info( "A*" );
        Pathfinder.printPath( nodes, astar );
        
        logger.info( "Equal? " + dij.equals( astar ) );
    }
}
