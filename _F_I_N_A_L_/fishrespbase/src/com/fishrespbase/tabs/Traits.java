package com.fishrespbase.tabs;

import com.fishrespbase.components.BaseTab;
import com.fishrespbase.components.Constants;
import com.fishrespbase.components.DPIkiller;
import com.fishrespbase.tabs.componets.CleanAllPane;
import com.fishrespbase.tabs.componets.GraphModule;
import com.fishrespbase.tabs.componets.for_trait.CorrectionPane;
import com.fishrespbase.tabs.componets.for_trait.QCTestsPane;
import com.fishrespbase.tabs.componets.for_trait.RawDataPane;
import javafx.beans.value.ObservableValue;

import java.util.Arrays;

/**
 * Created by R7Bishop on 20.02.2017.
 */
public class Traits extends BaseTab {
    public static Traits trait1, trait2;

    private boolean chamberVisible;
    private boolean completed;
    private int index;

    private RawDataPane rawDataPane;
    private CorrectionPane correctionPane;
    private QCTestsPane qcTestsPane;
    private GraphModule graphModule;
    private CleanAllPane cleanAllPane;

    public Traits(String label, int index){
        super(label, Constants.OBJECT_GRAY);

        this.completed = false;
        this.index = index;

        chamberVisible = false;

        graphModule = new GraphModule();
        rawDataPane = new RawDataPane(this, DPIkiller.kill(5), DPIkiller.kill(5), DPIkiller.kill(230), DPIkiller.kill(320));
        correctionPane = new CorrectionPane(this, DPIkiller.kill(5), DPIkiller.kill(330), DPIkiller.kill(230), DPIkiller.kill(90));
        qcTestsPane = new QCTestsPane(this, DPIkiller.kill(5), DPIkiller.kill(425), DPIkiller.kill(230), DPIkiller.kill(90));
        cleanAllPane = new CleanAllPane();

        mainPane.getChildren().addAll(rawDataPane, correctionPane, qcTestsPane, graphModule.getPane(), cleanAllPane);

        mainPane.widthProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cleanAllPane.relocate();
            });
        mainPane.heightProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cleanAllPane.relocate();
            });
    }

    public static void setDateFormat(String value) {
        switch (value)
        {
            case "DMY":
                trait1.rawDataPane.dateTime.setText("DD/MM/YYYY/HH:MM:SS");
                trait2.rawDataPane.dateTime.setText("DD/MM/YYYY/HH:MM:SS");
                break;
            case "MDY":
                trait1.rawDataPane.dateTime.setText("MM/DD/YYYY/HH:MM:SS");
                trait2.rawDataPane.dateTime.setText("MM/DD/YYYY/HH:MM:SS");
                break;
            case "YMD":
                trait1.rawDataPane.dateTime.setText("YYYY/MM/DD/HH:MM:SS");
                trait2.rawDataPane.dateTime.setText("YYYY/MM/DD/HH:MM:SS");
                break;
        }
    }

    @Override
    public void resetTab() {
        disable();
        rawDataPane.reset();
        correctionPane.reset();
        qcTestsPane.reset();
        graphModule.clear();
        completed = false;
    }

    public void enable() {
        resetTab();
        changeTabColor(Constants.OBJECT_YELLOW);
        rawDataPane.setEnabled(true);
        correctionPane.setDisable(true);
        qcTestsPane.setDisable(true);
    }

    public void disable() {
        changeTabColor(Constants.OBJECT_GRAY);

        rawDataPane.setEnabled(false);
        correctionPane.setDisable(true);
        qcTestsPane.setDisable(true);
    }

    public void completeImport(boolean state) {
        if (state) {
            correctionPane.enable();
        }
        else {
            correctionPane.reset();
            completeCorrection(state);
        }
    }

    public void completeCorrection(boolean state) {
        completed = state;
        if (state) {
            qcTestsPane.enable();
        }
        else {
            qcTestsPane.reset();
        }
        /*
        results.tryEnable();
         */
    }

    public void renameTab(String name) {
        this.setText(name);
    }

    public void relocateQCTests(boolean chamberVisible) {
        this.chamberVisible = chamberVisible;
        if (chamberVisible)
            qcTestsPane.relocate(DPIkiller.kill(5),DPIkiller.kill(455));
        else
            qcTestsPane.relocate(DPIkiller.kill(5),DPIkiller.kill(430));
    }

    public int getIndex() {
        return index;
    }

    public GraphModule getGraphModule() {
        return graphModule;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public static void setSelectedBGRTests(int userData) {
        trait1.setMethodOfCorrection(userData);
        trait2.setMethodOfCorrection(userData);
    }

    private void setMethodOfCorrection(int userData) {
        this.correctionPane.setMethodOfCorrection(userData);
    }

    public static void setChamberNumbers() {
        trait1.correctionPane.setChamberNumbers();
        trait2.correctionPane.setChamberNumbers();
    }
}
