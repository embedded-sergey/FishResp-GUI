package com.fishrespconfigurator;

import javafx.scene.control.Button;

/**
 * Created by R7Bishop on 07.03.2017.
 */
public class ColoredButton extends Button {
    public ColoredButton(boolean enabled, int color, String label) {
        super(label);
        changeColor(enabled, color);
    }

    public void changeColor(boolean enabled, int color) {
        switch (color) {
            case Constants.OBJECT_GRAY:
                this.setDisable(!enabled);
                this.setStyle("-fx-base: #ECECEC;");
                break;
            case Constants.OBJECT_YELLOW:
                this.setDisable(!enabled);
                this.setStyle("-fx-base: #FFFF80;");
                break;
            case Constants.OBJECT_GREEN:
                this.setDisable(!enabled);
                this.setStyle("-fx-base: #85FF85;");
                break;
            case Constants.OBJECT_RED:
                this.setDisable(!enabled);
                this.setStyle("-fx-base: #FF8080;");
                break;
            case Constants.OBJECT_BLUE:
                this.setDisable(!enabled);
                this.setStyle("-fx-base: #C1D6FF;");
                break;
        }
    }
}
