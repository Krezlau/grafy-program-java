package jimp.grafyprogram;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //private Graph graph;
    //private ObservableList<Node> selectedNodes;

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
    }

    @FXML
    public void onGenerateButtonClick(ActionEvent event) {
    }

    @FXML
    public void onCanvasMouseClick(MouseEvent mouseEvent) {
    }

    @FXML
    public void onRedrawButtonClick(ActionEvent event) {
    }

    @FXML
    public void onSaveButtonClick(ActionEvent event) {
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent event) {
    }

    @FXML
    public void onImportButtonClick(ActionEvent event) {
    }

    @FXML
    public void onBfsButtonClick(ActionEvent event) {
    }

    @FXML
    public void onDijkstraButtonClick(ActionEvent event) {
    }

    @FXML
    public void onResetButtonClick(ActionEvent event) {
    }
}