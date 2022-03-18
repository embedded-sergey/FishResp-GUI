package com.fishrespbase.tabs.componets.for_trait;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.tabs.Results;
import com.fishrespbase.tabs.Traits;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class QCTestsPane extends BaseTitledPane {

    private Traits mainTrait;
    private ComboBox<String> comboBox;
    private ColoredButton button;

    public QCTestsPane(Traits mainTrait, double x, double y, double w, double h) {
        super("Visual QC tests", x, y, w, h);
        this.setDisable(true);
        this.mainTrait = mainTrait;
    }

    @Override
    protected void createContent() {
        ObservableList<String> options = FXCollections.observableArrayList("Activity","Comparison","Temperature","Total.O2.phases","Total.O2.chambers","Corrected.O2.phases","Corrected.O2.chambers");

        comboBox = new ComboBox(options);
        comboBox.relocate(DPIkiller.kill(30),DPIkiller.kill(5));
        comboBox.setPrefSize(DPIkiller.kill(170),DPIkiller.kill(20));
        comboBox.setValue("Comparison");

        comboBox.setOnAction(event -> {
            askButton();
        });


        button = new ColoredButton(true, Constants.OBJECT_GRAY,"Plot a graph");
        button.setMaxWidth(DPIkiller.kill(100));
        button.setMinWidth(DPIkiller.kill(100));
        button.relocate(DPIkiller.kill(65), DPIkiller.kill(35));
        button.setOnAction(event -> {
            if (comboBox.getValue().length()>0) {
                String quest = "", graphName = "";
                switch (comboBox.getValue()) {
                    case "Activity":
                        quest = "QC.activity(Trait." + mainTrait.getIndex() + ".clean, compare = FALSE)";
                        graphName = "QC.total.O2.chambers_";
                        break;
                    case "Comparison":
                        quest = "QC.activity(Trait." + mainTrait.getIndex() + ".clean, compare = TRUE)";
                        graphName = "QC.total.O2.chambers_";
                        break;
                    case "Temperature":
                        quest = "QC.meas(Trait." + mainTrait.getIndex() + ".clean, QC = \"" + comboBox.getValue() + "\") ";
                        graphName = "QC.temperature_";
                        break;
                    case "Total.O2.chambers":
                        quest = "QC.meas(Trait." + mainTrait.getIndex() + ".clean, QC = \"" + comboBox.getValue() + "\") ";
                        graphName = "QC.total.O2.chambers_";
                        break;
                    case "Total.O2.phases":
                        quest = "QC.meas(Trait." + mainTrait.getIndex() + ".clean, QC = \"" + comboBox.getValue() + "\") ";
                        graphName = "QC.total.O2.phases_";
                        break;
                    case "Corrected.O2.chambers":
                        quest = "QC.meas(Trait." + mainTrait.getIndex() + ".clean, QC = \"" + comboBox.getValue() + "\") ";
                        graphName = "QC.corrected.O2.chambers_";
                        break;
                    case "Corrected.O2.phases":
                        quest = "QC.meas(Trait." + mainTrait.getIndex() + ".clean, QC = \"" + comboBox.getValue() + "\") ";
                        graphName = "QC.corrected.O2.phases_";
                        break;
                }
                SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Plotting a graph");
                AlertAccordion.pushMessage(alert);
                RLogger.log(quest);
                if (Main.re.eval(quest) != null) {
                    AlertAccordion.remove(alert);
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));
                    mainTrait.getGraphModule().setBaseFilename(graphName, 1);
                }
                else {
                    AlertAccordion.remove(alert);
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while plotting a graph"));
                    mainTrait.getGraphModule().clear();
                }
            }
        });

        contentPane.getChildren().addAll(comboBox,button);
    }

    private void askButton() {
    }

    public void enable() {
        button.changeColor(true,Constants.OBJECT_BLUE);
        this.setDisable(false);
    }

    public void reset() {
        comboBox.setValue("Comparison");
        button.changeColor(true,Constants.OBJECT_GRAY);

        this.setDisable(true);
    }
}
