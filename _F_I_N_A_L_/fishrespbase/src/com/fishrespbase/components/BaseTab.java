package com.fishrespbase.components;

import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * Created by R7Bishop on 07.03.2017.
 */
public abstract class BaseTab extends Tab {
    protected Pane mainPane;

    public BaseTab(String label, int color) {
        super(label);

        changeTabColor(color);

        this.setClosable(false);

        mainPane = new Pane();
        mainPane.setStyle("-fx-background-color: #909090;");
        this.setContent(mainPane);
    }

    public void changeTabColor(int color){
        switch (color){
            case Constants.OBJECT_GRAY:
                this.getStyleClass().removeAll("tabYellow","tabGreen");
                this.getStyleClass().add("tabGray");
                break;
            case Constants.OBJECT_YELLOW:
                this.getStyleClass().removeAll("tabGray","tabGreen");
                this.getStyleClass().add("tabYellow");
                break;
            case Constants.OBJECT_GREEN:
                this.getStyleClass().removeAll("tabGray","tabYellow");
                this.getStyleClass().add("tabGreen");
                break;
        }
    }

    public abstract void resetTab();
}
