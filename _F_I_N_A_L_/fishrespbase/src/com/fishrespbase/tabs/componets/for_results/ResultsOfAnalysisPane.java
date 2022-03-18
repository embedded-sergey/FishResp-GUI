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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class ResultsOfAnalysisPane extends BaseTitledPane {

    private ComboBox<String> traitsCombobox;
    private ObservableList<String> traitsOptions;
    private TextField densityTextField;
    private CheckBox densityCheckBox;
    private ColoredButton calculateAndPlotButton, saveButton;

    private FileChooser fileChooser;

    public ResultsOfAnalysisPane(double x, double y, double w, double h) {
        super("Results of analysis",x,y,w,h);

        fileChooser = new FileChooser();

        this.setDisable(true);
    }

    @Override
    protected void createContent() {
        Label traitsLabel = new Label("Target trait");
        traitsLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(10));

        traitsOptions = FXCollections.observableArrayList("Trait 1","Trait 2","Both traits","Metabolic scope");
        traitsCombobox = new ComboBox(traitsOptions);
        traitsCombobox.relocate(DPIkiller.kill(80),DPIkiller.kill(5));
        traitsCombobox.setPrefSize(DPIkiller.kill(140),DPIkiller.kill(20));
        traitsCombobox.setValue("Trait 1");
        traitsCombobox.setOnAction(event -> this.askButton());

        Label densityLabel = new Label("Density [kg/m³]");// of an animal
        densityLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(40));

        densityTextField = new TextField();
        densityTextField.relocate(DPIkiller.kill(95),DPIkiller.kill(35));
        densityTextField.setPrefWidth(DPIkiller.kill(50));
        densityTextField.setText("");

        densityTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue, 0, 2000)) {
                        densityTextField.setText(newValue);
                        askButton();
                    }
                    else
                        densityTextField.setText(oldValue);
                    askButton();
                });

        Label densityCheckLabel = new Label("Simplify");// the final dataset
        densityCheckLabel.relocate(DPIkiller.kill(160),DPIkiller.kill(40));

        densityCheckBox = new CheckBox();
        densityCheckBox.relocate(DPIkiller.kill(205),DPIkiller.kill(40));
        densityCheckBox.setSelected(true);

        calculateAndPlotButton = new ColoredButton(true, Constants.OBJECT_GRAY, "Calculate and plot");
        calculateAndPlotButton.setPrefWidth(DPIkiller.kill(130));
        calculateAndPlotButton.relocate(DPIkiller.kill(5), DPIkiller.kill(65));

        calculateAndPlotButton.setOnAction(event -> {
            if (traitsCombobox.getValue().length()>0 && densityTextField.getText().length()>0) {
                int INDEX = (traitsCombobox.getValue() == traitsOptions.get(0)) ? 1 : (traitsCombobox.getValue() == traitsOptions.get(1)) ? 2 : 2;

                SingleAlert alert = new SingleAlert(AlertType.WAIT, 0, "Calculating and visualizing the results");
                AlertAccordion.pushMessage(alert);
                String quest;
                if (traitsCombobox.getValue() == "Both traits") {
                    quest = "all.final<-calculate.both(Trait.1.slope, Trait.2.slope, density = " + densityTextField.getText() + ", simplify = " + (densityCheckBox.isSelected() ? "TRUE" : "FALSE") + ", plot.BR = TRUE, plot.MR.abs = TRUE, plot.MR.mass = TRUE)";
                    RLogger.log(quest);
                    if (Main.re.eval(quest) != null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));

                        Results.tab.getGraphModule().setBaseFilename("Results_", 6);
                        calculateAndPlotButton.changeColor(true, Constants.OBJECT_GREEN);
                        saveButton.changeColor(true, Constants.OBJECT_YELLOW);
                    }
                    else {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while calculating the results"));

                        Results.tab.getGraphModule().clear();
                        saveButton.changeColor(false, Constants.OBJECT_GRAY);
                    }
                }
                else
                if (traitsCombobox.getValue() == "Metabolic scope") {
                    quest = "all.final<-calculate.both.MS(Trait.1.slope, Trait.2.slope, density = " + densityTextField.getText() + ", simplify = " + (densityCheckBox.isSelected() ? "TRUE" : "FALSE") + ", plot.BR = TRUE, plot.MR.abs = TRUE, plot.MR.mass = TRUE)";
                    RLogger.log(quest);
                    if (Main.re.eval(quest) != null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));

                        Results.tab.getGraphModule().setBaseFilename("Results_", 9);
                        calculateAndPlotButton.changeColor(true, Constants.OBJECT_GREEN);
                        saveButton.changeColor(true, Constants.OBJECT_YELLOW);
                    } else {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while calculating the results"));

                        Results.tab.getGraphModule().clear();
                        saveButton.changeColor(false, Constants.OBJECT_GRAY);
                    }
                }
                else {
                    quest = "Trait." + INDEX + ".final <- calculate.MR(Trait." + INDEX + ".slope, density = " + densityTextField.getText() + ", simplify = " + (densityCheckBox.isSelected() ? "TRUE" : "FALSE") + ", plot.BR = TRUE, plot.MR.abs = TRUE, plot.MR.mass = TRUE)";
                    RLogger.log(quest);
                    if (Main.re.eval(quest) != null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));

                        Results.tab.getGraphModule().setBaseFilename("Results_", 3);
                        calculateAndPlotButton.changeColor(true, Constants.OBJECT_GREEN);
                        saveButton.changeColor(true, Constants.OBJECT_YELLOW);
                    }
                    else {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while calculating the results"));

                        Results.tab.getGraphModule().clear();
                        saveButton.changeColor(false, Constants.OBJECT_GRAY);
                    }
                }

            }
            else {
                if (densityTextField.getText().length()==0)
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Input the density of an animal"));
            }
            Results.tab.changeTabColor(Constants.OBJECT_YELLOW);
        });

        saveButton = new ColoredButton(false, Constants.OBJECT_GRAY, "Export");
        saveButton.setPrefWidth(DPIkiller.kill(70));
        saveButton.relocate(DPIkiller.kill(150), DPIkiller.kill(65));

        saveButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export the results");
            fileChooser.setInitialDirectory(new File(Saver.getLastInputDirectory()));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text table","*.txt"));
            if (Main.OS != "linux")
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV table","*.csv"));
            File file = fileChooser.showSaveDialog(Main.primaryStage);
            if (file != null) {
                Saver.setLastInputDirectory(file.getParent().toString());

                String extension = "";
                int i = file.getPath().toString().lastIndexOf('.');
                if (i >= 0) {
                    extension = file.getPath().toString().substring(i+1);
                }

                if (Main.OS == "linux")
                    extension = "txt";

                if (traitsCombobox.getValue().length()>0) {
                    int INDEX = (traitsCombobox.getValue() == traitsOptions.get(0)) ? 1 : 2;
                    String quest = "";
                    if (traitsCombobox.getValue() != "Both traits" && traitsCombobox.getValue() != "Metabolic scope") {

                        switch (extension.toLowerCase()) {
                            case "csv":
                                if (Main.OS == "windows")
                                    quest = "write.csv(Trait." + INDEX + ".final, \"" + file.getPath().toString().replace('\\', '/') + "\", row.names = FALSE)";
                                else
                                if (Main.OS == "linux")
                                    quest = "write.csv(Trait." + INDEX + ".final, \"" + file.getPath().toString() + "." + extension + "\", row.names = FALSE)";
                                else
                                    quest = "write.csv(Trait." + INDEX + ".final, \"" + file.getPath().toString() + "\", row.names = FALSE)";
                                break;
                            case "txt":
                                if (Main.OS == "windows")
                                    quest = "write.table(Trait." + INDEX + ".final, \"" + file.getPath().toString().replace('\\', '/') + "\", row.names = FALSE)";
                                else
                                if (Main.OS == "linux")
                                    quest = "write.table(Trait." + INDEX + ".final, \"" + file.getPath().toString() + "." + extension + "\", row.names = FALSE)";
                                else
                                    quest = "write.table(Trait." + INDEX + ".final, \"" + file.getPath().toString() + "\", row.names = FALSE)";
                                break;
                            default:
                                System.out.println("Extension error: " + extension.toLowerCase());
                        }
                    }
                    else {

                        switch (extension.toLowerCase()) {
                            case "csv":
                                //System.out.println("write.csv(all.final, \"" + file.getPath().toString().replace('\\', '/') + "\")");
                                quest = "write.csv(all.final, \"" + file.getPath().toString().replace('\\', '/') + "\", row.names = FALSE)";
                                break;
                            case "txt":
                                quest = "write.table(all.final, \"" + file.getPath().toString().replace('\\', '/') + "\", row.names = FALSE)";
                                break;
                            default:
                                System.out.println("Extension error: " + extension.toLowerCase());
                        }

                    }
                    SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Exporting the results");
                    AlertAccordion.pushMessage(alert);

                    RLogger.log(quest);
                    String logfile = file.getPath().toString().replace('\\', '/');
                    logfile = logfile.substring(0, logfile.lastIndexOf('/') + 1) + "log.txt";
                    RLogger.writeToFile(logfile);

                    if (Main.re.eval(quest) != null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));

                        saveButton.changeColor(true, Constants.OBJECT_GREEN);
                        Results.tab.changeTabColor(Constants.OBJECT_GREEN);
                    }
                    else {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while exporting the results"));

                        saveButton.changeColor(true, Constants.OBJECT_YELLOW);
                    }
                }
            }

        });

        contentPane.getChildren().addAll(traitsLabel,traitsCombobox,densityLabel,densityTextField,
                densityCheckLabel,densityCheckBox,calculateAndPlotButton,saveButton);
    }

    public void renameTraits(String first, String second) {
        traitsOptions.set(0, first);
        if (second.length()!=0)
            if (traitsOptions.size()==4)
                traitsOptions.set(1, second);
            else {
                traitsOptions.add(1, second);
                traitsOptions.add(2, "Both traits");
                traitsOptions.add(3, "Metabolic scope");
            }
        else
        if (traitsOptions.size()==4) {
            traitsOptions.remove(1);
            traitsOptions.remove(1);
            traitsOptions.remove(1);
        }

        traitsCombobox.setValue(first);
    }

    private void askButton() {
        calculateAndPlotButton.changeColor(true,Constants.OBJECT_YELLOW);
        saveButton.changeColor(false,Constants.OBJECT_GRAY);
        Results.tab.changeTabColor(Constants.OBJECT_YELLOW);
    }

    public void reset() {
        traitsCombobox.setValue(traitsOptions.get(0));
        densityTextField.setText("1000");
        densityCheckBox.setSelected(true);
        calculateAndPlotButton.changeColor(true,Constants.OBJECT_GRAY);
        saveButton.changeColor(false,Constants.OBJECT_GRAY);
        this.setDisable(true);
    }

    public void enable() {
        calculateAndPlotButton.changeColor(true,Constants.OBJECT_YELLOW);
        saveButton.changeColor(false,Constants.OBJECT_GRAY);
        this.setDisable(false);
    }
}
