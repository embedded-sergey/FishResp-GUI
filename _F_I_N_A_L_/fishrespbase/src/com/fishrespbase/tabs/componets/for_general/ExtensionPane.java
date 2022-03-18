package com.fishrespbase.tabs.componets.for_general;

import com.fishrespbase.Main;
import com.fishrespbase.components.BaseTitledPane;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.components.modalWindows.ModalWindowManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Created by R7Bishop on 02.12.2017.
 */
public class ExtensionPane extends BaseTitledPane {
    private double x;
    private Pane scrollablePane;
    private Pane refPane;
    public ExtensionPane(double x, double y, double w, double h) {
        super("Extensions module", x, y, w, h);
        this.x = x;
        this.prefWidthProperty().bind(Main.baseScene.widthProperty().subtract(DPIkiller.kill(435)));
        refPane.layoutXProperty().bind(Main.baseScene.widthProperty().subtract(DPIkiller.kill(435)).divide(2.0));
        this.setPrefHeight(h);
    }

    @Override
    protected void createContent() {
        scrollablePane = new Pane();

        Label doConvertersLabel = new Label("DO converters:");
        doConvertersLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(15));

        Button respirometryButton = new Button("respirometry");
        respirometryButton.relocate(DPIkiller.kill(95),DPIkiller.kill(10));
        respirometryButton.setOnAction(event -> ModalWindowManager.createModal("respirometry"));
        Button rMRButton = new Button("rMR");
        rMRButton.relocate(DPIkiller.kill(190),DPIkiller.kill(10));
        rMRButton.setOnAction(event -> ModalWindowManager.createModal("rMR"));


        refPane = new Pane();
        Label reformattingLabel = new Label("Reformatting:");
        reformattingLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(15));

        Button preSensButton = new Button("PreSens");
        preSensButton.relocate(DPIkiller.kill(90),DPIkiller.kill(10));
        preSensButton.setOnAction(event -> ModalWindowManager.createModal("PreSens"));

        Button pyroScienceButton = new Button("PyroScience");
        pyroScienceButton.relocate(DPIkiller.kill(160),DPIkiller.kill(10));
        pyroScienceButton.setOnAction(event -> ModalWindowManager.createModal("PyroScience"));

        refPane.getChildren().addAll(reformattingLabel,preSensButton,pyroScienceButton);

        scrollablePane.getChildren().addAll(doConvertersLabel,respirometryButton,rMRButton, refPane);
        contentPane.getChildren().add(scrollablePane);
    }

    @Override
    public void reset() {

    }

    public void setY(double y) {
        this.relocate(this.x, y);
    }
}
