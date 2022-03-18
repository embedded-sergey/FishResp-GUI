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
public class RMRWindow extends Stage {
    private Pane root;
    private WebEngine webEngine;
    private ComboBox fromComboBox, toComboBox;
    private ColoredButton exportButton;
    private TextField salinityField;
    private ComboBox softwareComboBox, chamberComboBox;
    private TextField fileAdressField;
    private ComboBox barometricComboBox;
    private TextField barometricField;

    public RMRWindow() {
        super();
        root = new Pane();
        root.setStyle("-fx-background-color: #FFFFFF;");
        Scene scn = new Scene(root, DPIkiller.kill(635), DPIkiller.kill(355));
        scn.getStylesheets().add(getClass().getClassLoader().getResource("com/fishrespbase/css/modalWindow.css").toExternalForm());
        this.setScene(scn);
        this.setTitle("FishResp "+ Main.programVersion+": rMR (R package)");
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
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "rMR.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "rMR.html").toURI().toURL().toString());
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

        Label barometricLabel = new Label("Bar. pressure");
        barometricLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(195));

        ObservableList<String> barometricOptions = FXCollections.observableArrayList("[kpa]","[atm]","[mmHg]");
        barometricComboBox = new ComboBox(barometricOptions);
        barometricComboBox.relocate(DPIkiller.kill(95),DPIkiller.kill(190));
        barometricComboBox.setValue(barometricComboBox.getItems().get(0));
        barometricComboBox.setStyle("-fx-font-size: 10px;");
        barometricComboBox.setPrefSize(DPIkiller.kill(75),DPIkiller.kill(25));


        barometricField = new TextField();
        barometricField.relocate(DPIkiller.kill(180), DPIkiller.kill(190));
        barometricField.setPrefSize(DPIkiller.kill(55), DPIkiller.kill(25));
        barometricField.textProperty().addListener(observable -> {
            askButton();
        });
        barometricField.setText("101.3");

        barometricField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (TextInputCheckers.checkFloat(newValue,0,5000))
                        barometricField.setText(newValue);
                    else
                        barometricField.setText(oldValue);
                });

        Label fromLabel = new Label("From");
        fromLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(230));

        ObservableList<String> fromOptions = FXCollections.observableArrayList("Air concentration [%]","Partial pressure","DO concentration [mg/L]");
        fromComboBox = new ComboBox(fromOptions);
        fromComboBox.relocate(DPIkiller.kill(60),DPIkiller.kill(225));
        fromComboBox.setPrefSize(DPIkiller.kill(175),DPIkiller.kill(25));
        fromComboBox.setValue(" ");
        fromComboBox.setDisable(true);

        Label toLabel = new Label("To");
        toLabel.relocate(DPIkiller.kill(20), DPIkiller.kill(265));

        toComboBox = new ComboBox(fromOptions);
        toComboBox.relocate(DPIkiller.kill(60),DPIkiller.kill(260));
        toComboBox.setPrefSize(DPIkiller.kill(175),DPIkiller.kill(25));
        toComboBox.setValue(" ");
        toComboBox.setDisable(true);

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
            String quest = "convert.rMR(\'"+tmp+"\', \'";

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
                quest += "\', n.chamber = " + chamberComboBox.getValue() + ", logger = \'" + softwareComboBox.getValue() + "\', DO.units.in = \'" + convertComboValue(fromComboBox.getValue().toString());
                quest += "\', DO.units.out = \'" + convertComboValue(toComboBox.getValue().toString()) + "\', salinity = "+ salinityField.getText() +", bar.units.in = \'" + convertBaroValue(barometricComboBox.getValue().toString()) + "\', bar.press = " + barometricField.getText() +")";
                //RLogger.log(quest);

                if (Main.re.eval(quest) != null) {
                    exportButton.changeColor(true, Constants.OBJECT_GREEN);
                }
            }
         });

        fromComboBox.setOnAction(event -> {
            askButton();
        });

        toComboBox.setOnAction(event -> {
            askButton();
        });

        barometricComboBox.setOnAction(event -> {
            askButton();

            if (barometricComboBox.getValue().toString().length()!=0) {
                ObservableList<String> toOptions = FXCollections.observableArrayList("Air concentration [%]", "Partial pressure " + barometricComboBox.getValue().toString(), "DO concentration [mg/L]");

                fromComboBox.setItems(toOptions);
                toComboBox.setItems(toOptions);
                toComboBox.setValue(" ");
                fromComboBox.setValue(" ");
            }
        });

        pane.getChildren().addAll(adressLabel,fileAdressField,openFileButton, loggerLabel,softwareComboBox, chambersLabel,chamberComboBox,
                salinityLabel,salinityField, barometricLabel,barometricComboBox,barometricField, fromLabel,fromComboBox,toLabel,toComboBox, exportButton);
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
        browser.setPrefSize(DPIkiller.kill(375),DPIkiller.kill(355));
        //browser.prefWidthProperty().bind(Main.baseScene.widthProperty().subtract(435));
        //browser.prefHeightProperty().bind(Main.baseScene.heightProperty().subtract(70));

        webEngine = browser.getEngine();
        webEngine.setUserStyleSheetLocation(getClass().getClassLoader().getResource("com/fishrespbase/css/webEngine.css").toString());
        try {
            if (Main.OS == "mac")
                webEngine.load(new File(System.getenv("PDIR") + File.separator + "rMR.html").toURI().toURL().toString());
            else
                webEngine.load(new File(System.getProperty("user.dir") + File.separator + "rMR.html").toURI().toURL().toString());
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
                return "pct";
            case "DO concentration [mg/L]":
                return "mg/L";
            default:
                return "PP";
        }
    }

    private String convertBaroValue(String value) {
        switch (value) {
            case "[atm]":
                return "atm";
            case "[kpa]":
                return "kpa";
            default:
                return "mmHg";
        }
    }

    private void askButton() {
        if (toComboBox != null) {
            if (!fileAdressField.getText().isEmpty()
            && !softwareComboBox.getValue().toString().isEmpty()
            && !chamberComboBox.getValue().toString().isEmpty()
            && !salinityField.getText().isEmpty()
            && !barometricComboBox.getValue().toString().isEmpty()
            && !barometricField.getText().isEmpty()) {
                fromComboBox.setDisable(false);
                toComboBox.setDisable(false);
            } else {
                fromComboBox.setDisable(true);
                fromComboBox.setValue(" ");
                toComboBox.setDisable(true);
                toComboBox.setValue(" ");
            }

            if (fromComboBox != null && toComboBox != null)
                if (fromComboBox.getValue() == " " || toComboBox.getValue() == " ") {
                    exportButton.changeColor(false, Constants.OBJECT_GRAY);
                } else {
                    exportButton.changeColor(true, Constants.OBJECT_YELLOW);
                }
            //exportButton.changeColor(false, Constants.OBJECT_GRAY);
        }
    }
}
