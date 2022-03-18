package com.fishrespbase.tabs.componets.for_trait;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import com.fishrespbase.components.alerts.AlertAccordion;
import com.fishrespbase.components.alerts.AlertType;
import com.fishrespbase.components.alerts.SingleAlert;
import com.fishrespbase.tabs.Results;
import com.fishrespbase.tabs.Traits;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.converter.DateTimeStringConverter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class RawDataPane extends BaseTitledPane {

    private Traits mainTrait;

    private boolean dataLoaded;
    private int graphs;
    private String software;
    private String firstTime, secondTime;

    private FileChooser fileChooser;

    private ColoredButton importDataButtonRaw;
    private ColoredButton qcTempButtonRaw, qcOxButtonRaw;
    private TextField fileAdressFieldRaw;
    private TextField timeIntervalField1Raw,timeIntervalField2Raw;
    private ComboBox chamberComboBoxRaw, softwareComboBoxRaw;

    private TextField mtw;
    public TextField dateTime;
    //public Label dateTimeExample;

    public String getSoftware() {
        if (softwareComboBoxRaw != null)
            return softwareComboBoxRaw.getValue().toString();
        return "";
    }

    public RawDataPane(Traits mainTrait, double x, double y, double w, double h) {
        super("Import of raw data", x, y, w, h);

        this.mainTrait = mainTrait;

        this.setDisable(true);
        fileChooser = new FileChooser();
    }

    @Override
    protected void createContent() {
        fileAdressFieldRaw = new TextField();
        fileAdressFieldRaw.relocate(DPIkiller.kill(5), DPIkiller.kill(5));
        fileAdressFieldRaw.setPrefSize(DPIkiller.kill(190), DPIkiller.kill(25));

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
                        fileAdressFieldRaw.setText(selectedFile.getPath());
                    }
                });

        Label timeIntervalLabel = new Label("Time interval");
        timeIntervalLabel.relocate(DPIkiller.kill(5), DPIkiller.kill(40));

        timeIntervalField1Raw = new TextField();
        timeIntervalField1Raw.relocate(DPIkiller.kill(85),DPIkiller.kill(35));
        timeIntervalField1Raw.setPrefSize(DPIkiller.kill(60),DPIkiller.kill(20));
        timeIntervalField1Raw.setText("00:00:00");

        timeIntervalField1Raw.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> askButton());

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            timeIntervalField1Raw.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Label timeIntervalLabel2 = new Label("-");
        timeIntervalLabel2.relocate(DPIkiller.kill(150), DPIkiller.kill(40));

        timeIntervalField2Raw = new TextField();
        timeIntervalField2Raw.relocate(DPIkiller.kill(160),DPIkiller.kill(35));
        timeIntervalField2Raw.setPrefSize(DPIkiller.kill(60),DPIkiller.kill(20));
        timeIntervalField2Raw.setText("23:59:59");

        timeIntervalField2Raw.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> askButton());

        try {
            timeIntervalField2Raw.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("23:59:59")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Label loggerSoftwareLabel = new Label("Logger software");
        loggerSoftwareLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(75));

        ObservableList<String> softwareOptions = FXCollections.observableArrayList("AutoResp","FishResp","QboxAqua");
        softwareComboBoxRaw = new ComboBox(softwareOptions);
        softwareComboBoxRaw.relocate(DPIkiller.kill(100),DPIkiller.kill(70));
        softwareComboBoxRaw.setPrefSize(DPIkiller.kill(120),DPIkiller.kill(20));
        softwareComboBoxRaw.setValue(" ");

        Label dateTimeLabel = new Label("Date&Time");
        dateTimeLabel.relocate(DPIkiller.kill(5), DPIkiller.kill(180));

        dateTime = new TextField("");

        softwareComboBoxRaw.setOnAction(event -> {
            askButton();
            if (softwareComboBoxRaw.getValue() != "QboxAqua") {
                dateTime.setDisable(true);
                dateTimeLabel.setDisable(true);
            }
            else {
                dateTime.setDisable(false);
                dateTimeLabel.setDisable(false);
            }
        });


        Label chamberNumberLabel = new Label("Number of chambers");
        chamberNumberLabel.relocate(DPIkiller.kill(5),DPIkiller.kill(110));

        ObservableList<String> chamberOptions = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");
        chamberComboBoxRaw = new ComboBox(chamberOptions);
        chamberComboBoxRaw.relocate(DPIkiller.kill(135),DPIkiller.kill(105));
        chamberComboBoxRaw.setPrefSize(DPIkiller.kill(85),DPIkiller.kill(20));
        chamberComboBoxRaw.setValue(" ");

        chamberComboBoxRaw.setOnAction(event -> {
            askButton();
        });


        Label mtwLabel = new Label("To increase wait phase by (s)");
        mtwLabel.relocate(DPIkiller.kill(5), DPIkiller.kill(145));

        mtw = new TextField("0");
        mtw.relocate(DPIkiller.kill(162), DPIkiller.kill(140));
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

        //dateTimeExample = new Label();
        //dateTimeExample.relocate(DPIkiller.kill(78), DPIkiller.kill(179));
        //dateTimeExample.setTextFill(Color.GRAY);

        dateTime.relocate(DPIkiller.kill(72), DPIkiller.kill(175));
        switch (Saver.getDateFormat())
        {
            case "DMY":
                dateTime.setText("DD/MM/YYYY/HH:MM:SS");
                break;
            case "MDY":
                dateTime.setText("MM/DD/YYYY/HH:MM:SS");
                break;
            case "YMD":
                dateTime.setText("YYYY/MM/DD/HH:MM:SS");
                break;
        }
        //dateTime.setStyle("-fx-text-fill: white;");
        dateTime.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

        });


        importDataButtonRaw = new ColoredButton(true, Constants.OBJECT_GRAY, "Import data");
        importDataButtonRaw.setMaxWidth(DPIkiller.kill(100));
        importDataButtonRaw.setMinWidth(DPIkiller.kill(100));
        importDataButtonRaw.relocate(DPIkiller.kill(65), DPIkiller.kill(205));

        qcTempButtonRaw = new ColoredButton(false, Constants.OBJECT_GRAY, "QC: Temperature");
        qcTempButtonRaw.setMaxWidth(DPIkiller.kill(170));
        qcTempButtonRaw.setMinWidth(DPIkiller.kill(170));
        qcTempButtonRaw.relocate(DPIkiller.kill(30), DPIkiller.kill(235));

        qcOxButtonRaw = new ColoredButton(false, Constants.OBJECT_GRAY, "QC: Oxygen");
        qcOxButtonRaw.setMaxWidth(DPIkiller.kill(170));
        qcOxButtonRaw.setMinWidth(DPIkiller.kill(170));
        qcOxButtonRaw.relocate(DPIkiller.kill(30), DPIkiller.kill(265));

        importDataButtonRaw.setOnAction(
                (ActionEvent event) -> {
                    //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
                    //Y Y Y Y / M M / D D /  H  H  :  M  M  :  S  S
                    //D D / M M / Y Y Y Y /  H  H  :  M  M  :  S  S
                    if (softwareComboBoxRaw.getValue() == "QboxAqua") {
                        boolean invalidDateFormat = true;

                        if (dateTime.getText() != null)
                            switch (Saver.getDateFormat()) {
                                case "DMY":
                                    invalidDateFormat = !dateTime.getText().matches(
                                            "(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/[0-9]{4}/([01][0-9]||2[0-3]):[0-5][0-9]:[0-5][0-9]"
                                    );
                                    break;
                                case "MDY":
                                    invalidDateFormat = !dateTime.getText().matches(
                                            "(0[1-9]|1[012])/(0[1-9]|1[0-9]|2[0-9]|3[01])/[0-9]{4}/([01][0-9]||2[0-3]):[0-5][0-9]:[0-5][0-9]"
                                    );
                                    break;
                                case "YMD":
                                    invalidDateFormat = !dateTime.getText().matches(
                                            "[0-9]{4}/(0[1-9]|1[012])/(0[1-9]|1[0-9]|2[0-9]|3[01])/([01][0-9]||2[0-3]):[0-5][0-9]:[0-5][0-9]"
                                    );
                                    break;
                            }

                        if (invalidDateFormat) {
                            SingleAlert alert = new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "Input \"Date&Type\" in a correct format");
                            AlertAccordion.pushMessage(alert);
                            return;
                        }
                    }


                    if (fileAdressFieldRaw.getText().length()>0) {
                        StringBuffer tmp = new StringBuffer(fileAdressFieldRaw.getText());
                        int length = tmp.length();
                        for (int i = 0; i < length; i++) {
                            if (tmp.charAt(i) == '\\')
                                tmp.insert(i++,'\\');
                        }
                        SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Importing raw data");
                        AlertAccordion.pushMessage(alert);
                        String quest;
                        if (softwareComboBoxRaw.getValue() != "QboxAqua")
                            quest = "Trait."+mainTrait.getIndex()+" <- import.meas(file = \""+tmp+"\", logger = \""+softwareComboBoxRaw.getValue().toString()+"\", n.chamber = "+Integer.parseInt(chamberComboBoxRaw.getValue().toString())+", set.date.time = NA, meas.to.wait = "+mtw.getText()+", start.measure = \""+timeIntervalField1Raw.getText()+"\", stop.measure = \""+timeIntervalField2Raw.getText()+"\")";
                        else
                            quest = "Trait."+mainTrait.getIndex()+" <- import.meas(file = \""+tmp+"\", logger = \"QboxAqua\", n.chamber = "+Integer.parseInt(chamberComboBoxRaw.getValue().toString())+", set.date.time = \""+dateTime.getText()+"\", meas.to.wait = "+mtw.getText()+", start.measure = \""+timeIntervalField1Raw.getText()+"\", stop.measure = \""+timeIntervalField2Raw.getText()+"\")";
                        RLogger.log(quest);
                        if (Main.re.eval(quest) == null) {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR,Constants.DEFAULT_ALERTLIFETIME,"An error occurred while importing raw data"));
                            dataLoaded = false;

                            qcTempButtonRaw.changeColor(false, Constants.OBJECT_GRAY);
                            qcOxButtonRaw.changeColor(false, Constants.OBJECT_GRAY);
                            importDataButtonRaw.changeColor(true, Constants.OBJECT_YELLOW);

                            graphs = 0;
                            software = "";
                            firstTime = "";
                            secondTime = "";

                            mainTrait.completeImport(false);
                        }
                        else {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS,Constants.DEFAULT_ALERTLIFETIME,"Done!"));
                            dataLoaded = true;

                            graphs = Integer.parseInt(chamberComboBoxRaw.getValue().toString());
                            software = softwareComboBoxRaw.getValue().toString();
                            firstTime = timeIntervalField1Raw.getText();
                            secondTime = timeIntervalField2Raw.getText();

                            qcTempButtonRaw.changeColor(true, Constants.OBJECT_BLUE);
                            qcOxButtonRaw.changeColor(true, Constants.OBJECT_BLUE);
                            importDataButtonRaw.changeColor(true, Constants.OBJECT_GREEN);

                            mainTrait.completeImport(true);
                        }
                    }
                    else
                        AlertAccordion.pushMessage(new SingleAlert(AlertType.WARNING, Constants.DEFAULT_ALERTLIFETIME, "Specify the file address"));

                });


        qcTempButtonRaw.setOnAction(
                (ActionEvent event) -> {
                    if (dataLoaded) {
                        SingleAlert alert = new SingleAlert(AlertType.WAIT, 0, "Plotting a graph");
                        AlertAccordion.pushMessage(alert);

                        String quest = "QC.temperature.meas(Trait." + mainTrait.getIndex() + ")";
                        RLogger.log(quest);
                        if (Main.re.eval(quest) == null) {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while plotting a graph"));
                        }
                        else {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));
                            if (graphs > 4)
                                mainTrait.getGraphModule().setBaseFilename("meas.temperature_", 2);
                            else
                                mainTrait.getGraphModule().setBaseFilename("meas.temperature_", 1);

                            mainTrait.getGraphModule().resizeGraph();
                        }
                    }
                });

        qcOxButtonRaw.setOnAction(
                (ActionEvent event) -> {

                    if (dataLoaded) {
                        SingleAlert alert = new SingleAlert(AlertType.WAIT,0,"Plotting a graph");
                        AlertAccordion.pushMessage(alert);

                        String quest = "QC.oxygen.meas(Trait." + mainTrait.getIndex() + ")";
                        RLogger.log(quest);
                        if (Main.re.eval(quest) == null) {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.ERROR, Constants.DEFAULT_ALERTLIFETIME, "An error occurred while plotting a graph"));
                        }
                        else {
                            AlertAccordion.remove(alert);
                            AlertAccordion.pushMessage(new SingleAlert(AlertType.SUCCESS, Constants.DEFAULT_ALERTLIFETIME, "Done!"));
                            if (graphs > 4)
                                mainTrait.getGraphModule().setBaseFilename("meas.oxygen_", 2);
                            else
                                mainTrait.getGraphModule().setBaseFilename("meas.oxygen_", 1);

                            mainTrait.getGraphModule().resizeGraph();
                        }
                    }
                });

        contentPane.getChildren().addAll(fileAdressFieldRaw, openFileButton,
                timeIntervalLabel, timeIntervalField1Raw, timeIntervalLabel2, timeIntervalField2Raw,
                chamberNumberLabel, chamberComboBoxRaw, loggerSoftwareLabel, softwareComboBoxRaw,
                mtwLabel, mtw, dateTimeLabel, dateTime, /*dateTimeExample,*/
                importDataButtonRaw, qcTempButtonRaw, qcOxButtonRaw);
    }

    public void setEnabled(boolean state) {
        this.setDisable(!state);

        if (!state)
            importDataButtonRaw.changeColor(true,Constants.OBJECT_GRAY);
        else
            importDataButtonRaw.changeColor(true,Constants.OBJECT_YELLOW);
    }

    private void askButton() {
        importDataButtonRaw.changeColor(true,Constants.OBJECT_YELLOW);
        qcTempButtonRaw.changeColor(false,Constants.OBJECT_GRAY);
        qcOxButtonRaw.changeColor(false,Constants.OBJECT_GRAY);
        mainTrait.setCompleted(false);
        mainTrait.completeImport(false);

        Results.tab.resetTab();

        mainTrait.changeTabColor(Constants.OBJECT_YELLOW);
    }

    public void reset() {
        fileAdressFieldRaw.setText("");
        timeIntervalField1Raw.setText("00:00:00");
        timeIntervalField2Raw.setText("23:59:59");
        chamberComboBoxRaw.setValue(Saver.getDefaultChamberNumber());
        softwareComboBoxRaw.setValue(Saver.getDefaultLoggerSoftware());
        if (this.isDisabled())
            importDataButtonRaw.changeColor(true,Constants.OBJECT_GRAY);
        else
            importDataButtonRaw.changeColor(true,Constants.OBJECT_YELLOW);
        qcTempButtonRaw.changeColor(false,Constants.OBJECT_GRAY);
        qcOxButtonRaw.changeColor(false,Constants.OBJECT_GRAY);

        this.setDisable(true);
    }
}
