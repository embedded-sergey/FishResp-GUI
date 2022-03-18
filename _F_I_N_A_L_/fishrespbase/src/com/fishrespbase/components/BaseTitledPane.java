package com.fishrespbase.components;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public abstract class BaseTitledPane extends TitledPane{
    protected Pane contentPane;

    public BaseTitledPane(String label, double x, double y, double w, double h) {
        super();
        //this.setStyle("-fx-base: #ECECEC;");
        this.setText(label);
        this.setCollapsible(false);
        this.setPrefSize(w, h);
        this.relocate(x, y);
        contentPane = new Pane();
        this.setContent(contentPane);

        createContent();
    }

    protected abstract void createContent();

    public abstract void reset();

    public void resize(int w, int h) {
        this.setPrefSize(w, h);
    }
}
