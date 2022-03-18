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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class GlobalSettingsPane extends BaseTitledPane {
    private ColoredButton saveButton;

    public GlobalSettingsPane(double x, double y, double w, double h) {
        super("Global settings", x, y, w, h);
        createContent();
    }

    @Override
    protected void createContent() {
        Pane dateFormatPane = new Pane();
        dateFormatPane.relocate(DPIkiller.kill(5),DPIkiller.kill(10));
        dateFormatPane.setPrefSize(DPIkiller.kill(170),DPIkiller.kill(35));
        dateFormatPane.setBorder(new Border(new BorderStroke(Color.rgb(186,186,186),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label dateFormatLabel = new Label("Date format");
        dateFormatLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        ObservableList<String> dateFormatOptions = FXCollections.observableArrayList("DMY", "MDY", "YMD");
        ComboBox dateFormatComboBox = new ComboBox(dateFormatOptions);
        dateFormatComboBox.relocate(DPIkiller.kill(80), DPIkiller.kill(5));
        dateFormatComboBox.setPrefSize(DPIkiller.kill(80),DPIkiller.kill(20));
        dateFormatComboBox.setValue(" ");

        dateFormatPane.getChildren().addAll(dateFormatLabel, dateFormatComboBox);

        Pane radioButtonPane = new Pane();
        radioButtonPane.relocate(DPIkiller.kill(5),DPIkiller.kill(50));
        radioButtonPane.setPrefSize(DPIkiller.kill(170),DPIkiller.kill(129));
        radioButtonPane.setBorder(new Border(new BorderStroke(Color.rgb(186,186,186),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label radioPaneLabel = new Label("Background respiration tests:");
        radioPaneLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(5));

        ToggleGroup bgrRadioGroup = new ToggleGroup();

        RadioButton radioButton1 = new RadioButton("only pre-test");
        radioButton1.setToggleGroup(bgrRadioGroup);
        radioButton1.relocate(DPIkiller.kill(7), DPIkiller.kill(28));
        radioButton1.setUserData(1);
        RadioButton radioButton2 = new RadioButton("only post-test");
        radioButton2.setToggleGroup(bgrRadioGroup);
        radioButton2.relocate(DPIkiller.kill(7),DPIkiller.kill(53));
        radioButton2.setUserData(2);
        RadioButton radioButton3 = new RadioButton("pre- and post-tests");
        radioButton3.setToggleGroup(bgrRadioGroup);
        radioButton3.relocate(DPIkiller.kill(7),DPIkiller.kill(78));
        radioButton3.setUserData(3);
        RadioButton radioButton4 = new RadioButton("empty chamber");
        radioButton4.setToggleGroup(bgrRadioGroup);
        radioButton4.relocate(DPIkiller.kill(7),DPIkiller.kill(103));
        radioButton4.setUserData(0);

        radioButtonPane.getChildren().addAll(radioPaneLabel,radioButton1,radioButton2,radioButton3,radioButton4);

        Pane traitsPane = new Pane();
        traitsPane.relocate(DPIkiller.kill(180),DPIkiller.kill(10));
        traitsPane.setPrefSize(DPIkiller.kill(233),DPIkiller.kill(67));
        traitsPane.setBorder(new Border(new BorderStroke(Color.rgb(186,186,186),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
/*
        Label nameofTrait1Label, nameofTrait2Label;
        nameofTrait1Label = new Label("Name of Trait 1:");
        nameofTrait2Label = new Label("Name of Trait 2:");

        nameofTrait1Label.relocate(5,10);
        nameofTrait2Label.relocate(5,45);
*/
        Label numberOfTraitsLabel = new Label("Number of metabolic traits");
        numberOfTraitsLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        ObservableList<String> numberOfTraitsOptions = FXCollections.observableArrayList("1", "2");
        ComboBox numberOfTraitsComboBox = new ComboBox(numberOfTraitsOptions);
        numberOfTraitsComboBox.relocate(DPIkiller.kill(160), DPIkiller.kill(7));
        numberOfTraitsComboBox.setPrefSize(DPIkiller.kill(65),DPIkiller.kill(20));
        numberOfTraitsComboBox.setValue("1");

        TextField nameofTrait1Field, nameofTrait2Field;
        nameofTrait1Field = new TextField("Trait 1");
        nameofTrait2Field = new TextField("Trait 2");
/*
        nameofTrait1Field.relocate(115,7);
        nameofTrait1Field.setPrefSize(100,20);

        nameofTrait2Field.relocate(115,42);
        nameofTrait2Field.setPrefSize(100,20);
*/
        nameofTrait1Field.relocate(DPIkiller.kill(8),DPIkiller.kill(37));
        nameofTrait1Field.setPrefSize(DPIkiller.kill(105),DPIkiller.kill(20));

        nameofTrait2Field.setDisable(true);
        //nameofTrait2Field.setDisable(false);
        nameofTrait2Field.relocate(DPIkiller.kill(120),DPIkiller.kill(37));
        nameofTrait2Field.setPrefSize(DPIkiller.kill(105),DPIkiller.kill(20));

        traitsPane.getChildren().addAll(numberOfTraitsLabel, numberOfTraitsComboBox,/*nameofTrait1Label,nameofTrait2Label,*/nameofTrait1Field,nameofTrait2Field);

        Pane params = new Pane();
        params.relocate(DPIkiller.kill(180),DPIkiller.kill(82));
        params.setPrefSize(DPIkiller.kill(233),DPIkiller.kill(97));
        params.setBorder(new Border(new BorderStroke(Color.rgb(186,186,186),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label loggerSoftwareLabel, chamberNumberLabel;

        loggerSoftwareLabel = new Label("Logger software");
        loggerSoftwareLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        ObservableList<String> softwareOptions = FXCollections.observableArrayList("AutoResp", "FishResp", "QboxAqua");
        ComboBox softwareComboBox = new ComboBox(softwareOptions);
        softwareComboBox.relocate(DPIkiller.kill(105), DPIkiller.kill(7));
        softwareComboBox.setPrefSize(DPIkiller.kill(120),DPIkiller.kill(20));
        softwareComboBox.setValue(" ");

        chamberNumberLabel = new Label("Number of chambers");
        chamberNumberLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(40));

        ObservableList<String> chamberOptions = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8");
        ComboBox chamberComboBox = new ComboBox(chamberOptions);
        chamberComboBox.relocate(DPIkiller.kill(135), DPIkiller.kill(37));
        chamberComboBox.setPrefSize(DPIkiller.kill(90), DPIkiller.kill(20));
        chamberComboBox.setValue(" ");

        Label douLabel = new Label("Dissolved oxygen unit*");
        douLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(70));

        ObservableList<String> douOptions = FXCollections.observableArrayList("mg O2/L", "mmol O2/L", "ml O2/L");
        ComboBox douComboBox = new ComboBox(douOptions);
        douComboBox.relocate(DPIkiller.kill(135), DPIkiller.kill(67));
        douComboBox.setPrefSize(DPIkiller.kill(90),DPIkiller.kill(20));
        douComboBox.setValue(" ");

        params.getChildren().addAll(loggerSoftwareLabel,softwareComboBox,chamberNumberLabel,chamberComboBox, douLabel,douComboBox);


        saveButton = new ColoredButton(true, Constants.OBJECT_YELLOW,"Save global settings");
        saveButton.relocate(DPIkiller.kill(140),DPIkiller.kill(185));
        saveButton.setMaxWidth(DPIkiller.kill(140));
        saveButton.setMinWidth(DPIkiller.kill(140));

        saveButton.setOnAction(
            (ActionEvent event) -> {
                if (dateFormatComboBox.getValue() == " ") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the date format"));
                    return;
                }
                else {
                    String quest = "DATEFORMAT <- \"" + dateFormatComboBox.getValue() +"\"";
                    RLogger.log(quest);
                    Main.re.eval(quest);
                    Saver.setDateFormat("" + dateFormatComboBox.getValue());
                    Traits.setDateFormat("" + dateFormatComboBox.getValue());
                }
                if (softwareComboBox.getValue() == " ") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the logger software"));
                    return;
                }
                if (chamberComboBox.getValue() == " ") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the number of chambers"));
                    return;
                }
                if (bgrRadioGroup.getSelectedToggle() != null) {

                    if (nameofTrait2Field.getText().length()==0) {
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the name of a trait"));
                        return;
                    }

                    if (nameofTrait1Field.getText().length()>0) {
                        Traits.trait1.renameTab(nameofTrait1Field.getText());
                        String quest = "Trait.1.name <- \"" + nameofTrait1Field.getText() +"\"";
                        RLogger.log(quest);
                        Main.re.eval(quest);
                    }
                    else {
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the name of a trait"));
                        return;
                    }

                    if (chamberComboBox.getValue() == "1" && (int)bgrRadioGroup.getSelectedToggle().getUserData() == 0){
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING2LINES, Constants.DEFAULT_ALERTLIFETIME, "The number of chambers must be more than one if an empty chamber is used"));
                        return;
                    }

                    if (douComboBox.getValue() == " ") {
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the dissolved oxygen unit"));
                        return;
                    }
                    else {
                        String tmp = douComboBox.getValue().toString();
                        String quest = "DOUNIT <- \"" + tmp.substring(0, tmp.length()-2) + "\"";
                        RLogger.log(quest);
                        Main.re.eval(quest);
                    }

                    General.tab.setSelectBGRTests((int)bgrRadioGroup.getSelectedToggle().getUserData());
                    BGR.tab.resetTab();
                    BGR.tab.enableTests((int)bgrRadioGroup.getSelectedToggle().getUserData());
                    Traits.setSelectedBGRTests((int)bgrRadioGroup.getSelectedToggle().getUserData());


                    boolean needBGR = false,
                            needTrait2 = false;
                    int bgr = -1,
                        trait2 = -1;

                    if ((int)bgrRadioGroup.getSelectedToggle().getUserData() != 0) {
                        //Main.tabPane.getTabs().add(1,BGR.tab);
                        needBGR = true;
                    }
                    else {
                        //if (Main.tabPane.getTabs().get(1)==BGR.tab)
                        //  Main.tabPane.getTabs().remove(1);
                    }

                    for (int i = 0; i < Main.tabPane.getTabs().size(); i++)
                        if (Main.tabPane.getTabs().get(i) == BGR.tab) {
                            bgr = i;
                            break;
                        }

                    for (int i = 0; i < Main.tabPane.getTabs().size(); i++)
                        if (Main.tabPane.getTabs().get(i) == Traits.trait2) {
                            trait2 = i;
                            break;
                        }

                    if (numberOfTraitsComboBox.getValue() == "2") {
                        needTrait2 = true;
                        Traits.trait2.renameTab(nameofTrait2Field.getText());
                        Saver.setTrait2Enabled(true);
                        Results.tab.secondTrait(true);
                        String quest = "Trait.2.name <- \"" + nameofTrait2Field.getText() + "\"";
                        RLogger.log(quest);
                        Main.re.eval(quest);
                    }
                    else {
                        Saver.setTrait2Enabled(false);
                        Results.tab.secondTrait(false);
                    }
                    /*
                    if (nameofTrait2Field.getText().length()>0) {
                        needTrait2 = true;
                        Traits.trait2.renameTab(nameofTrait2Field.getText());
                        Saver.setTrait2Enabled(true);
                        Results.tab.secondTrait(true);
                        Main.re.eval("Trait.2.name <- \"" + nameofTrait2Field.getText() + "\"");

                    }

                    else {
                        /*
                        for (int i = 0; i < Main.tabPane.getTabs().size(); i++)
                        if (Main.tabPane.getTabs().get(i)==Traits.trait2) {
                            Main.tabPane.getTabs().remove(i);
                            break;
                        }
                        *//*
                        Saver.setTrait2Enabled(false);
                        Results.tab.secondTrait(false);

                    }
                    */

                    if (trait2!=-1 && !needTrait2) {
                        Main.tabPane.getTabs().remove(trait2);
                        trait2 = -1;
                    }
                    if (trait2==-1 && needTrait2) {
                        if (bgr==-1) {
                            Main.tabPane.getTabs().add(2, Traits.trait2);
                            trait2 = 1;
                        }
                        else {
                            Main.tabPane.getTabs().add(3, Traits.trait2);
                            trait2 = 1;
                        }

                    }

                    if (bgr!=-1 && !needBGR)
                        Main.tabPane.getTabs().remove(bgr);
                    if (bgr==-1 && needBGR) {
                        Main.tabPane.getTabs().add(1, BGR.tab);
                        bgr = 1;
                    }


                    BGR.tab.setChamberAndSoftware(chamberComboBox.getValue().toString(), softwareComboBox.getValue().toString());

                    General.tab.getIndividualInfoPane().setTable(Integer.parseInt(chamberComboBox.getValue().toString()));

                    saveButton.changeColor(true,Constants.OBJECT_GREEN);

                    Saver.setDefaultChamberNumber((String) chamberComboBox.getValue());
                    Saver.setDefaultLoggerSoftware((String) softwareComboBox.getValue());

                    General.tab.setGlobalData(true);
                    General.tab.changeTabColor(Constants.OBJECT_YELLOW);

                    Traits.setChamberNumbers();
                    Traits.trait1.resetTab();
                    Traits.trait2.resetTab();
                    Traits.trait1.disable();
                    Traits.trait2.disable();

                    Results.tab.renameTraits(nameofTrait1Field.getText(),nameofTrait2Field.getText(), trait2);
                    Results.tab.resetTab();
                    Results.tab.setChamberNumbers();
                    Results.tab.changeTabColor(Constants.OBJECT_GRAY);

                    AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));
                }
                else
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING2LINES, Constants.DEFAULT_ALERTLIFETIME, "Select the test for background respiration"));
            });

        bgrRadioGroup.selectedToggleProperty().addListener(
            (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                General.tab.changedParam();
            });

        numberOfTraitsComboBox.setOnAction(event -> {
            General.tab.changedParam();
            if (numberOfTraitsComboBox.getValue() == "1") {
                nameofTrait2Field.setDisable(true);
                nameofTrait2Field.setText("Trait 2");
            }
            else {
                nameofTrait2Field.setDisable(false);
            }
        });

        softwareComboBox.setOnAction(event -> {
            General.tab.changedParam();
        });

        chamberComboBox.setOnAction(event -> {
            General.tab.changedParam();
        });

        contentPane.getChildren().addAll(dateFormatPane, radioButtonPane, traitsPane,params,saveButton);
    }

    public void reset() {
        saveButton.changeColor(true,Constants.OBJECT_YELLOW);
    }
}
