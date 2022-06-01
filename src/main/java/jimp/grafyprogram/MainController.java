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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import jimp.grafyprogram.graphutils.*;
import jimp.grafyprogram.functions.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private Graph graph;
    private ObservableList<Node> selectedNodes;
    private final GraphGenerator graphGenerator = new GraphGenerator();

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
    @FXML
    private Rectangle gradientRectangle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedNodesLabel.setText("0");
        selectedNodes = FXCollections.observableArrayList();
        selectedNodes.addListener((ListChangeListener<Node>) change -> selectedNodesLabel.setText(String.valueOf(selectedNodes.size())));
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());

        //filling gradient rectangle
        // start(255,0,0) -> stop1(255,255,0) -> stop2(0,255,0) -> stop3(0,255,255) -> end(0,0,255)
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(255,0,0)),
                                    new Stop(.25, Color.rgb(255,255,0)),
                                    new Stop(.5, Color.rgb(0,255,0)),
                                    new Stop(.75, Color.rgb(0,255,255)),
                                    new Stop(1, Color.rgb(0,0,255))
        };
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        gradientRectangle.setFill(lg1);
    }

    @FXML
    public void onGenerateButtonClick() {
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

        onDeleteButtonClick();
        graph = graphGenerator.generate(columns, rows, start, end);
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
                    DijkstraCanvasPrinter dcp = new DijkstraCanvasPrinter(selected, gcp);
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
        selectedNodes.clear();
        clearCanvas();

        if (graph != null) {
            GraphCanvasPrinter gcp = new GraphCanvasPrinter(graph, graphCanvas);
            gcp.print();
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
                GraphTextPrinter ge = new GraphTextPrinter(filePath, graph);
                ge.print();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Akcja nie powiodła się.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        selectedNodes.clear();
        graph = null;
        clearCanvas();
    }

    //przy graph = null wywala sie, do poprawy
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
            return;
        }
        try {
            boolean cohesion = bfs.solve();
            if (cohesion) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graf jest spojny", ButtonType.OK);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graf nie jest spojny", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDijkstraButtonClick() {
        if (graph != null && selectedNodes.size() > 1) {
            DijkstraCanvasPrinter dcp = new DijkstraCanvasPrinter(selectedNodes.get(0), new GraphCanvasPrinter(graph, graphCanvas));
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

    private void clearCanvas(){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
    }
}