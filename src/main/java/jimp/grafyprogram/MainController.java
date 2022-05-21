package jimp.grafyprogram;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jimp.grafyprogram.functions.DijkstraCanvasPrinter;
import jimp.grafyprogram.graphutils.*;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private Graph graph;
    private ObservableList<Node> selectedNodes;

    @FXML
    private TextField gridSizeTextField;
    @FXML
    private TextField edgeWeightTextField;
    @FXML
    private TextField filePathTextField;
    @FXML
    private Canvas graphCanvas;
    @FXML
    private Label selectedNodesLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedNodesLabel.setText("0");
        selectedNodes = FXCollections.observableArrayList();
        selectedNodes.addListener((ListChangeListener<Node>) change -> selectedNodesLabel.setText(String.valueOf(selectedNodes.size())));
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
    }

    @FXML
    public void onGenerateButtonClick() {
        onDeleteButtonClick();

        String gridSizeText = gridSizeTextField.getText();
        String edgeWeightText = edgeWeightTextField.getText();

        int columns, rows;
        double start, end;

        try {
            columns = Integer.parseInt(gridSizeText.split("x")[0]);
            rows = Integer.parseInt(gridSizeText.split("x")[1]);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format wymiarów grafu.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            start = Double.parseDouble(edgeWeightText.split("-")[0]);
            end = Double.parseDouble(edgeWeightText.split("-")[1]);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Błędny format zakresu wag.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        graph = new Graph(columns, rows, start, end);
        gridSizeTextField.clear();
        edgeWeightTextField.clear();

        onRedrawButtonClick();

    }

    @FXML
    public void onCanvasMouseClick(MouseEvent mouseEvent) {
        if (graph != null) {
            GraphCanvasPrinter gcp = new GraphCanvasPrinter(graph, graphCanvas);
            Node selected = gcp.selectNode(mouseEvent.getX(), mouseEvent.getY());
            if (selected != null) {
                if (selectedNodes.size() == 0) {
                    selectedNodes.add(selected);
                    DijkstraCanvasPrinter dcp = new DijkstraCanvasPrinter(graph, graphCanvas, selected);
                    dcp.makeGradient();
                }
                else{
                    boolean ifAlreadyInside = false;
                    for (Node node : selectedNodes){
                        if (node.getNodeId() == selected.getNodeId()){
                            ifAlreadyInside = true;
                            break;
                        }
                    }
                    if (!ifAlreadyInside) {
                        selectedNodes.add(selected);
                        gcp.paintNode(selected, Color.GREEN);
                    }
                }
            }
        }
    }

    @FXML
    public void onRedrawButtonClick() {
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());

        if (graph != null) {
            GraphCanvasPrinter gcp = new GraphCanvasPrinter(graph, graphCanvas);
            gcp.print();

            if (selectedNodes.size() > 0){
                gcp.paintNode(selectedNodes.get(0), Color.BLUE);
                DijkstraCanvasPrinter dcp = new DijkstraCanvasPrinter(graph, graphCanvas, selectedNodes.get(0));
                dcp.makeGradient();
            }
            for (int i = 1; i < selectedNodes.size(); i++){
                gcp.paintNode(selectedNodes.get(i), Color.GREEN);
            }
        }
    }

    @FXML
    public void onSaveButtonClick() {
        /*
        if (graph == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz najpierw wczytać graf!", ButtonType.OK);
            alert.showAndWait();
        }
        if (graph != null){
            try{
                String filePath = filePathTextField.getText();
                GraphExporter ge = new GraphTextPrinter(graph, filePath);
                ge.print();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Akcja nie powiodła się.", ButtonType.OK);
                alert.showAndWait();
            }
        }

         */
    }

    @FXML
    public void onDeleteButtonClick() {
        selectedNodes.clear();
        graph = null;
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
    }

    @FXML
    public void onImportButtonClick() {
    }

    @FXML
    public void onBfsButtonClick() {
    }

    @FXML
    public void onDijkstraButtonClick() {
        if (graph != null && selectedNodes.size() > 1) {
            DijkstraCanvasPrinter dcp = new DijkstraCanvasPrinter(graph, graphCanvas, selectedNodes.get(0));
            for (int i = 1; i < selectedNodes.size(); i++) {
                dcp.print(selectedNodes.get(i));
            }
        }
        if (graph == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz najpierw wczytać graf!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (selectedNodes.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wybrano węzła startowego i węzłów docelowych", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (selectedNodes.size() == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wybrano żadnego węzła docelowego", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void onResetButtonClick() {
        if (graph != null) {
            selectedNodes.clear();
            onRedrawButtonClick();
        }
    }
}