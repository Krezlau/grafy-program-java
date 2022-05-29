package jimp.grafyprogram.graphutils;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class GraphTextReader extends GraphReader {



    protected Graph graph;
    private  String filePath;
    public GraphTextReader(String filePath){
        this.graph = null;
        this.filePath = filePath;
    }

    @Override
    public Graph read() throws IOException {

        int rows = 0, collumns = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String[] lines = bufferedReader.readLine().split(" ");
        rows = Integer.parseInt(lines[0]);
        collumns = Integer.parseInt(lines[1]);
        graph = new Graph(collumns, rows);

        String line;
        Scanner scanner;
        Node newNode;
        int i = 0, j = 0;
        for ( i = 0; (line = bufferedReader.readLine()) != null; i++) {
            scanner = new Scanner(line.replaceAll(",", ".")); //jesli w pliku sa przecinki zamiast kropek
            scanner.useDelimiter(" :|\\s* ");                          //to dzieki temu, plik i tak jest wczytywany
            scanner.useLocale(Locale.US);

            newNode = new Node(i);
            for ( j = 0; scanner.hasNext(); j++) {
                int dest = scanner.nextInt();
                double weight = scanner.nextDouble();
                newNode.addEdgeToNode(j,new Edge(dest, weight));
                if(weight < 0) throw new IOException("Waga nie moze byc ujemna");
            }
            graph.addNodeToGraph(i, newNode);
        }
        return graph;
    }

}
