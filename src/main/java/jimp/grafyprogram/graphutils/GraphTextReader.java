package jimp.grafyprogram.graphutils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class GraphTextReader extends GraphReader {



    protected Graph graph;
    private final String filePath;
    public GraphTextReader(String filePath){
        this.graph = null;
        this.filePath = filePath;
    }

    @Override
    public Graph read() throws IOException, IllegalArgumentException, InputMismatchException, NullPointerException {

        try {
            int rows = -2, collumns = -2, i, j;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String[] lines = bufferedReader.readLine().split("\\s+");
            for (i = 0; i < lines.length; i++) {
                if(!lines[i].equals("")) {
                    rows = Integer.parseInt(lines[i]);
                    break;
                }
            }
            for ( j = i+1; j < lines.length; j++) {
                if(!lines[j].equals("")) {
                    collumns = Integer.parseInt(lines[j]);
                    break;
                }
            }
            for ( int k = j + 1; k < lines.length; k++) {
                if(!lines[k].equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
                    alert.showAndWait();                                //Są dodatkowe znaki po wymiarach
                    break;
                }

            }

            if(rows != -2 && collumns != -2) {
                graph = new Graph(collumns, rows);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
                alert.showAndWait();                    //Liczba wierszy i kolumn niewłaściwie podana
            }

            String line;
            Scanner scanner;
            Node newNode;
            for (i = 0; (line = bufferedReader.readLine()) != null; i++) {
                scanner = new Scanner(line.replaceAll(",", "."));
                scanner.useDelimiter(" :|\\s* ");
                scanner.useLocale(Locale.US);

                newNode = new Node(i);
                for (j = 0; scanner.hasNext(); j++) {
                    int dest = scanner.nextInt();
                    if(dest > rows * collumns) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
                        alert.showAndWait();                                    //Wierzchołek nie należy do grafu
                    }
                    double weight = scanner.nextDouble();
                    if(weight < 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
                        alert.showAndWait();                            //Waga krawędzi jest ujemna
                    }
                    newNode.addEdgeToNode(j, new Edge(dest, weight));

                }
                graph.addNodeToGraph(i, newNode);
            }
        }
        catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
            alert.showAndWait();                              //Wymiary nie mogą być liczbami rzeczywistymi.

        }
        catch (IllegalArgumentException illegalArgumentException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
            alert.showAndWait();                                    //Wymiary nie mogą być ujemne.
        }
        catch (InputMismatchException inputMismatchException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of graph dimensions.\n", ButtonType.OK);
            alert.showAndWait();                                    //Podano niewlaściwe wagi krawedzi.
        }
        catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The graph is empty.", ButtonType.OK);
            alert.showAndWait();                                //Podany graf jest pusty
        }
        catch (FileNotFoundException fileNotFoundException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A file with this name was not found.", ButtonType.OK);
            alert.showAndWait();                            //Nie ma pliku o takiej nazwie do wczytania
        }
        return graph;
    }


}
