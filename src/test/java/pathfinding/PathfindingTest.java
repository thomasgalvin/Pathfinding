package pathfinding;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        int multiplier = 5;
        int width = 100;
        int height = 100;
        
        Node[][] nodes = new Node[ width ][ height ];
        for( int x = 0; x < width; x++ ){
            for( int y = 0; y < height; y++ ){
                int xx = x * multiplier;
                int yy = y * multiplier;
                Node node = new Node(xx, yy, x, y);
                nodes[x][y] = node;
            }
        }
        
        return nodes;
    }

    @Test
    public void testDistance() throws Exception{
        double expected = 14.142;
        double delta = 0.01;
        int x1 = 0;
        int y1 = 0;
        int x2 = 10;
        int y2 = 10;
        double distance = Vertex.distance( x1, y1, x2, y2 );
        Assert.assertEquals( expected, distance, delta );
        
        Vertex one = new Vertex( x1, y1 );
        Vertex two = new Vertex( x2, y2 );
        distance = Vertex.distance( one, two );
        Assert.assertEquals( expected, distance, delta );
    }
    
    private static void runTest( Node[][] nodes, Vertex start, Vertex end ) throws Exception{
        long startTime = System.currentTimeMillis();
        List<Node> dijkstra = Pathfinder.dijkstra(nodes, start, end );
        long endTime = System.currentTimeMillis();
        long dTime = (endTime - startTime);
        
        startTime = System.currentTimeMillis();
        List<Node> astar = Pathfinder.bestFirst(nodes, start, end );
        endTime = System.currentTimeMillis();
        long bfTime = (endTime - startTime);
        
//        logger.info( "Dijkstra" );
//        Pathfinder.printPath( nodes, dijkstra );
//        logger.info( "A*" );
//        Pathfinder.printPath( nodes, astar );
        logger.info( "Same solution:         " + dijkstra.equals( astar ) );
        logger.info( "Dijkstra time:         " + dTime + " ms" );
        logger.info( "Best First time :      " + bfTime + " ms" );
        logger.info( "" );
    }
    
    @Test
    public void testOne() throws Exception {
        Node[][] nodes = makeNodes();

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex( nodes.length-1, nodes[0].length-1 );
        runTest( nodes, start, end );
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
        nodes[6][1].traversable = false;
        nodes[7][1].traversable = false;
        nodes[8][1].traversable = false;

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex( nodes.length-1, nodes[0].length-1 );
        runTest( nodes, start, end );
    }
    
    @Test
    public void testTwoA() throws Exception {
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
        Vertex end = new Vertex( nodes.length-3, nodes[0].length-2);
        runTest( nodes, start, end );
    }
    
    @Test
    public void testTwoB() throws Exception {
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
        Vertex end = new Vertex( 5, 3 );
        runTest( nodes, start, end );
    }
    
    @Test
    public void testThree() throws Exception {
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
        runTest( nodes, start, end );
    }

    @Test
    public void testFour() throws Exception {
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
        runTest( nodes, start, end );
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

        nodes[1][2].traversable = false;
        nodes[1][3].traversable = false;
        nodes[1][4].traversable = false;
        nodes[1][5].traversable = false;
        nodes[1][6].traversable = false;
        nodes[1][7].traversable = false;
        nodes[1][8].traversable = false;

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex(2,0);
        runTest( nodes, start, end );
    }
    
    @Test
    public void testSix() throws Exception {
        Node[][] nodes = makeNodes();

        Vertex start = new Vertex(0,0);
        Vertex end = new Vertex( 1,1 );
        runTest( nodes, start, end );
    }
    
    @Test
    public void testSeven() throws Exception {
        Node[][] nodes = makeNodes();

        Vertex start = new Vertex(5,5);
        Vertex end = new Vertex(5,5);
        runTest( nodes, start, end );
    }
}
