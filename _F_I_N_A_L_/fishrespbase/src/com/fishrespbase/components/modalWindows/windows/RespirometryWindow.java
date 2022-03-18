package com.fishrespbase.components.modalWindows.windows;

import com.fishrespbase.Main;
import com.fishrespbase.components.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;

import java.io.File;
import java.net.URI;

/**
 * Created by R7Bishop on 08.11.2017.
 */
public class RespirometryWindow extends Stage {
    private Pane root;
    private WebEngine webEngine;
    private ComboBox fromComboBox, toComboBox;
    private ColoredButton exportButton;
    private TextField salinityField;
    private ComboBox softwareComboBox, chamberComboBox;
    private TextField fileAdressField;
    private TextField barometricField;

    public RespirometryWindow() {
        super();
        root = new Pane();
        root.setStyle("-fx-background-color: #FFFFFF;");
        Scene scn = new Scene(root, DPIkiller.kill(615), DPIkiller.kill(355));
        this.setScene(scn);
        scn.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/modalWindow.css").toExternalForm());
        this.setTitle("FishResp "+Main.programVersion+": respirometry (R package)");
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(Main.primaryStage);
        this.getIcons().add(new Image("com/fishrespbase/img/programIcon.png"));
        this.setResizable(false);

        //createContent();

        //this.showAndWait();
    }

    public void showWindow() {
        this.show();
        createContent();
        fromComboBox.setValue("");
        toComboBox.setValue("");
        /*try {
            if (Main.OS == "mac")
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "respirometry.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "respirometry.html").toURI().toURL().toString());
        }
        catch (Exception e) {
            System.out.println("Tutorial opening exception: " + e);
        }*/
    }

    private void createContent() {
        root.getChildren().clear();
        Pane pane = new Pane();

        Label adressLabel = new Label("The address of the file");
        adressLabel.relocate(DPIkiller.kill(20),DPIkiller.kill(25));

        fileAdressField = new TextField();
        fileAdressField.relocate(DPIkiller.kill(20), DPIkiller.kill(50));
        fileAdressField.setPrefSize(DPIkiller.kill(190), DPIkiller.kill(25));
        fileAdressField.textProperty().addListener(observable -> askButton());

        FileChooser fileChooser = new FileChooser();

        Button openFileButton = new Button();
        openFileButton.relocate(DPIkiller.kill(210), DPIkiller.kill(50));
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

        Label loggerLabel = new Label("Logger software");
        loggerLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(90));

        ObservableList<String> softwareOptions = FXCollections.observableArrayList("AutoResp","FishResp", "QboxAqua");

        softwareComboBox = new ComboBox(softwareOptions);
        softwareComboBox.relocate(DPIkiller.kill(115),DPIkiller.kill(85));
        softwareComboBox.setPrefSize(DPIkiller.kill(120),DPIkiller.kill(25));
        softwareComboBox.setValue("");

        softwareComboBox.setOnAction(event -> {
            askButton();
        });

