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
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nSą dodatkowe znaki po wymiarach", ButtonType.OK);
                    alert.showAndWait();
                    break;
                }

            }

            if(rows != -2 && collumns != -2) {
                graph = new Graph(collumns, rows);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nLiczba wierszy i kolumn niewłaściwie podana", ButtonType.OK);
                alert.showAndWait();
            }

            String line;
            Scanner scanner;
            Node newNode;
            for (i = 0; (line = bufferedReader.readLine()) != null; i++) {
                scanner = new Scanner(line.replaceAll(",", ".")); //jesli w pliku sa przecinki zamiast kropek
                scanner.useDelimiter(" :|\\s* ");                          //to dzieki temu, plik i tak jest wczytywany
                scanner.useLocale(Locale.US);

                newNode = new Node(i);
                for (j = 0; scanner.hasNext(); j++) {
                    int dest = scanner.nextInt();
                    if(dest > rows * collumns) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nWierzchołek nie należy do grafu", ButtonType.OK);
                        alert.showAndWait();
                    }
                    double weight = scanner.nextDouble();
                    if(weight < 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nWaga krawędzi jest ujemna", ButtonType.OK);
                        alert.showAndWait();
                    }
                    newNode.addEdgeToNode(j, new Edge(dest, weight));

                }
                graph.addNodeToGraph(i, newNode);
            }
        }
        catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nWymiary nie mogą być liczbami rzeczywistymi.", ButtonType.OK);
            alert.showAndWait();
            //blad wymiary nie sa poprawnie podane(nie jest to pierwszy i trzeci znak w pliku przedzielony spacja
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nWymiary nie mogą być ujemne.", ButtonType.OK);
            alert.showAndWait();
            //blad wymiary sa ujemnymi liczbami
        }
        catch (InputMismatchException inputMismatchException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.\nPodano niewlaściwe wagi krawedzi.", ButtonType.OK);
            alert.showAndWait();
            //blad wagi krawedzi po dwukropku to nie double
        }
        catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Graf jest pusty.", ButtonType.OK);
            alert.showAndWait();
            //blad graf jest pusty bo plik jest pusty
        }
        catch (FileNotFoundException fileNotFoundException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie znaleziono pliku o takiej nazwie.", ButtonType.OK);
            alert.showAndWait();
        }  //blad nie ma pliku
        return graph;
    }


}
