package com.fishrespbase.tabs.componets.for_general;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.tabs.BGR;
import com.fishrespbase.tabs.General;
import com.fishrespbase.tabs.Results;
import com.fishrespbase.tabs.Traits;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class IndividualInfoPane extends BaseTitledPane {
    private int count;

    private Label chamberLabel[];
    private TextField chamberID[],
                      chamberWeight[],
                      chamberVolume[];

    public ColoredButton inputDataButton;

    public IndividualInfoPane(double x, double y, double w, double h) {
        super("Individual info", x, y, w, h);
    }

    public void setTable(int count) {
        this.count = count;

        for (int i = 0; i < count; i++) {
            chamberLabel[i].setDisable(false);
            chamberID[i].setDisable(false);
            chamberWeight[i].setDisable(false);
            chamberVolume[i].setDisable(false);
        }
        for (int i = count; i < 8; i++) {
            chamberLabel[i].setDisable(true);
            chamberID[i].setDisable(true);
            chamberWeight[i].setDisable(true);
            chamberVolume[i].setDisable(true);
        }
        inputDataButton.changeColor(true, Constants.OBJECT_YELLOW);
    }

    @Override
    protected void createContent() {
        Label idLabel, weightLabel, volumeLabel;
        idLabel = new Label("ID");
        idLabel.relocate(DPIkiller.kill(120),DPIkiller.kill(5));

        weightLabel = new Label("Mass [g]");
        weightLabel.relocate(DPIkiller.kill(215),DPIkiller.kill(5));

        volumeLabel = new Label("Volume [ml]");
        volumeLabel.relocate(DPIkiller.kill(320),DPIkiller.kill(5));

        chamberLabel = new Label[8];
        chamberID = new TextField[8];
        chamberWeight = new TextField[8];
        chamberVolume = new TextField[8];

        for (int i = 0; i < 8; i++) {
            chamberLabel[i] = new Label("Chamber " + (i + 1));
            chamberLabel[i].setDisable(true);
            chamberLabel[i].relocate(DPIkiller.kill(7), DPIkiller.kill(40 * (i + 1) - 10));

            chamberID[i] = new TextField();
            chamberID[i].setDisable(true);
            chamberID[i].relocate(DPIkiller.kill(76), DPIkiller.kill(40 * (i + 1) - 3 - 10));
            chamberID[i].setPrefSize(DPIkiller.kill(100), DPIkiller.kill(20));

            chamberWeight[i] = new TextField();
            chamberWeight[i].setDisable(true);
            chamberWeight[i].relocate(DPIkiller.kill(76 + 115), DPIkiller.kill(40 * (i + 1) - 3 - 10));
            chamberWeight[i].setPrefSize(DPIkiller.kill(100), DPIkiller.kill(20));

            chamberVolume[i] = new TextField();
            chamberVolume[i].setDisable(true);
            chamberVolume[i].relocate(DPIkiller.kill(76 + 230), DPIkiller.kill(40 * (i + 1) - 3 - 10));
            chamberVolume[i].setPrefSize(DPIkiller.kill(100), DPIkiller.kill(20));

            contentPane.getChildren().addAll(chamberLabel[i], chamberID[i], chamberWeight[i], chamberVolume[i]);

            int finalI = i;
            chamberWeight[i].textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue))
                        chamberWeight[finalI].setText(newValue);
                    else
                        chamberWeight[finalI].setText(oldValue);
                });
            chamberVolume[i].textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue))
                        chamberVolume[finalI].setText(newValue);
                    else
                        chamberVolume[finalI].setText(oldValue);
                });
        }

        inputDataButton = new ColoredButton(false, Constants.OBJECT_GRAY,"Input data");
        /*if (Main.OS == "linux")
            inputDataButton.relocate(160,337);
        else
            inputDataButton.relocate(160,342);*/
        inputDataButton.relocate(DPIkiller.kill(160),DPIkiller.kill(342));
        inputDataButton.setMaxWidth(DPIkiller.kill(100));
        inputDataButton.setMinWidth(DPIkiller.kill(100));

        inputDataButton.setOnAction(
            (ActionEvent event) -> {
                for (int i = 0; i < 8; i++)
                    if (!chamberID[i].isDisabled()){
                        if (chamberID[i].getText().length()==0) {
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the ID for Chamber "+(i+1)));
                            return;
                        }
                    }

                for (int i = 0; i < 8; i++)
                    if (!chamberWeight[i].isDisabled()){
                        if (chamberWeight[i].getText().length()==0) {
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the weight for Chamber "+(i+1)));
                            return;
                        }
                    }

                for (int i = 0; i < 8; i++)
                    if (!chamberVolume[i].isDisabled()){
                        if (chamberVolume[i].getText().length()==0) {
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the volume of Chamber "+(i+1)));
                            return;
                        }
                    }

                for (int i = 0; i < 8 && !chamberID[i].isDisabled(); i++) {
                    for (int j = i+1; j < 8 && !chamberID[j].isDisabled(); j++) {
                            if (chamberID[i].getText().equals(chamberID[j].getText())) {
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING2LINES, Constants.DEFAULT_ALERTLIFETIME, "The same ID cannot be used for different chambers"));
                            return;
                        }
                    }
                }
                String resultID = "ID = c(\""+chamberID[0].getText()+"\"", resultWeight, resultVolume;
                if (chamberWeight[0].getText().length()>0)
                    resultWeight = "Mass = c("+chamberWeight[0].getText();
                else
                    resultWeight = "Mass = c(0";

                if (chamberVolume[0].getText().length()>0)
                    resultVolume = "Volume = c("+chamberVolume[0].getText();
                else
                    resultVolume = "Volume = c(0";

                for (int i = 1; i < 8; i++)
                    if (!chamberID[i].isDisabled()){
                        if (chamberWeight[i].getText().length()!=0)
                            resultWeight += ","+chamberWeight[i].getText();
                        else
                            resultWeight += ",0";

                        if (chamberVolume[i].getText().length()!=0)
                            resultVolume += ","+chamberVolume[i].getText();
                        else
                            resultVolume += ",0";

                        resultID += ",\""+chamberID[i].getText()+"\"";
                    }
                resultID += "), ";
                resultWeight += "), ";
                resultVolume += ")";

                String quest = "info <- input.info("+resultID+resultWeight+resultVolume+")";
                RLogger.log(quest);
                if (Main.re.eval(quest)!=null) {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));

                    inputDataButton.changeColor(true, Constants.OBJECT_GREEN);
                    General.tab.setTableReady(true);
                    BGR.tab.enable(General.tab.getSelectBGRTests());
                    General.tab.changeTabColor(Constants.OBJECT_GREEN);
                }
                else {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while importing info"));

                    General.tab.changeTabColor(Constants.OBJECT_YELLOW);
                }
            });

        contentPane.getChildren().addAll(idLabel,weightLabel,volumeLabel,inputDataButton);
    }

    public void reset() {
        for (int i = 0; i < 8; i++) {
            chamberLabel[i].setDisable(true);
            chamberID[i].setDisable(true);
            chamberWeight[i].setDisable(true);
            chamberVolume[i].setDisable(true);
        }
        clear();

        //inputDataButton.changeColor(false,Constants.OBJECT_GRAY);
    }

    public void clear() {
        for (int i = 0; i < 8; i++) {
            chamberID[i].setText("");
            chamberWeight[i].setText("");
            chamberVolume[i].setText("");
        }
        if (chamberID[0].isDisabled())
            inputDataButton.changeColor(General.tab.isGlobalData(), Constants.OBJECT_GRAY);
        else
            inputDataButton.changeColor(General.tab.isGlobalData(), Constants.OBJECT_YELLOW);

        General.tab.setTableReady(false);

        Traits.trait1.resetTab();
        Traits.trait2.resetTab();
        Results.tab.resetTab();
    }
}
