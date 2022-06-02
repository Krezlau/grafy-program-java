package jimp.grafyprogram.unittests;


import jimp.grafyprogram.graphutils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GraphTextPrinterTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void printToConsoll() {
        GraphTextPrinter graphTextPrinter = new GraphTextPrinter(testGraph1());
        graphTextPrinter.printToConsoll();
        assertEquals("""
                3 3
                     1 :0.76  3 :0.66 \s
                     0 :0.09  2 :0.03  4 :0.01 \s
                     1 :0.58  5 :0.71 \s
                     4 :0.42  6 :0.50  0 :0.07 \s
                     3 :0.88  5 :0.24  7 :0.04  1 :0.37 \s
                     4 :0.69  8 :0.38  2 :0.40 \s
                     7 :0.55  3 :0.53 \s
                     6 :0.77  8 :0.97  4 :0.68 \s
                     7 :0.31  5 :0.99 \s
                """, outputStreamCaptor.toString());
        }

    private Graph testGraph1() {

        Graph testGraph1 = new Graph(3, 3);
        for (int i = 0; i < 9; i++){
            testGraph1.getNodes().add(new Node(i, new ArrayList<>()));
        }

        // node 0
        testGraph1.getNodes().get(0).getEdges().add(new Edge(1, 0.76));
        testGraph1.getNodes().get(0).getEdges().add(new Edge(3, 0.66));
        // node 1
        testGraph1.getNodes().get(1).getEdges().add(new Edge(0, 0.09));
        testGraph1.getNodes().get(1).getEdges().add(new Edge(2, 0.03));
        testGraph1.getNodes().get(1).getEdges().add(new Edge(4, 0.01));
        // node 2
        testGraph1.getNodes().get(2).getEdges().add(new Edge(1, 0.58));
        testGraph1.getNodes().get(2).getEdges().add(new Edge(5, 0.71));
        // node 3
        testGraph1.getNodes().get(3).getEdges().add(new Edge(4, 0.42));
        testGraph1.getNodes().get(3).getEdges().add(new Edge(6, 0.5));
        testGraph1.getNodes().get(3).getEdges().add(new Edge(0, 0.07));
        // node 4
        testGraph1.getNodes().get(4).getEdges().add(new Edge(3, 0.88));
        testGraph1.getNodes().get(4).getEdges().add(new Edge(5, 0.24));
        testGraph1.getNodes().get(4).getEdges().add(new Edge(7, 0.04));
        testGraph1.getNodes().get(4).getEdges().add(new Edge(1, 0.37));
        // node 5
        testGraph1.getNodes().get(5).getEdges().add(new Edge(4, 0.69));
        testGraph1.getNodes().get(5).getEdges().add(new Edge(8, 0.38));
        testGraph1.getNodes().get(5).getEdges().add(new Edge(2, 0.4));
        // node 6
        testGraph1.getNodes().get(6).getEdges().add(new Edge(7, 0.55));
        testGraph1.getNodes().get(6).getEdges().add(new Edge(3, 0.53));
        // node 7
        testGraph1.getNodes().get(7).getEdges().add(new Edge(6, 0.77));
        testGraph1.getNodes().get(7).getEdges().add(new Edge(8, 0.97));
        testGraph1.getNodes().get(7).getEdges().add(new Edge(4, 0.68));
        // node 8
        testGraph1.getNodes().get(8).getEdges().add(new Edge(7, 0.31));
        testGraph1.getNodes().get(8).getEdges().add(new Edge(5, 0.99));

        return testGraph1;
    }
}
