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
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class CorrectionPane extends BaseTitledPane {

    private Traits mainTrait;

    private ObservableList<String> chamberOptions;
    private Label chamberLabel;
    private ComboBox<String> correctionComboBox;
    private ComboBox<String> chamberComboBox;
    private ColoredButton correctButton;
    private boolean costyl;

    public CorrectionPane(Traits mainTrait, double x, double y, double w, double h) {
        super("Correction for background respiration", x, y, w, h);
        //this.setFont(Font.font("System", FontWeight.BOLD, 11.8));
        this.setMaxWidth(w);
        this.setMinWidth(w);
        this.setDisable(true);
        this.mainTrait = mainTrait;
    }

    @Override
    protected void createContent() {
        chamberLabel = new Label("Chamber used for correction:");
        chamberLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(40));
        chamberLabel.setVisible(false);

        chamberOptions = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");

        chamberComboBox= new ComboBox(chamberOptions);
        chamberComboBox.relocate(DPIkiller.kill(165),DPIkiller.kill(35));
        chamberComboBox.setPrefSize(DPIkiller.kill(55),DPIkiller.kill(20));
        chamberComboBox.setValue(" ");
        chamberComboBox.setOnAction(event -> askButton());
        chamberComboBox.setVisible(false);
        chamberComboBox.getStyleClass().add("my-list-cell");

        Label correctionLabel = new Label("Method of correction");
        correctionLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        ObservableList<String> options = FXCollections.observableArrayList("pre-test","post-test","average","linear","exponential","parallel");

        correctionComboBox = new ComboBox(options);
        correctionComboBox.relocate(DPIkiller.kill(125),DPIkiller.kill(5));
        correctionComboBox.setPrefSize(DPIkiller.kill(95),DPIkiller.kill(20));
        costyl = false;
        correctionComboBox.setOnAction(event -> {
            this.resize(DPIkiller.kill(230),DPIkiller.kill(95));
            chamberLabel.setVisible(false);
            chamberComboBox.setVisible(false);
            mainTrait.relocateQCTests(false);
            correctButton.relocate(DPIkiller.kill(65),DPIkiller.kill(35));
            if (!costyl)
                if (correctionComboBox.getValue().length()>0)
                    if (correctionComboBox.getValue() == "parallel")
                    {
                        this.resize(DPIkiller.kill(230),DPIkiller.kill(150));
                        chamberLabel.setVisible(true);
                        chamberComboBox.setVisible(true);
                        mainTrait.relocateQCTests(true);
                        correctButton.relocate(DPIkiller.kill(65),DPIkiller.kill(65));
                    }
            askButton();
        });



        correctButton = new ColoredButton(true, Constants.OBJECT_GRAY,"Correct data");
        correctButton.setMaxWidth(DPIkiller.kill(100));
        correctButton.setMinWidth(DPIkiller.kill(100));
        correctButton.relocate(DPIkiller.kill(65), DPIkiller.kill(35));

        correctButton.setOnAction(
                (ActionEvent event) -> {
                    if (correctionComboBox.getValue() == "parallel")
                        if (chamberComboBox.getValue() == " ") {
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the chamber for correction"));
                            return;
                        }
                    if (correctionComboBox.getValue().length()>0) {
                        SingleAlert alert = new SingleAlert(AlertType.WAIT, 0, "Correcting raw data");
                        AlertAccordion.pushMessage(alert);

                        String tmp;
                        switch (correctionComboBox.getValue()) {
                            case "pre-test":
                                tmp = "pre.test";
                                break;
                            case "post-test":
                                tmp = "post.test";
                                break;
                            default:
                                tmp = correctionComboBox.getValue();
                                break;
                        }

                        String quest;
                        if (tmp != "parallel")
                            quest = "Trait." + mainTrait.getIndex() + ".clean <- correct.meas(info.data = info, pre.data = pre, post.data = post, meas.data = Trait."+mainTrait.getIndex()+", method = \""+tmp+"\")";
                        else
                            quest = "Trait." + mainTrait.getIndex() + ".clean <- correct.meas(info.data = info, meas.data = Trait." + mainTrait.getIndex() + ", method = \"parallel\", empty.chamber = \"CH" + chamberComboBox.getValue() + "\")";

                        RLogger.log(quest);
                        if (Main.re.eval(quest) == null) {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while correcting raw data"));

                            mainTrait.completeCorrection(false);
                            mainTrait.changeTabColor(Constants.OBJECT_YELLOW);
                        }
                        else {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));

                            mainTrait.completeCorrection(true);
                            mainTrait.changeTabColor(Constants.OBJECT_GREEN);
                            correctButton.changeColor(true,Constants.OBJECT_GREEN);
                        }
                        Results.tab.check();
                    }
                    else
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the method of correction"));
                });

        contentPane.getChildren().addAll(correctionLabel,correctionComboBox,chamberLabel,chamberComboBox,correctButton);
    }

    private void askButton() {
        mainTrait.completeCorrection(false);
        mainTrait.setCompleted(false);
        correctButton.changeColor(true,Constants.OBJECT_YELLOW);
        mainTrait.changeTabColor(Constants.OBJECT_YELLOW);
        Results.tab.resetTab();
    }

    public void setChamberNumbers() {
        chamberOptions = FXCollections.observableArrayList("1");
        for (int i = 2; i <= Integer.parseInt(Saver.getDefaultChamberNumber()); i++)
            chamberOptions.add(""+i);
        costyl = true;
        chamberComboBox.setItems(chamberOptions);
        costyl = false;
    }

    public void reset() {
        if (correctionComboBox.getItems().size()>1)
            correctionComboBox.setValue("");
        else
            correctionComboBox.setValue(correctionComboBox.getItems().get(0));
        chamberComboBox.setValue(" ");
        correctButton.changeColor(true,Constants.OBJECT_GRAY);

        this.setDisable(true);
    }

    public void enable() {
        correctButton.changeColor(true,Constants.OBJECT_YELLOW);

        this.setDisable(false);
    }

    public void setMethodOfCorrection(int userData) {
        costyl = true;
        ObservableList<String> options;
        this.setPrefSize(DPIkiller.kill(230),DPIkiller.kill(95));
        //this.resize();
        chamberLabel.setVisible(false);
        chamberComboBox.setVisible(false);
        mainTrait.relocateQCTests(false);
        correctButton.relocate(DPIkiller.kill(65),DPIkiller.kill(40));
        switch (userData) {
            case 0:
                options = FXCollections.observableArrayList("parallel");
                correctionComboBox.setItems(options);
                correctionComboBox.setValue("parallel");
                this.setPrefSize(DPIkiller.kill(230),DPIkiller.kill(120));
                chamberLabel.setVisible(true);
                chamberComboBox.setVisible(true);
                mainTrait.relocateQCTests(true);
                correctButton.relocate(DPIkiller.kill(65),DPIkiller.kill(65));
                break;
            case 1:
                options = FXCollections.observableArrayList("pre-test");
                correctionComboBox.setItems(options);
                correctionComboBox.setValue("pre-test");
                break;
            case 2:
                options = FXCollections.observableArrayList("post-test");
                correctionComboBox.setItems(options);
                correctionComboBox.setValue("post-test");
                break;
            case 3:
                options = FXCollections.observableArrayList("pre-test", "post-test", "average", "linear", "exponential");
                correctionComboBox.setItems(options);
                correctionComboBox.setValue("");
                break;
        }
        costyl = false;
    }
}

