package com.fishrespbase.tabs.componets.for_bgr;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.tabs.BGR;
import com.fishrespbase.tabs.componets.GraphModule;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class DataPane extends BaseTitledPane {

    private String r_variableName;
    private boolean dataLoaded;
    private int graphs;
    private String software;

    private FileChooser fileChooser;

    private ColoredButton importDataButton;
    private ColoredButton qcTempButton, qcOxButton;
    private TextField fileAdressField;
    private GraphModule graphModule;
    private ComboBox<String> chamberComboBox, softwareComboBox;

    private TextField mtw;

    public DataPane(String label, GraphModule graphModule, String r_variableName, double x, double y, double w, double h) {
        super(label, x, y, w, h);
        setDisable(true);

        fileChooser = new FileChooser();

        setR_Variable(r_variableName);
        linkGraphModule(graphModule);
    }

    @Override
    protected void createContent() {
        fileAdressField = new TextField();
        fileAdressField.relocate(DPIkiller.kill(5), DPIkiller.kill(5));
        fileAdressField.setPrefSize(DPIkiller.kill(190), DPIkiller.kill(25));

        Button openFileButton = new Button();
        openFileButton.relocate(DPIkiller.kill(195), DPIkiller.kill(5));
        openFileButton.setPrefSize(DPIkiller.kill(25), DPIkiller.kill(25));
        openFileButton.setStyle("-fx-background-image: url('/com/fishrespbase/img/OpenFileIcon.png')");
        openFileButton.setOnAction(
            (ActionEvent event) -> {
                fileChooser.setTitle("Select raw data");
                if (Saver.getLastInputDirectory().length() > 0)
                    fileChooser.setInitialDirectory(new File(Saver.getLastInputDirectory()));
                File selectedFile = fileChooser.showOpenDialog(Main.primaryStage);
                if (selectedFile != null) {
                    Saver.setLastInputDirectory(selectedFile.getPath().substring(0,selectedFile.getPath().length()-selectedFile.getName().length()));
                    fileAdressField.setText(selectedFile.getPath());
                }
            });

        Label loggerSoftwareLabel = new Label("Logger software");
        loggerSoftwareLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(45));

        ObservableList<String> softwareOptions = FXCollections.observableArrayList("AutoResp","FishResp","QboxAqua");
        softwareComboBox = new ComboBox(softwareOptions);
        softwareComboBox.relocate(DPIkiller.kill(100),DPIkiller.kill(40));
        softwareComboBox.setPrefSize(DPIkiller.kill(120),DPIkiller.kill(20));
        softwareComboBox.setValue(" ");

        softwareComboBox.setOnAction(event -> {
            askButton();
        });


        Label chamberNumberLabel = new Label("Number of chambers");
        chamberNumberLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(80));

        ObservableList<String> chamberOptions = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");
        chamberComboBox = new ComboBox(chamberOptions);
        chamberComboBox.relocate(DPIkiller.kill(135),DPIkiller.kill(75));
        chamberComboBox.setPrefSize(DPIkiller.kill(85),DPIkiller.kill(20));
        chamberComboBox.setValue(" ");

        chamberComboBox.setOnAction(event -> {
            askButton();
        });

        Label mtwLabel = new Label("To increase wait phase by (s)");
        mtwLabel.relocate(DPIkiller.kill(5), DPIkiller.kill(115));

        mtw = new TextField("0");
        mtw.relocate(DPIkiller.kill(162), DPIkiller.kill(110));
        mtw.setPrefWidth(DPIkiller.kill(58));
        mtw.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (TextInputCheckers.checkInt(newValue, 0, 9999)) {
                mtw.setText(newValue);
                askButton();
            }
            else
                mtw.setText(oldValue);

            if (newValue.length() == 0)
                mtw.setText("0");
        });

        importDataButton = new ColoredButton(true, Constants.OBJECT_GRAY, "Import data");
        importDataButton.setPrefWidth(DPIkiller.kill(100));
        importDataButton.relocate(DPIkiller.kill(65), DPIkiller.kill(140));

        qcTempButton = new ColoredButton(false, Constants.OBJECT_GRAY, "QC: Temperature");
        qcTempButton.setPrefWidth(DPIkiller.kill(170));
        qcTempButton.relocate(DPIkiller.kill(30), DPIkiller.kill(170));

        qcOxButton = new ColoredButton(false, Constants.OBJECT_GRAY, "QC: Oxygen");
        qcOxButton.setPrefWidth(DPIkiller.kill(170));
        qcOxButton.relocate(DPIkiller.kill(30), DPIkiller.kill(200));

        importDataButton.setOnAction(
            (ActionEvent event) -> {
                if (fileAdressField.getText().length()>0) {
                    StringBuffer tmp = new StringBuffer(fileAdressField.getText());
                    int length = tmp.length();
                    for (int i = 0; i < length; i++) {
                        if (tmp.charAt(i) == '\\')
                            tmp.insert(i++,'\\');
                    }

                    SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Importing raw data");
                    AlertAccordion.pushMessage(alert);

                    String quest = r_variableName+" <- import.test(\""+tmp+"\", meas.to.wait = "+mtw.getText()+", n.chamber = "+chamberComboBox.getValue()+", logger = \""+softwareComboBox.getValue()+"\")";
                    RLogger.log(quest);
                    if (Main.re.eval(quest) == null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while importing raw data"));

                        dataLoaded = false;

                        qcTempButton.changeColor(false, Constants.OBJECT_GRAY);
                        qcOxButton.changeColor(false, Constants.OBJECT_GRAY);
                        importDataButton.changeColor(true, Constants.OBJECT_YELLOW);

                        software = "";
                        graphs = 0;
                    }
                    else{
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));

                        dataLoaded = true;

                        qcTempButton.changeColor(true, Constants.OBJECT_BLUE);
                        qcOxButton.changeColor(true, Constants.OBJECT_BLUE);
                        importDataButton.changeColor(true, Constants.OBJECT_GREEN);

                        graphs = Integer.parseInt(chamberComboBox.getValue().toString());
                        software = softwareComboBox.getValue().toString();
                    }
                    BGR.tab.recolorTab();
                }
                else
                    AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Specify the file address"));
            });

        qcTempButton.setOnAction(
                (ActionEvent event) -> {
                if (dataLoaded) {
                    SingleAlert alert = new SingleAlert(AlertType.WAIT, 0, "Plotting a graph");
                    AlertAccordion.pushMessage(alert);

                    String quest = "QC.temperature(" + r_variableName + ")";
                    RLogger.log(quest);
                    if (Main.re.eval(quest) == null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while plotting a graph"));
                    }
                    else {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));
                        if (graphs > 4)
                            graphModule.setBaseFilename("test.temperature_",2);
                        else
                            graphModule.setBaseFilename("test.temperature_",1);

                        graphModule.resizeGraph();
                    }
                }
            });

        qcOxButton.setOnAction(
                (ActionEvent event) -> {
                if (dataLoaded) {
                    SingleAlert alert = new SingleAlert(AlertType.WAIT, 0, "Plotting a graph");
                    AlertAccordion.pushMessage(alert);

                    String quest = "QC.oxygen(" + r_variableName + ")";
                    RLogger.log(quest);
                    if (Main.re.eval(quest) == null) {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while plotting a graph"));
                    }
                    else {
                        AlertAccordion.remove(alert);
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));

                        if (graphs > 4)
                            graphModule.setBaseFilename("test.oxygen_", 2);
                        else
                            graphModule.setBaseFilename("test.oxygen_", 1);


                        graphModule.resizeGraph();
                    }
                }
            });

        contentPane.getChildren().addAll(fileAdressField, openFileButton,
                chamberNumberLabel, chamberComboBox, loggerSoftwareLabel, softwareComboBox,
                mtwLabel, mtw,
                importDataButton, qcTempButton, qcOxButton);

    }

    private void askButton() {
        if (chamberComboBox.getValue().toString() != " ") {
            if (Integer.parseInt(chamberComboBox.getValue().toString()) == graphs
                    && softwareComboBox.getValue().toString() == software) {
                importDataButton.changeColor(true, Constants.OBJECT_GREEN);
                qcOxButton.changeColor(true, Constants.OBJECT_GRAY);
                qcTempButton.changeColor(true, Constants.OBJECT_GRAY);
            }
        }
        else {
            importDataButton.changeColor(true, Constants.OBJECT_YELLOW);
            qcOxButton.changeColor(false, Constants.OBJECT_GRAY);
            qcTempButton.changeColor(false, Constants.OBJECT_GRAY);
        }
        BGR.tab.setBgrReady(false);
        BGR.tab.recolorTab();
    }

    public void linkGraphModule(GraphModule graphModule) {
        this.graphModule = graphModule;
    }

    private void setR_Variable(String r_variableName) {
        this.r_variableName = r_variableName;
    }

    public boolean getDataLoaded() {
        return dataLoaded;
    }

    public void reset() {
        graphs = 0;
        software = "";

        if (this.isDisabled())
            importDataButton.changeColor(true,Constants.OBJECT_GRAY);
        else
            importDataButton.changeColor(true,Constants.OBJECT_YELLOW);
        qcOxButton.changeColor(false,Constants.OBJECT_GRAY);
        qcTempButton.changeColor(false,Constants.OBJECT_GRAY);

        dataLoaded = false;

        fileAdressField.setText("");
        chamberComboBox.setValue(Saver.getDefaultChamberNumber());

        softwareComboBox.setValue(Saver.getDefaultLoggerSoftware());
        this.setDisable(true);
    }

    public void setEnabled(boolean state) {
        this.setDisable(!state);
        if (!state)
            importDataButton.changeColor(true,Constants.OBJECT_GRAY);
        else
            importDataButton.changeColor(true,Constants.OBJECT_YELLOW);
    }

    public String getChamber() {
        return chamberComboBox.getValue();
    }

    public String getSoftware() {
        return softwareComboBox.getValue();
    }

    public boolean checkPane() {
        return Integer.parseInt(getChamber()) == graphs && getSoftware() == software &&  dataLoaded;
    }

    public void setChamber(String value) {
        chamberComboBox.setValue(value);
    }

    public void setSoftware(String value) {
        softwareComboBox.setValue(value);
    }
}
