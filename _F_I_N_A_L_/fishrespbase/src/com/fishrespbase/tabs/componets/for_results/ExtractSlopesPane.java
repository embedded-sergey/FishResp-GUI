package com.fishrespbase.tabs.componets.for_results;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.tabs.Results;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class ExtractSlopesPane extends BaseTitledPane {
    private int INDEX;
    private TextField numberTextField, rsquaredTextField;
    private ComboBox<String> methodComboBox;
    private ColoredButton button;
    private TextField lengthTextField;
    private TextField percentTextField;
    private TextField qTextField;
    private TextField GTextField;

    public ExtractSlopesPane(String label, int INDEX, double x, double y, double w, double h) {
        super("Extract slopes of "+label, x, y, w, h);

        this.INDEX = INDEX;

        this.setDisable(true);
    }

    @Override
    protected void createContent() {
        Label numberLabel = new Label("Number");
        numberLabel.relocate(DPIkiller.kill(5), DPIkiller.kill(40));

        numberTextField = new TextField();
        numberTextField.relocate(DPIkiller.kill(61), DPIkiller.kill(35));
        numberTextField.setPrefSize(DPIkiller.kill(40), DPIkiller.kill(20));

        numberTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> askButton());

        Label rsquaredLabel = new Label("r²");
        rsquaredLabel.relocate(DPIkiller.kill(163), DPIkiller.kill(70));

        rsquaredTextField = new TextField();
        rsquaredTextField.relocate(DPIkiller.kill(175), DPIkiller.kill(65));
        rsquaredTextField.setPrefSize(DPIkiller.kill(45), DPIkiller.kill(20));

        rsquaredTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue, 0, 1)) {
                        rsquaredTextField.setText(newValue);
                        askButton();
                    }
                    else
                        rsquaredTextField.setText(oldValue);
                });

        Label methodLabel = new Label("Method");
        methodLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        ObservableList<String> options = FXCollections.observableArrayList("all","min","max","lower tail","upper tail","calcSMR: mlnd","calcSMR: quant","calcSMR: low10","calcSMR: low10pc");
        methodComboBox = new ComboBox(options);
        methodComboBox.relocate(DPIkiller.kill(61),DPIkiller.kill(5));
        methodComboBox.setPrefSize(DPIkiller.kill(159),DPIkiller.kill(20));
        methodComboBox.setValue(" ");
        methodComboBox.setOnAction(event -> {
            //rsquaredTextField.setText("");
            //lengthTextField.setText("");
            //numberTextField.setText("");
            //percentTextField.setText("");
            //qTextField.setText("");
            updateFieldsAccordingToMethod();
            this.askButton();
        });

        Label lengthLabel = new Label("Length [s]");
        lengthLabel.relocate(DPIkiller.kill(120),DPIkiller.kill(40));

        lengthTextField = new TextField("");
        lengthTextField.relocate(DPIkiller.kill(175),DPIkiller.kill(35));
        lengthTextField.setPrefSize(DPIkiller.kill(45),DPIkiller.kill(20));


        Label percentLabel = new Label("%");
        percentLabel.relocate(DPIkiller.kill(108),DPIkiller.kill(70));

        percentTextField = new TextField();
        percentTextField.relocate(DPIkiller.kill(120),DPIkiller.kill(65));
        percentTextField.setPrefWidth(DPIkiller.kill(35));
        percentTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkInt(newValue, 0, 99)) {
                        percentTextField.setText(newValue);
                        askButton();
                    }
                    else
                        percentTextField.setText(oldValue);
                });

        Label qLabel = new Label("p");
        qLabel.relocate(DPIkiller.kill(51),DPIkiller.kill(70));

        qTextField = new TextField();
        qTextField.relocate(DPIkiller.kill(61),DPIkiller.kill(65));
        qTextField.setPrefWidth(DPIkiller.kill(40));
        qTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue, 0, 1)) {
                        qTextField.setText(newValue);
                        askButton();
                    }
                    else
                        qTextField.setText(oldValue);
                });

        Label Glabel = new Label("G");
        Glabel.relocate(DPIkiller.kill(5), DPIkiller.kill(70));
        GTextField = new TextField();
        GTextField.relocate(DPIkiller.kill(18),DPIkiller.kill(65));
        GTextField.setPrefWidth(DPIkiller.kill(25));
        GTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkInt(newValue, 1, 9)) {
                        GTextField.setText(newValue);
                        askButton();
                    }
                    else
                        GTextField.setText(oldValue);
                });;

        button = new ColoredButton(true, Constants.OBJECT_GRAY, "Extract slopes");
        button.setPrefWidth(DPIkiller.kill(100));
        button.relocate(DPIkiller.kill(65), DPIkiller.kill(95));

        button.setOnAction(event -> {
            if (methodComboBox.getValue() == " ") {
                AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Select the method of slope extraction"));
            }
            else {
                Results.tab.changeTabColor(Constants.OBJECT_YELLOW);
                if (numberTextField.getText().length()==0 && (methodComboBox.getValue() == "min" || methodComboBox.getValue() == "max")) {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the number of slopes"));
                    Results.tab.extractComplete(false, INDEX);
                    return;
                }
                if (rsquaredTextField.getText().length() == 0) {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Set the minimum value of r²"));
                    Results.tab.extractComplete(false, INDEX);
                    return;
                }
                if (GTextField.getText().length() == 0 && methodComboBox.getValue() == "calcSMR: mlnd") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING2LINES, Constants.DEFAULT_ALERTLIFETIME, "Set the G value from 1 to 9\n(see Chabot et al, 2016 in J Fish Biol)"));
                    Results.tab.extractComplete(false, INDEX);
                    return;
                }
                if (qTextField.getText().length() == 0 && methodComboBox.getValue() == "calcSMR: quant") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING2LINES, Constants.DEFAULT_ALERTLIFETIME, "Set the quantile value\n(see Chabot et al, 2016 in J Fish Biol)"));
                    Results.tab.extractComplete(false, INDEX);
                    return;
                }
                if (percentTextField.getText().length() == 0 && methodComboBox.getValue() == "lower tail") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input percentage of a lower tail"));
                    Results.tab.extractComplete(false, INDEX);
                    return;
                }
                if (percentTextField.getText().length() == 0 && methodComboBox.getValue() == "upper tail") {
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input percentage of an upper tail"));
                    Results.tab.extractComplete(false, INDEX);
                    return;
                }

            /*if ((numberTextField.getText().length()>0 || !(methodComboBox.getValue() == "min" && methodComboBox.getValue() == "max")) && rsquaredTextField.getText().length()>0) {*/
                SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Extracting slopes");
                AlertAccordion.pushMessage(alert);

                String method;
                boolean calcSMR;
                switch (methodComboBox.getValue()) {
                    case "upper tail":
                        method = "upper.tail";
                        calcSMR = false;
                        break;
                    case "lower tail":
                        method = "lower.tail";
                        calcSMR = false;
                        break;
                    case "calcSMR: mlnd":
                        method = "calcSMR.mlnd";
                        calcSMR = true;
                        break;
                    case "calcSMR: quant":
                        method = "calcSMR.quant";
                        calcSMR = true;
                        break;
                    case "calcSMR: low10":
                        method = "calcSMR.low10";
                        calcSMR = true;
                        break;
                    case "calcSMR: low10pc":
                        method = "calcSMR.low10pc";
                        calcSMR = true;
                        break;
                    default:
                        method = methodComboBox.getValue();
                        calcSMR = false;
                }
                //String quest = "Trait." + INDEX + ".slope <- extract.slope(Trait." + INDEX + ".clean, method = \"" + method + "\", n.slope = " + numberTextField.getText() + ", r2=" + rsquaredTextField.getText() + ", length = " + lengthTextField.getText() + ", plot.data = TRUE)";
                String quest = "Trait." + INDEX + ".slope <- extract.slope(Trait." + INDEX + ".clean, method = \"" + method + "\", ";
                quest += "n.slope = " + (numberTextField.getText().length()>0?numberTextField.getText():1000) + ", r2 = " + rsquaredTextField.getText() + ", ";
                quest += "length = " + (lengthTextField.getText().length()>0?lengthTextField.getText():99999) + ", ";
                quest += "G = 1:"+ (GTextField.getText().length()>0?GTextField.getText():"4") + ", ";
                quest += "p = "+ (qTextField.getText().length()>0?qTextField.getText():"0.1")+", ";
                quest += "percent = "+ (percentTextField.getText().length()>0?percentTextField.getText():10) +")";

                RLogger.log(quest);
                if (Main.re.eval(quest) != null) {
                    AlertAccordion.remove(alert);
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));


                    //Results.tab.getGraphModule().setBaseFilename("extracted_slopes_", 1);

                    button.changeColor(true, Constants.OBJECT_GREEN);
                    Results.tab.extractComplete(true, INDEX);
                    if (calcSMR) {
                        Results.tab.setCompleteTrait(INDEX, 2);
                    }
                    else {
                        Results.tab.setCompleteTrait(INDEX, 1);
                    }
                    Results.tab.setActiveTrait(INDEX);
                }
                else {
                    AlertAccordion.remove(alert);
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while extracting slope"));

                    Results.tab.getGraphModule().clear();
                }
            }

            //Results.tab.changeTabColor(Constants.OBJECT_YELLOW);
        });

        contentPane.getChildren().addAll(methodLabel,methodComboBox,
                lengthLabel, lengthTextField, numberLabel,numberTextField, rsquaredLabel,rsquaredTextField,
                qLabel, qTextField, Glabel, GTextField, percentLabel, percentTextField, button);
    }

    private void updateFieldsAccordingToMethod() {
        switch (methodComboBox.getValue()) {
            case "all":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(true);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "min":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(false);
                percentTextField.setDisable(true);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "max":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(false);
                percentTextField.setDisable(true);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "upper tail":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(false);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "lower tail":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(false);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "calcSMR: mlnd":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(true);
                qTextField.setDisable(true);
                GTextField.setDisable(false);
                GTextField.setText("4");
                break;
            case "calcSMR: quant":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(true);
                qTextField.setDisable(false);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "calcSMR: low10":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(true);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
            case "calcSMR: low10pc":
                rsquaredTextField.setDisable(false);
                lengthTextField.setDisable(false);
                numberTextField.setDisable(true);
                percentTextField.setDisable(true);
                qTextField.setDisable(true);
                GTextField.setDisable(true);
                GTextField.setText("");
                break;
        }
    }

    private void askButton() {
        button.changeColor(true,Constants.OBJECT_YELLOW);
        Results.tab.extractComplete(false, INDEX);
        Results.tab.changeTabColor(Constants.OBJECT_YELLOW);
    }

    public String getNumberValue() {
        return numberTextField.getText();
    }

    public String getRSquaredValue() {
        return rsquaredTextField.getText();
    }

    public String getMethodValue() {
        return methodComboBox.getValue();
    }

    public void renameTrait(String label) {
        setText("Extract slopes of "+label);
    }

    public void reset() {
        Results.tab.setCompleteTrait(INDEX, 0);
        //numberTextField.setText("");
        //rsquaredTextField.setText("");
        //methodComboBox.setValue(" ");
        //lengthTextField.setText("");
        //percentTextField.setText("");
        //qTextField.setText("");
        //GTextField.setText("");

        if (methodComboBox.getValue().isEmpty() || methodComboBox.getValue().equals(" ")) {
            numberTextField.setDisable(false);
            methodComboBox.setDisable(false);
            percentTextField.setDisable(false);
            qTextField.setDisable(false);
            GTextField.setDisable(false);
        } else {
            updateFieldsAccordingToMethod();
        }


        if (this.isDisabled())
            button.changeColor(true,Constants.OBJECT_GRAY);
        else
            button.changeColor(true,Constants.OBJECT_YELLOW);

        this.setDisable(true);
    }

    public void enable() {
        this.setDisable(false);

        button.changeColor(true,Constants.OBJECT_YELLOW);
    }
}
