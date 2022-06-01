package jimp.grafyprogram.unittests;

import jimp.grafyprogram.functions.BfsSolver;
import jimp.grafyprogram.graphutils.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BfsSolverTest {
    @Test
    public void testBfsSolverForConsistentGraph() {
        BfsSolver bfsSolver = new BfsSolver(testGraph1());
        assertTrue(bfsSolver.solve());
    }
    @Test
    public void testBfsSolverForIncoherentGraph() {
        BfsSolver bfsSolver = new BfsSolver(testGraph2());
        assertFalse(bfsSolver.solve());
    }
    @Test
    public void testBfsSolverFromFile() throws IOException {
        GraphTextReader graphTextReader = new GraphTextReader("niespojny.txt");
        Graph testGraphFromFile = graphTextReader.read() ;
        BfsSolver bfsSolver  = new BfsSolver(testGraphFromFile);
        assertFalse(bfsSolver.solve());
    }
    @Test
    public void testBfsSolverFromFile2() throws IOException {
        GraphTextReader graphTextReader = new GraphTextReader("spojny.txt");
        Graph testGraphFromFile = graphTextReader.read() ;
        BfsSolver bfsSolver  = new BfsSolver(testGraphFromFile);
        assertTrue(bfsSolver.solve());
    }
    private Graph testGraph1() {

        Graph testGraph1 = new Graph(3, 3);
        for (int i = 0; i < 9; i++){
            testGraph1.getNodes().add(new Node(i, new ArrayList<>()));
        }
        //graf spojny
        // node 0
        testGraph1.getNodes().get(0).getEdges().add(new Edge(1, 1));
        testGraph1.getNodes().get(0).getEdges().add(new Edge(3, 9));
        // node 1
        testGraph1.getNodes().get(1).getEdges().add(new Edge(0, 3));
        testGraph1.getNodes().get(1).getEdges().add(new Edge(2, 2));
        testGraph1.getNodes().get(1).getEdges().add(new Edge(4, 2));
        // node 2
        testGraph1.getNodes().get(2).getEdges().add(new Edge(1, 1));
        testGraph1.getNodes().get(2).getEdges().add(new Edge(5, 2));
        // node 3
        testGraph1.getNodes().get(3).getEdges().add(new Edge(0, 3));
        testGraph1.getNodes().get(3).getEdges().add(new Edge(4, 8));
        testGraph1.getNodes().get(3).getEdges().add(new Edge(6, 10));
        // node 4
        testGraph1.getNodes().get(4).getEdges().add(new Edge(1, 8));
        testGraph1.getNodes().get(4).getEdges().add(new Edge(5, 5));
        testGraph1.getNodes().get(4).getEdges().add(new Edge(7, 1));
        testGraph1.getNodes().get(4).getEdges().add(new Edge(3, 5));
        // node 5
        testGraph1.getNodes().get(5).getEdges().add(new Edge(4, 3));
        testGraph1.getNodes().get(5).getEdges().add(new Edge(2, 4));
        testGraph1.getNodes().get(5).getEdges().add(new Edge(8, 7));
        // node 6
        testGraph1.getNodes().get(6).getEdges().add(new Edge(3, 5));
        testGraph1.getNodes().get(6).getEdges().add(new Edge(7, 3));
        // node 7
        testGraph1.getNodes().get(7).getEdges().add(new Edge(6, 8));
        testGraph1.getNodes().get(7).getEdges().add(new Edge(4, 1));
        testGraph1.getNodes().get(7).getEdges().add(new Edge(8, 2));
        // node 8
        testGraph1.getNodes().get(8).getEdges().add(new Edge(7, 3));
        testGraph1.getNodes().get(8).getEdges().add(new Edge(5, 6));
        return testGraph1;
    }
    private Graph testGraph2(){
        Graph testGraph2 = new Graph(3, 3);
        for (int i = 0; i < 9; i++){
            testGraph2.getNodes().add(new Node(i, new ArrayList<>()));
        }

        // graf niespojny
        // node 0
        testGraph2.getNodes().get(0).getEdges().add(new Edge(1, 2));
        testGraph2.getNodes().get(0).getEdges().add(new Edge(3, 2));
        // node 1
        testGraph2.getNodes().get(1).getEdges().add(new Edge(0, 3));
        // node 2
        testGraph2.getNodes().get(2).getEdges().add(new Edge(5, 1));
        // node 3
        testGraph2.getNodes().get(3).getEdges().add(new Edge(0, 2));
        testGraph2.getNodes().get(3).getEdges().add(new Edge(4,4));
        testGraph2.getNodes().get(3).getEdges().add(new Edge(6,3));
        // node 4
        testGraph2.getNodes().get(4).getEdges().add(new Edge(3, 3));
        // node 5
        testGraph2.getNodes().get(5).getEdges().add(new Edge(2, 4));
        // node 6
        testGraph2.getNodes().get(6).getEdges().add(new Edge(7, 5));
        testGraph2.getNodes().get(6).getEdges().add(new Edge(3, 5));
        // node 7
        testGraph2.getNodes().get(7).getEdges().add(new Edge(6, 5));
        testGraph2.getNodes().get(7).getEdges().add(new Edge(8, 1));
        // node 8
        testGraph2.getNodes().get(8).getEdges().add(new Edge(7, 1));

        return testGraph2;
    }



}