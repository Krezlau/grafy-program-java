package jimp.grafyprogram.unittests;

import jimp.grafyprogram.functions.Dijkstra;
import jimp.grafyprogram.functions.DijkstraConsolePrinter;
import jimp.grafyprogram.graphutils.Edge;
import jimp.grafyprogram.graphutils.Graph;
import jimp.grafyprogram.graphutils.Node;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class DijkstraTest {
    private final Graph sampleGraphV1 = makeSampleGraphV1();
    private final Graph sampleGraphV2 = makeSampleGraphV2();
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void prepare(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void cleanUp() {
        System.setOut(standardOut);
    }

    @Test
    public void v1DistanceFromZeroToElevenShouldEqualThirteen() {
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV1, sampleGraphV1.getNodes().get(0));
        Assertions.assertEquals(13, dijkstra.getDistances()[11].getDistance());
        dijkstra.print(sampleGraphV1.getNodes().get(11));
        Assertions.assertEquals("11 <- 7 <- 3 <- 2 <- 1 <- 0", outputStreamCaptor.toString());
    }

    @Test
    public void v1DistanceFromZeroToTenShouldEqualNine(){
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV1, sampleGraphV1.getNodes().get(0));
        Assertions.assertEquals(9, dijkstra.getDistances()[10].getDistance());
        dijkstra.print(sampleGraphV1.getNodes().get(10));
        Assertions.assertEquals("10 <- 9 <- 8 <- 4 <- 0", outputStreamCaptor.toString());
    }

    @Test
    public void v2DistanceFromZeroToThreeShouldEqualEight(){
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV2, sampleGraphV2.getNodes().get(0));
        Assertions.assertEquals(8, dijkstra.getDistances()[3].getDistance());
        dijkstra.print(sampleGraphV2.getNodes().get(3));
        Assertions.assertEquals("3 <- 4 <- 1 <- 0", outputStreamCaptor.toString());
    }

    @Test
    public void v2DistanceFromFourToEightShouldEqualThree(){
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV2, sampleGraphV2.getNodes().get(4));
        Assertions.assertEquals(3, dijkstra.getDistances()[8].getDistance());
        dijkstra.print(sampleGraphV2.getNodes().get(8));
        Assertions.assertEquals("8 <- 7 <- 4", outputStreamCaptor.toString());
    }

    @Test
    public void v2DistanceFromFourToZeroShouldEqualEight(){
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV2, sampleGraphV2.getNodes().get(4));
        Assertions.assertEquals(8, dijkstra.getDistances()[0].getDistance());
        dijkstra.print(sampleGraphV2.getNodes().get(0));
        Assertions.assertEquals("0 <- 3 <- 4", outputStreamCaptor.toString());
    }

    @Test
    public void v2DistanceFromThreeToFiveShouldEqualEight(){
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV2, sampleGraphV2.getNodes().get(3));
        Assertions.assertEquals(8, dijkstra.getDistances()[5].getDistance());
        dijkstra.print(sampleGraphV2.getNodes().get(5));
        Assertions.assertEquals("5 <- 2 <- 1 <- 0 <- 3", outputStreamCaptor.toString());
    }

    @Test
    public void v2DistanceFromZeroToSixShouldEqualTwelve(){
        DijkstraConsolePrinter dijkstra = new DijkstraConsolePrinter(sampleGraphV2, sampleGraphV2.getNodes().get(0));
        Assertions.assertEquals(12, dijkstra.getDistances()[6].getDistance());
        dijkstra.print(sampleGraphV2.getNodes().get(6));
        Assertions.assertEquals("6 <- 7 <- 4 <- 1 <- 0", outputStreamCaptor.toString());
    }

    private Graph makeSampleGraphV1(){
        Graph sampleGraphV1 = new Graph(4, 3);
        for (int i = 0; i < 12; i++){
            sampleGraphV1.getNodes().add(new Node(i, new ArrayList<>()));
        }
        sampleGraphV1.getNodes().get(0).getEdges().add(new Edge(1, 2));
        sampleGraphV1.getNodes().get(0).getEdges().add(new Edge(4, 2));
        sampleGraphV1.getNodes().get(1).getEdges().add(new Edge(2, 3));
        sampleGraphV1.getNodes().get(2).getEdges().add(new Edge(6, 1));
        sampleGraphV1.getNodes().get(2).getEdges().add(new Edge(3, 1));
        sampleGraphV1.getNodes().get(3).getEdges().add(new Edge(7, 2));
        sampleGraphV1.getNodes().get(4).getEdges().add(new Edge(8, 5));
        sampleGraphV1.getNodes().get(6).getEdges().add(new Edge(7, 5));
        sampleGraphV1.getNodes().get(6).getEdges().add(new Edge(10, 5));
        sampleGraphV1.getNodes().get(7).getEdges().add(new Edge(11, 5));
        sampleGraphV1.getNodes().get(8).getEdges().add(new Edge(9, 1));
        sampleGraphV1.getNodes().get(9).getEdges().add(new Edge(10, 1));
        sampleGraphV1.getNodes().get(10).getEdges().add(new Edge(11, 5));

        return sampleGraphV1;
    }

    private Graph makeSampleGraphV2(){
        Graph sampleGraphV2 = new Graph(3, 3);
        for (int i = 0; i < 9; i++){
            sampleGraphV2.getNodes().add(new Node(i, new ArrayList<>()));
        }
        // node 0
        sampleGraphV2.getNodes().get(0).getEdges().add(new Edge(1, 1));
        sampleGraphV2.getNodes().get(0).getEdges().add(new Edge(3, 9));
        // node 1
        sampleGraphV2.getNodes().get(1).getEdges().add(new Edge(0, 3));
        sampleGraphV2.getNodes().get(1).getEdges().add(new Edge(2, 2));
        sampleGraphV2.getNodes().get(1).getEdges().add(new Edge(4, 2));
        // node 2
        sampleGraphV2.getNodes().get(2).getEdges().add(new Edge(1, 1));
        sampleGraphV2.getNodes().get(2).getEdges().add(new Edge(5, 2));
        // node 3
        sampleGraphV2.getNodes().get(3).getEdges().add(new Edge(0, 3));
        sampleGraphV2.getNodes().get(3).getEdges().add(new Edge(4, 8));
        sampleGraphV2.getNodes().get(3).getEdges().add(new Edge(6, 10));
        // node 4
        sampleGraphV2.getNodes().get(4).getEdges().add(new Edge(1, 8));
        sampleGraphV2.getNodes().get(4).getEdges().add(new Edge(5, 5));
        sampleGraphV2.getNodes().get(4).getEdges().add(new Edge(7, 1));
        sampleGraphV2.getNodes().get(4).getEdges().add(new Edge(3, 5));
        // node 5
        sampleGraphV2.getNodes().get(5).getEdges().add(new Edge(4, 3));
        sampleGraphV2.getNodes().get(5).getEdges().add(new Edge(2, 4));
        sampleGraphV2.getNodes().get(5).getEdges().add(new Edge(8, 7));
        // node 6
        sampleGraphV2.getNodes().get(6).getEdges().add(new Edge(3, 5));
        sampleGraphV2.getNodes().get(6).getEdges().add(new Edge(7, 3));
        // node 7
        sampleGraphV2.getNodes().get(7).getEdges().add(new Edge(6, 8));
        sampleGraphV2.getNodes().get(7).getEdges().add(new Edge(4, 1));
        sampleGraphV2.getNodes().get(7).getEdges().add(new Edge(8, 2));
        // node 8
        sampleGraphV2.getNodes().get(8).getEdges().add(new Edge(7, 3));
        sampleGraphV2.getNodes().get(8).getEdges().add(new Edge(5, 6));

        return sampleGraphV2;
    }
}