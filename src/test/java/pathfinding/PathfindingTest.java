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
        int width = 10;
        int height = 10;
        int matrixStartX = 0;
        int matrixStartY = 0;
        int stepX = 10;
        int stepY = 10;
        return Pathfinder.makeNodes( width, height, matrixStartX, matrixStartY, stepX, stepY );
        
//        Node[][] nodes = new Node[ width ][ height ];
//        for( int x = 0; x < width; x++ ){
//            for( int y = 0; y < height; y++ ){
//                int xx = x * multiplier;
//                int yy = y * multiplier;
//                Node node = new Node(xx, yy, x, y);
//                nodes[x][y] = node;
//            }
//        }
//        
//        return nodes;
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
        int dsize = dijkstra == null ? -1 : dijkstra.size();
        long endTime = System.currentTimeMillis();
        long dTime = (endTime - startTime);
        
        startTime = System.currentTimeMillis();
        List<Node> dijkstraNoDiag = Pathfinder.dijkstra(nodes, start, end, false );
        int dndsize = dijkstraNoDiag == null ? -1 : dijkstraNoDiag.size();
        endTime = System.currentTimeMillis();
        long dndTime = (endTime - startTime);
        
        startTime = System.currentTimeMillis();
        List<Node> bf = Pathfinder.bestFirst(nodes, start, end );
        int bfsize = bf == null ? -1 : bf.size();
        endTime = System.currentTimeMillis();
        long bfTime = (endTime - startTime);
        
        startTime = System.currentTimeMillis();
        List<Node> bfNoDiag = Pathfinder.bestFirst(nodes, start, end, false );
        int bfndsize = bfNoDiag == null ? -1 : bfNoDiag.size();
        endTime = System.currentTimeMillis();
        long bfndTime = (endTime - startTime);
        
        startTime = System.currentTimeMillis();
        List<Node> astar = Pathfinder.astar(nodes, start, end, true );
        int astarsize = astar == null ? -1 : astar.size();
        endTime = System.currentTimeMillis();
        long astarTime = (endTime - startTime);
        
        startTime = System.currentTimeMillis();
        List<Node> astarnd = Pathfinder.astar(nodes, start, end, false );
        int astarndsize = astarnd == null ? -1 : astar.size();
        endTime = System.currentTimeMillis();
        long astarndTime = (endTime - startTime);
        
        logger.info( "Diagonals allowed" );
//        logger.info( "Dijkstra (diagonals allowed)" );
//        Pathfinder.printPath( nodes, dijkstra );
//        logger.info( "Best First (diagonals allowed)" );
//        Pathfinder.printPath( nodes, bf );
        logger.info( "Dijkstra length:                  " + dsize );
        logger.info( "Best First length:                " + bfsize );
        logger.info( "A* length:                        " + astarsize );
        logger.info( "Dijkstra time:                    " + dTime + " ms" );
        logger.info( "Best First time:                  " + bfTime + " ms" );
        logger.info( "A* time:                          " + astarTime + " ms" );
//        logger.info( "---" );
//        logger.info( "No diagonals:" );
//        logger.info( "Dijkstra (no diagonals)" );
//        Pathfinder.printPath( nodes, dijkstraNoDiag );
//        logger.info( "Best First (no diagonals)" );
//        Pathfinder.printPath( nodes, bfNoDiag );
//        logger.info( "Dijkstra length:                  " + dndsize );
//        logger.info( "Best First length:                " + bfndsize );
//        logger.info( "A* length:                        " + astarndsize );
//        logger.info( "Dijkstra time:                    " + dndTime + " ms" );
//        logger.info( "Best First time:                  " + bfndTime + " ms" );
//        logger.info( "A* time:                          " + astarndTime + " ms" );
        logger.info( "\n\n\n" );
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
