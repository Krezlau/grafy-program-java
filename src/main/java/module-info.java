module jimp.grafyprogram {
    requires javafx.controls;
    requires javafx.fxml;


    opens jimp.grafyprogram to javafx.fxml;
    exports jimp.grafyprogram;
}