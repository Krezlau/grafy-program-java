package jimp.grafyprogram.graphutils;

import javafx.scene.paint.Color;

public class ColorPicker {

    public Color determineColor(double value, double rangeStart, double rangeEnd){
        // start(255,0,0) -> stop1(255,255,0) -> stop2(0,255,0) -> stop3(0,255,255) -> end(0,0,255)
        double stop1 = (rangeEnd - rangeStart) / 4 + rangeStart;
        double stop2 = (rangeEnd - rangeStart) / 4 + stop1;
        double stop3 = (rangeEnd - rangeStart) / 4 + stop2;
        double ratio = 255 / ((rangeEnd - rangeStart) / 4);

        if (value <= stop1){
            return Color.rgb(255,
                    (int) ((value - rangeStart) * ratio),
                    0);
        }
        if (value <= stop2){
            return Color.rgb((int) ((value - stop2) * (-ratio)),
                    255,
                    0);
        }
        if (value <= stop3){
            return Color.rgb(0,
                    255,
                    (int) ((value - stop2) * ratio));
        }
        else{
            return Color.rgb(0,
                    (int) ((value - rangeEnd) * (-ratio)),
                    255);
        }
    }
}