        Label chambersLabel = new Label("Number of chambers");
        chambersLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(125));

        ObservableList<String> chamberOptions = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");
        chamberComboBox = new ComboBox(chamberOptions);
        chamberComboBox.relocate(DPIkiller.kill(145),DPIkiller.kill(120));
        chamberComboBox.setPrefSize(DPIkiller.kill(90),DPIkiller.kill(25));
        chamberComboBox.setValue("");

        chamberComboBox.setOnAction(event -> {
            askButton();
        });

        Label salinityLabel = new Label("Salinity [parts per thousand]");
        salinityLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(160));

        salinityField = new TextField();
        salinityField.relocate(DPIkiller.kill(180), DPIkiller.kill(155));
        salinityField.setPrefSize(DPIkiller.kill(55), DPIkiller.kill(25));
        salinityField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue,0,100)) {
                        salinityField.setText(newValue);
                        askButton();
                    }
                    else
                        salinityField.setText(oldValue);
                });

        Label barometricLabel = new Label("Barometric pressure [mbar]");
        barometricLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(195));

        barometricField = new TextField();
        barometricField.relocate(DPIkiller.kill(180), DPIkiller.kill(190));
        barometricField.setPrefSize(DPIkiller.kill(55), DPIkiller.kill(25));
        barometricField.textProperty().addListener(observable -> askButton());
        barometricField.setText("1013.3");

        barometricField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue,0,5000))
                        barometricField.setText(newValue);
                    else
                        barometricField.setText(oldValue);
                });

        Label fromLabel = new Label("From");
        fromLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(230));


        ObservableList<String> fromOptions = FXCollections.observableArrayList("Air concentration [%]","Oxygen saturation [%]","Partial pressure [hPa]","Partial pressure [kPa]","Partial pressure [torr]",
                "Partial pressure [mmHg]","Partial pressure [inHg]","DO concentration [mg/L]","DO concentration [ug/L]","DO concentration [umol/L]","DO concentration [mmol/L]","DO concentration [ml/L]",
                "DO concentration [mg/kg] or [ppm]","DO concentration [ug/kg]", "DO concentration [umol/kg]", "DO concentration [mmol/kg]", "DO concentration [ml/kg]","Volumes percent [%]");
        fromComboBox = new ComboBox(fromOptions);
        fromComboBox.relocate(DPIkiller.kill(60),DPIkiller.kill(225));
        fromComboBox.setPrefSize(DPIkiller.kill(175),DPIkiller.kill(25));
        fromComboBox.setValue("");
        fromComboBox.setDisable(true);

        fromComboBox.setOnAction(event -> {
            askButton();
        });

        Label toLabel = new Label("To");
        toLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(265));

        toComboBox = new ComboBox(fromOptions);
        toComboBox.relocate(DPIkiller.kill(60),DPIkiller.kill(260));
        toComboBox.setPrefSize(DPIkiller.kill(175),DPIkiller.kill(25));
        toComboBox.setValue("");
        toComboBox.setDisable(true);

        toComboBox.setOnAction(event -> {
            askButton();
        });


        exportButton = new ColoredButton(false, Constants.OBJECT_GRAY, "Convert and Export");
        exportButton.setPrefWidth(DPIkiller.kill(180));
        exportButton.relocate(DPIkiller.kill(40), DPIkiller.kill(310));

        exportButton.setOnAction(event -> {
            StringBuffer tmp = new StringBuffer(fileAdressField.getText());
            int length = tmp.length();
            for (int i = 0; i < length; i++) {
                if (tmp.charAt(i) == '\\')
                    tmp.insert(i++,'\\');
            }
            String quest = "convert.respirometry(\'"+tmp+"\', \'";

            FileChooser fileChooser1 = new FileChooser();
            fileChooser1.setTitle("Export the results");
            fileChooser1.setInitialDirectory(new File(Saver.getLastInputDirectory()));
            fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text table","*.txt"));
            File file = fileChooser1.showSaveDialog(Main.primaryStage);
            if (file != null) {
                Saver.setLastInputDirectory(file.getParent().toString());
                tmp = new StringBuffer(file.toString());
                if (Main.OS == "linux" && !tmp.toString().endsWith(".txt"))
                    tmp.append(".txt");
                length = tmp.length();
                for (int i = 0; i < length; i++) {
                    if (tmp.charAt(i) == '\\')
                        tmp.insert(i++,'\\');
                    //tmp.setCharAt(i, '/');
                }
                quest += tmp;
                quest += "\', n.chamber = " + chamberComboBox.getValue() + ", logger = \"" + softwareComboBox.getValue() + "\", from = \"" + convertComboValue(fromComboBox.getValue().toString()) + "\", to = \"" + convertComboValue(toComboBox.getValue().toString()) + "\", atm_pres = " + barometricField.getText() + ", sal = " + salinityField.getText() + ")";
                //RLogger.log(quest);

                if (Main.re.eval(quest) != null) {
                    exportButton.changeColor(true, Constants.OBJECT_GREEN);
                }
            }
        });

        pane.getChildren().addAll(adressLabel,fileAdressField,openFileButton, loggerLabel,softwareComboBox, chambersLabel,chamberComboBox,
                salinityLabel,salinityField, barometricLabel,barometricField, fromLabel,fromComboBox,toLabel,toComboBox, exportButton);
        /**вертикальная линия**/
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(DPIkiller.kill(260.0));
        moveTo.setY(DPIkiller.kill(30.0));
        VLineTo vLineTo = new VLineTo();
        vLineTo.setY(DPIkiller.kill(335.0));
        path.getElements().add(moveTo);
        path.getElements().add(vLineTo);
        /**вертикальная линия**/
        WebView browser = new WebView();
        browser.relocate(DPIkiller.kill(260),DPIkiller.kill(0));
        browser.setPrefSize(DPIkiller.kill(355),DPIkiller.kill(355));

        webEngine = browser.getEngine();
        webEngine.setUserStyleSheetLocation(getClass().getClassLoader().getResource("com/fishrespbase/css/webEngine.css").toString());
        try {
            if (Main.OS == "mac")
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "respirometry.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "respirometry.html").toURI().toURL().toString());
        }
        catch (Exception e) {
            System.out.println("RMR opening exception: "+e);
        }

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> o, Worker.State old, final Worker.State state) {


                Document doc = webEngine.getDocument();
                if (doc != null) {
                    NodeList nodeList = doc.getElementsByTagName("a");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        EventTarget tmp = ((EventTarget) nodeList.item(i));
                        ((EventTarget) nodeList.item(i)).addEventListener("click", evt -> Main.openWebPage(URI.create(tmp.toString())), false);
                    }

                }
            }
        });
        root.getChildren().addAll(pane,path,browser);
    }

    private String convertComboValue(String value) {
        switch (value) {
            case "Air concentration [%]":
                return "percent_a.s.";
            case "Oxygen saturation [%]":
                return "percent_o2";
            case "Partial pressure [hPa]":
                return "hPa";
            case "Partial pressure [kPa]":
                return "kPa";
            case "Partial pressure [torr]":
                return "torr";
            case "Partial pressure [mmHg]":
                return "mmHg";
            case "Partial pressure [inHg]":
                return "inHg";
            case "DO concentration [mg/L]":
                return "mg_per_l";
            case "DO concentration [ug/L]":
                return "ug_per_l";
            case "DO concentration [umol/L]":
                return "umol_per_l";
            case "DO concentration [mmol/L]":
                return "mmol_per_l";
            case "DO concentration [ml/L]":
                return "ml_per_l";
            case "DO concentration [mg/kg]":
                return "mg_per_kg";
            case "DO concentration [ug/kg]":
                return "ug_per_kg";
            case "DO concentration [umol/kg]":
                return "umol_per_kg";
            case "DO concentration [mmol/kg]":
                return "mmol_per_kg";
            case "DO concentration [ml/kg]":
                return "ml_per_kg";
            case "Volumes percent [%]":
                return "volumes_percent";
        }
        return "mmHg";
    }

    private void askButton() {
        if (toComboBox != null) {
            if (!fileAdressField.getText().isEmpty()
            && !softwareComboBox.getValue().toString().isEmpty()
            && !chamberComboBox.getValue().toString().isEmpty()
            && !salinityField.getText().isEmpty()
            && !barometricField.getText().isEmpty()) {
                fromComboBox.setDisable(false);
                toComboBox.setDisable(false);
            } else {
                fromComboBox.setDisable(true);
                fromComboBox.setValue("");
                toComboBox.setDisable(true);
                toComboBox.setValue("");
            }


            if (fromComboBox.getValue().toString().isEmpty() || toComboBox.getValue().toString().isEmpty()) {
                exportButton.changeColor(false, Constants.OBJECT_GRAY);
            } else {
                exportButton.changeColor(true, Constants.OBJECT_YELLOW);
            }
        }
    }
}
