package jimp.grafyprogram;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jimp.grafyprogram.functions.BfsSolver;
import jimp.grafyprogram.functions.DijkstraCanvasPrinter;
import jimp.grafyprogram.graphutils.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// TODO
// (?) jakiś gradient jeśli chodzi o krawędzie
// testy dijkstry

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
        String gridSizeText = gridSizeTextField.getText();
        String edgeWeightText = edgeWeightTextField.getText();

        try {
            int collumns = Integer.parseInt(gridSizeText.split("x")[0]);
            int rows = Integer.parseInt(gridSizeText.split("x")[1]);
            double start = Double.parseDouble(edgeWeightText.split("-")[0]);
            double end = Double.parseDouble(edgeWeightText.split("-")[1]);

            graph = new Graph(collumns, rows, start, end);
            gridSizeTextField.clear();
            edgeWeightTextField.clear();

            onRedrawButtonClick();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Akcja nie powiodła się.", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    public void onCanvasMouseClick(MouseEvent mouseEvent) {
        if (graph != null) {
            GraphCanvasPrinter gcp = new GraphCanvasPrinter(graph, graphCanvas);
            Node selected = gcp.selectNode(mouseEvent.getX(), mouseEvent.getY());
            if (selected != null) {
                if (selectedNodes.size() == 0) {
                    selectedNodes.add(selected);
                    gcp.paintNode(selected, Color.BLUE);
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
            }
            for (int i = 1; i < selectedNodes.size(); i++){
                gcp.paintNode(selectedNodes.get(i), Color.GREEN);
            }
        }
    }

    @FXML
    public void onSaveButtonClick() {

        if (graph == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz najpierw wczytać graf!", ButtonType.OK);
            alert.showAndWait();
        }
        if (graph != null){
            try{
                String filePath = filePathTextField.getText();
                GraphTextPrinter gtp = new GraphTextPrinter(filePath, this.graph);
                gtp.print();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Akcja nie powiodła się.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        onResetButtonClick();

        graph = null;
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
    }

    @FXML
    public void onImportButtonClick() {
        GraphTextReader gtr = new GraphTextReader(filePathTextField.getText()); //filePathTextField.getText()

        try {
            this.graph = gtr.read();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz podac nazwę pliku.", ButtonType.OK);
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onBfsButtonClick() {
        BfsSolver bfs = new BfsSolver(graph);
        if (graph == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz najpierw wczytać graf!", ButtonType.OK);
            alert.showAndWait();
        }


        try {
            boolean cohesion = bfs.solve();
            if (cohesion == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graf jest spojny", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graf nie jest spojny", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {

        }
    }

    @FXML
    public void onDijkstraButtonClick() {
        DijkstraCanvasPrinter dcp = new DijkstraCanvasPrinter(graph, graphCanvas, selectedNodes.get(0));
        for (int i = 1; i < selectedNodes.size(); i++){
            dcp.print(selectedNodes.get(i));
        }
    }

    @FXML
    public void onResetButtonClick() {
        if (graph != null) {
            GraphCanvasPrinter gpc = new GraphCanvasPrinter(graph, graphCanvas);
            gpc.bleachSelectedNodes(selectedNodes);
            selectedNodes.clear();
        }
    }
}